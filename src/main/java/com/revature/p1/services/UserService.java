package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.Role;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.UserRepository;
import com.revature.p1.utils.custom_exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;
    private final LocationService locationService;

    public UserService(UserRepository userRepo, RoleService roleService, LocationService locationService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.locationService = locationService;
    }

    public boolean isValidUsername(String username) {
        //8-18 character length
        return username.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");
    }

    public boolean isUniqueUsername(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    public boolean isValidPassword(String password) {
        return password.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");
    }

    public boolean isSamePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public User registerUser(NewUserRequest req) {
        //must create Role - automatically set newUserRequest to be a user
        Role defaultRole = roleService.findByName("user");

        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

        User newUser = new User(req.getUsername(), hashed, defaultRole);

        // save the user
        userRepo.save(newUser);

        // user is now in the db, so we link the location by user
        Location newLocation = new Location("home", req.getLongitude(), req.getLatitude(), newUser);
        locationService.save(newLocation);

        return newUser;
    }

    public Principal login(NewLoginRequest req) {
        Optional<User> userOpt = userRepo.findByUsername(req.getUsername());
        if(userOpt.isPresent()) {
            User foundUser = userOpt.get();
            if(BCrypt.checkpw(req.getPassword(), foundUser.getPassword())) {
                return new Principal(foundUser);
            }
        }
        //if user not found, throws exception - must handle in controller
        throw new UserNotFoundException("Invalid Credentials");
    }

    public User findUserById(String userId) {
        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        throw new UserNotFoundException("User not found!");
    }
}
