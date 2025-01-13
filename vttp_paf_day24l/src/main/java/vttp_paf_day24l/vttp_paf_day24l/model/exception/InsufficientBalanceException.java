package vttp_paf_day24l.vttp_paf_day24l.model.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        //serializable for json
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    public InsufficientBalanceException(String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}
