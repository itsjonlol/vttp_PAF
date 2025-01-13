package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.service.ReservationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vttp_paf_day24l.vttp_paf_day24l.model.ReservationDetail;


@RestController
@RequestMapping("/api")
public class ReservationRestController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationDetail reservation) {
        //TODO: process POST request
        
        return null;
    }
    
    
}
