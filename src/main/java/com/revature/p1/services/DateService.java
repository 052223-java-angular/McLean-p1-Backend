package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewDateRequest;
import com.revature.p1.entities.Date;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.DateRepository;
import com.revature.p1.utils.custom_exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DateService {

    private final DateRepository dateRepo;

    public DateService(DateRepository dateRepo) {
        this.dateRepo = dateRepo;
    }

    public Date save(NewDateRequest req, User foundUser) {
        Date newDate = new Date(req.getName(), req.getCreated_at(), req.getMercury_phase(), req.getVenus_phase(), req.getMars_phase(), req.getJupiter_phase(), req.getSaturn_phase(), req.getUranus_phase(), req.getNeptune_phase(), req.getPluto_phase(), foundUser);
        return dateRepo.save(newDate);
    }

    public List<Date> findByUser(User foundUser) {
        return dateRepo.findByUser(foundUser);
    }

    public void deleteDateById(String id) {
        Optional<Date> dateOpt = dateRepo.findById(id);
        if(dateOpt.isPresent()) {
            dateRepo.deleteById(id);
        }
        throw new ResourceNotFoundException("No date found with id: "  + id + " found.");
    }
}
