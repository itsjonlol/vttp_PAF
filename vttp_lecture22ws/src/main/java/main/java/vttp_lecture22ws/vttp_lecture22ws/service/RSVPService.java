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
    public void addRSVP(RSVP rsvp) {
        rsvpRepo.addRSVP(rsvp);
    }
    public void updateRSVP(RSVP rsvp,String email) {
        rsvpRepo.updateRSVP(rsvp,email);
    }
}
