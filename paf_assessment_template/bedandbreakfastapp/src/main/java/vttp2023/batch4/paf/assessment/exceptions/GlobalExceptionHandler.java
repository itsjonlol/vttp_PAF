package vttp2023.batch4.paf.assessment.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp2023.batch4.paf.assessment.exceptions.exception.BookingErrorException;
import vttp2023.batch4.paf.assessment.exceptions.exception.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorMessage> handleException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({BookingErrorException.class}) 
    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex,
    HttpServletRequest request) {
        
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
