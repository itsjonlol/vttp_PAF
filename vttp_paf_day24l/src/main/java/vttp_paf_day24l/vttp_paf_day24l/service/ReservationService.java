package vttp_paf_day24l.vttp_paf_day24l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf_day24l.vttp_paf_day24l.repo.ReservationRepo;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepo reservationRepo;
    
    // @Transactional
    // public Boolean createReservationRecord(Reservation reservation, ReservationDetail reservationDetail) {
    //     //start transaction
    //     //create the reservation record
    //     reservationRepo.createReservation(reservation);
    //     //created the reservation detail record
    //     // reservationRepo.createReservationDetails
    //     //commit transaction
    //     //auto because already annotated
    // }


    
}
