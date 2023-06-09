package com.revature.p1.services;

import com.revature.p1.entities.Location;
import com.revature.p1.repositories.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location save(Location loc) {
        Location newLoc = new Location();
    }



}
