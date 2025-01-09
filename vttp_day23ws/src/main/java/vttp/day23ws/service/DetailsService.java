package vttp.day23ws.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.day23ws.model.Details;
import vttp.day23ws.repo.DetailsRepo;

@Service
public class DetailsService {
    
    @Autowired
    DetailsRepo detailsRepo;

    public Optional<Details> getDetails(Integer orderId) {
        return detailsRepo.getDetails(orderId);
    }
}
