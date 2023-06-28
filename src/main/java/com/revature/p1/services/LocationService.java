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

    public Location save(NewLocationRequest req, User foundUser) {
        Location newLocation = new Location(req.getName(), req.getLongitude(), req.getLatitude(), foundUser);
        return locationRepo.save(newLocation);
    }
    public Location save(Location location) {
        return locationRepo.save(location);
    }


    public List<Location> findByUser(User user) {
        return locationRepo.findByUser(user);
    }

}
