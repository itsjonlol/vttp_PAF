package vttp_day24ws.vttp_day24ws.model.exceptions;

public class InsertionException extends RuntimeException {
    public InsertionException() {
        //serializable for json
    }

    public InsertionException(String message) {
        super(message);
    }
    
    public InsertionException(String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}
