package vttp_paf_day24l.vttp_paf_day24l.model.exception;

public class ModifiedException extends RuntimeException {

    public ModifiedException(){

    }

    public ModifiedException(String message){
        super(message);
    }

    public ModifiedException(String message, Throwable throwable){
        super(message,throwable);
    }
    
}

