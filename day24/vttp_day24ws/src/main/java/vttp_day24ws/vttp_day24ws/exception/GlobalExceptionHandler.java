package vttp_day24ws.vttp_day24ws.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp_day24ws.vttp_day24ws.model.exceptions.ErrorMessage;
import vttp_day24ws.vttp_day24ws.model.exceptions.InsertionException;

@RestControllerAdvice  // to intercept. middleware
public class GlobalExceptionHandler {
    
    //array?
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorMessage> handleException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(500,
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(InsertionException.class) 
    public ResponseEntity<ErrorMessage> handleAccountNotFoundException(Exception ex,
    HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage errorMessage = new ErrorMessage(400,
        ex.getMessage(),new Date(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);

    }

   


 

}
