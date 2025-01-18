package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.model.ReservationDetail;
import vttp_paf_day24l.vttp_paf_day24l.service.ReservationService;


@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("")
    public ResponseEntity<Boolean> makeReservation(@RequestBody ReservationDetail reservationDetail) {
        Boolean bCreated = reservationService.createReservationRecord(reservationDetail.getReservation(), reservationDetail);
        
        return ResponseEntity.ok().body(bCreated);
    }
    


}
