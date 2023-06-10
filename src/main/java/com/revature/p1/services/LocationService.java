package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location save(NewLocationRequest loc, User user) {
        Location newLoc = new Location(loc.getName(), loc.getLongitude(), loc.getLatitude(), user);
        return locationRepo.save(newLoc);
    }

    public List<Location> findByUser(User user) {
        return locationRepo.findByUser(user);
    }



}
