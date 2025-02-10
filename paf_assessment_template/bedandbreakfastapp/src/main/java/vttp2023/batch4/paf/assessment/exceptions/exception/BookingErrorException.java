package vttp2023.batch4.paf.assessment.exceptions.exception;

public class BookingErrorException  extends RuntimeException {
    public BookingErrorException()  {
        //serializable for json
    }

    public BookingErrorException (String message) {
        super(message);
    }
    
    public BookingErrorException (String message, Throwable throwable) {
        super(message,throwable); //throwable can implement as a throw
    }
}