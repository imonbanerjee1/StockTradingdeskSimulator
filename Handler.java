public class Handler extends Exception {
    public Handler() {
    }

    public Handler(String message) {
        super(message);
    }

    public Handler(String message, Throwable cause) {
        super(message, cause);
    }
    @Override
    public String getMessage(){
        String mess  = "wrong type of input";
        System.err.println(mess);
        return mess;
    }

}
