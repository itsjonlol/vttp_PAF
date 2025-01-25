package vttp.paf.day28ws.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException  extends RuntimeException {
    public RecordNotFoundException()  {
        //serializable for json
    }

    public RecordNotFoundException (String message) {
        super(message);
    }
    
    public RecordNotFoundException (String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}
