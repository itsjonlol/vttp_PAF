package vttp_paf_day24l.vttp_paf_day24l.model.exception;

public class AccountInactiveException  extends RuntimeException{
    public AccountInactiveException() {
        //serializable for json
    }

    public AccountInactiveException(String message) {
        super(message);
    }
    
    public AccountInactiveException(String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}
