package vttp_paf_day24l.vttp_paf_day24l.model.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        //serializable for json
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
    
    public AccountNotFoundException(String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }

}
