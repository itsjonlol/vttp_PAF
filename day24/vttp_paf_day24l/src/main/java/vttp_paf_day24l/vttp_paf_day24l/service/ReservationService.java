package vttp_paf_day24l.vttp_paf_day24l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp_paf_day24l.vttp_paf_day24l.model.Reservation;
import vttp_paf_day24l.vttp_paf_day24l.model.ReservationDetail;
import vttp_paf_day24l.vttp_paf_day24l.repo.ReservationRepo;

@Service
public class ReservationService {

    @Autowired
    ReservationRepo reservationRepo;

    @Transactional
    public boolean createReservationRecord(Reservation reservation, ReservationDetail reservationDetail) {

        Boolean bCreated = false;
        // start transaction
        // already in transaction based on the @Transactional annotation.

        // create the reservation record
        int iReservationId = reservationRepo.createReservation(reservation);

        // uncomment to simulate error
        // throw new IllegalArgumentException("Simulate error after creating Reservation...");

        // created the reservation detail record
        reservationDetail.getReservation().setId(iReservationId);
        reservationRepo.createReservationDetails(reservationDetail);

        // uncomment to simulate error
        // throw new IllegalArgumentException("Simulate error after creating ReservationDetails...");

        // commit transaction
        // auto because already annotated
        bCreated = true;

        return bCreated;
    }
    
}
