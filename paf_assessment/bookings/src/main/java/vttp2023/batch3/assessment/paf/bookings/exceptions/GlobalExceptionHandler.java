package vttp2023.batch3.assessment.paf.bookings.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler()
   public ModelAndView handleException(Exception ex) {
      System.err.println(">>>>> In ExceptionHandler");
      ModelAndView mav = new ModelAndView("error");
      mav.addObject("error", "Handler by ExceptionHandler");
      mav.addObject("message", ex.getMessage());
      mav.setStatus(HttpStatusCode.valueOf(400));
      return mav;
   }
   @ExceptionHandler({BookingErrorException.class})
   public ModelAndView handleException2(Exception ex) {
      System.err.println(">>>>> In Booking failed");
      ModelAndView mav = new ModelAndView("error");
      mav.addObject("error", "Handler by Booking failed");
      mav.addObject("message", ex.getMessage());
      mav.setStatus(HttpStatusCode.valueOf(400));
      return mav;
   }
}
