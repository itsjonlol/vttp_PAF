package vttp.paf.day28l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day28l.repo.BGGRepo;

@Service
public class BGGService {
    
    @Autowired
    BGGRepo bggRepo;
}
