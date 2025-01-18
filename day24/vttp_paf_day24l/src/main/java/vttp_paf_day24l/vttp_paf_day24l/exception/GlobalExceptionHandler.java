package vttp_paf_day24l.vttp_paf_day24l.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.AccountNotFoundException;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.ErrorMessage;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.InsufficientBalanceException;

@RestControllerAdvice  // to intercept. middleware
public class GlobalExceptionHandler {
    
    //array?
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorMessage> handleException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(response.getStatus(),
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(AccountNotFoundException.class) 
    public ResponseEntity<ErrorMessage> handleAccountNotFoundException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InsufficientBalanceException.class) 
    public ResponseEntity<ErrorMessage> handleInsufficientBalanceException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);

    }


 

}
