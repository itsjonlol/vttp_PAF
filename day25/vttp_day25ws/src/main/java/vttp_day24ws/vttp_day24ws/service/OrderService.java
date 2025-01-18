package vttp_day24ws.vttp_day24ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp_day24ws.vttp_day24ws.model.Order;
import vttp_day24ws.vttp_day24ws.repo.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    OrderRepo orderRepo;

    @Transactional
    public boolean createOrderRecord(Order order) {

        Boolean bCreated = false;
      

        // create the reservation record
        int iOrderId = orderRepo.createOrder(order);
        orderRepo.createOrderDetails(order,iOrderId);
            
        
       

        // uncomment to simulate error
        // throw new IllegalArgumentException("Simulate error after creating Reservation...");

        // created the reservation detail record
        
        // reservationDetail.getReservation().setId(iReservationId);
        // reservationRepo.createReservationDetails(reservationDetail);

        // uncomment to simulate error
        // throw new IllegalArgumentException("Simulate error after creating ReservationDetails...");

        // commit transaction
        // auto because already annotated
        bCreated = true;

        return bCreated;
    }

    
}
