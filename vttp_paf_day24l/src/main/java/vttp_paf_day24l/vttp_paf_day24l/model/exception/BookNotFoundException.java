package vttp_paf_day24l.vttp_paf_day24l.model.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException() {

    }

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
