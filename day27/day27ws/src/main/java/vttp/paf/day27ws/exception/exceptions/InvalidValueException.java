package vttp.paf.day27ws.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueException  extends RuntimeException {
    public InvalidValueException()  {
        //serializable for json
    }

    public InvalidValueException (String message) {
        super(message);
    }
    
    public InvalidValueException (String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}
