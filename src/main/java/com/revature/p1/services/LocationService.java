package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.LocationRepository;
import com.revature.p1.utils.custom_exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location save(NewLocationRequest req, User foundUser) {
        Location newLocation = new Location(req.getName(), req.getLongitude(), req.getLatitude(), false, foundUser);
        return locationRepo.save(newLocation);
    }
    public Location save(Location location) {
        return locationRepo.save(location);
    }

    public List<Location> findByUser(User user) {
        return locationRepo.findByUser(user);
    }

    public Location updateLocation(String id, NewLocationRequest req, User user) {
        Optional<Location> location = locationRepo.findById(id);
        if(location.isEmpty()) {
            throw new ResourceNotFoundException("No location with id: " + id + " found.");
        }
        Location locationToUpdate = location.get();

        //remove old home location then update new home location
        Location currentHome = locationRepo.findByHomeTrue(user);
        currentHome.setHome(false);
        locationRepo.save(currentHome);

        locationToUpdate.setHome(true);
        return locationRepo.save(locationToUpdate);
    }

}
