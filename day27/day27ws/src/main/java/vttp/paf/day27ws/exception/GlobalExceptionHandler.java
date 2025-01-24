package vttp.paf.day27ws.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp.paf.day27ws.exception.exceptions.ErrorMessage;
import vttp.paf.day27ws.exception.exceptions.InvalidValueException;
import vttp.paf.day27ws.exception.exceptions.RecordNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorMessage> handleException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(500,
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({RecordNotFoundException.class,InvalidValueException.class}) 
    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex,
    HttpServletRequest request) {
        HttpStatus status;
        if (ex instanceof RecordNotFoundException) {
            status = HttpStatus.NOT_FOUND; // Set explicitly for RecordNotFoundException
        } else if (ex instanceof InvalidValueException) {
            status = HttpStatus.BAD_REQUEST; // Set explicitly for InvalidValueException
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // Fallback for unexpected exceptions
        }
        ErrorMessage errorMessage = new ErrorMessage(status.value(),
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,status);

    }
}
