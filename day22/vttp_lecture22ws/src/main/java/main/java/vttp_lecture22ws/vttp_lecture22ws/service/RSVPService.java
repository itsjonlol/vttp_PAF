package main.java.vttp_lecture22ws.vttp_lecture22ws.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.vttp_lecture22ws.vttp_lecture22ws.model.RSVP;
import main.java.vttp_lecture22ws.vttp_lecture22ws.repo.RSVPRepo;

@Service
public class RSVPService {
    
    @Autowired
    RSVPRepo rsvpRepo;

    public List<RSVP> getAllRSVP() {
        return rsvpRepo.getAllRSVP();
    }

    public Optional<RSVP> getRSVPByEmail(String email) {
        return rsvpRepo.getRSVPByEmail(email);
        
    }
    public Boolean addRSVP(RSVP rsvp) {
        return rsvpRepo.addRSVP(rsvp);
    }
    public Boolean updateRSVP(RSVP rsvp,Integer rsvp_id) {
        return rsvpRepo.updateRSVP(rsvp,rsvp_id);
    }

    public Integer countRSVP() {
        return rsvpRepo.getRSVPCount();
    }
}
