import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trading.*;
import positions.*;

public class SwingApp extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private JTextField textField2;
    private PositionManager positionManager;

    Sim sim = new Sim();
    Handler handler = new Handler();
    public SwingApp() {
        setTitle("Trading Terminal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Create a custom font with a larger size
        Font customFont = new Font("Arial", Font.PLAIN, 18);

        // Create top panel for buttons and text input
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered layout with horizontal gap

        // Set the background color for the panel behind the buttons (Dark Gray)
        buttonPanel.setBackground(Color.decode("#333333"));

        JButton getOHLCButton = new JButton("GET OHLC");
        JButton buyButton = new JButton("BUY");
        JButton sellButton = new JButton("SELL");
        JButton myPositionsButton = new JButton("POSITIONS");

        // Set the background color and text color for buttons (Custom Dark Mode Colors)
        getOHLCButton.setBackground(Color.decode("#1E1E1E"));
        getOHLCButton.setForeground(Color.decode("#FFFFFF"));
        buyButton.setBackground(Color.decode("#1E1E1E"));
        buyButton.setForeground(Color.decode("#FFFFFF"));
        sellButton.setBackground(Color.decode("#1E1E1E"));
        sellButton.setForeground(Color.decode("#FFFFFF"));
        myPositionsButton.setBackground(Color.decode("#1E1E1E"));
        myPositionsButton.setForeground(Color.decode("#FFFFFF"));

        // Make the buttons vertically taller
        Dimension buttonSize = new Dimension(140, 60); // Width: 140, Height: 60
        getOHLCButton.setPreferredSize(buttonSize);
        buyButton.setPreferredSize(buttonSize);
        sellButton.setPreferredSize(buttonSize);
        myPositionsButton.setPreferredSize(buttonSize);

        // Set the custom font for buttons
        getOHLCButton.setFont(customFont);
        buyButton.setFont(customFont);
        sellButton.setFont(customFont);
        myPositionsButton.setFont(customFont);

        // Create a panel for text input
        JPanel textInputPanel = new JPanel();
        textInputPanel.setLayout(new BoxLayout(textInputPanel, BoxLayout.Y_AXIS)); // Vertical layout

        textField = new JTextField(20); // 20 columns wide
        textField.setFont(customFont);
        textField.setText(""); // Placeholder text
        textField.setForeground(Color.decode("#02b829")); // Text color
        textField.setBackground(Color.decode("#1E1E1E")); // Background color

        textField2 = new JTextField(20); // 20 columns wide
        textField2.setFont(customFont);
        textField2.setText(""); // Placeholder text
        textField2.setForeground(Color.decode("#02b829")); // Text color
        textField2.setBackground(Color.decode("#1E1E1E")); // Background color

        // Add the first text input field and label
        textInputPanel.add(new JLabel());
        textInputPanel.add(textField);

        // Add the second text input field and label
        textInputPanel.add(new JLabel());
        textInputPanel.add(textField2);

        // Create a custom font for text area
        Font textAreaFont = new Font("Monospaced", Font.PLAIN, 18);

        // Create bottom panel for text area
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Set the background color and text color for text area (Custom Dark Mode Colors)
        textArea.setBackground(Color.decode("#1E1E1E"));
        textArea.setForeground(Color.decode("#02b829"));
        textArea.setFont(textAreaFont);

        // Initialize PositionManager
        positionManager = new PositionManager();

        // Add ActionListener to each button
        getOHLCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sim.updatePrice();
                Stock[] stocks = sim.getStockData();
                String ohlcData = "";
                String inputText = textField.getText().toUpperCase();
                boolean stockExists = sim.doesStockExist(inputText, stocks);
                
                if (stockExists) {
                    // Stock exists, display its name and OHLC data
                    for (Stock stock : stocks) {
                        if (stock.getName().equals(inputText)) {
                            ohlcData = stock.getOHLCData() + "\nCurrent price is: " + Math.round(stock.getCurrentPrice() * 100.0) / 100.0;
                            System.out.println(stock.getCurrentPrice());
                            break;
                        }
                    }
                    displayText(inputText + "\n" + ohlcData);
                } else {
                    // Stock doesn't exist
                    displayText(inputText + " does not exist in the stock data.");
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stock[] stocks = sim.getStockData();
                String inputText = textField.getText().toUpperCase();
                try{
                    int quantity = Integer.parseInt(textField2.getText());
                    String buyResult = "";
                    if(sim.doesStockExist(inputText, stocks)){
                    for (Stock stock : stocks) {
                            if (stock.getName().equals(inputText)) {
                                buyResult = positionManager.buy(stock, quantity);
                                break;
                            }
                        }
                    displayText(buyResult);
                    }
                    else{
                        displayText(inputText + " does not exist in the stock data.");
                    }
                }catch(Exception p){
                    handler.getMessage();
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stock[] stocks = sim.getStockData();
                String inputText = textField.getText().toUpperCase();
                int quantity = Integer.parseInt(textField2.getText());
                String sellResult = "";
                if(sim.doesStockExist(inputText, stocks)){
                for (Stock stock : stocks) {
                        if (stock.getName().equals(inputText)) {
                            sellResult = positionManager.sell(stock, quantity,sim);
                            break;
                        }
                    }
                displayText(sellResult);
                }
                else{
                    displayText(inputText + " does not exist in the stock data.");
                }
            }
        });

        myPositionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stock[] stocks = sim.getStockData();
                String positionsData = positionManager.displayCurrentPositions(stocks,sim);
                displayText(positionsData);
            }
        });

        buttonPanel.add(getOHLCButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(myPositionsButton);

        // Add text input field to the text input panel
        textInputPanel.add(new JLabel("ENTER STOCK TICKER"));
        textInputPanel.add(textField);

        // Add text input field 2 to the text input panel
        textInputPanel.add(new JLabel("NUMBER OF STOCKS"));
        textInputPanel.add(textField2);

        // Add components to the frame
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(textInputPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void displayText(String message) {
        textArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingApp app = new SwingApp();
        app.setVisible(true);
    }
}
