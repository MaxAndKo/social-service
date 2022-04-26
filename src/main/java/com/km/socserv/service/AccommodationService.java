package com.km.socserv.service;

import com.km.socserv.entity.Accommodation;
import com.km.socserv.entity.Order;
import com.km.socserv.entity.User;
import com.km.socserv.repository.AccommodationRepository;
import com.km.socserv.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation findById(int id){
        Accommodation accommodation = null;
        Optional<Accommodation> optional = accommodationRepository.findById(id);
        if (optional.isPresent())
            accommodation = optional.get();
        return accommodation;
    }

    public void save(Accommodation accommodation){
        accommodationRepository.save(accommodation);
    }

    public List<Accommodation> findAll(){
        return accommodationRepository.findAll();
    }

}
