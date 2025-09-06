package exception;

public class RequestException extends Exception{
    
    public RequestException(String message, Throwable cause) {
            super(message, cause);
    }
}
