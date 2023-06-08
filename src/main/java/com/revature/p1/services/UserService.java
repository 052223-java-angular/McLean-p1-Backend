package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Role;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.RoleRepository;
import com.revature.p1.repositories.UserRepository;
import com.revature.p1.utils.custom_exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Optional;

//spring bean - singleton design pattern by default - only instantiated by itself with a private constructor
//uses static method
@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;

    public UserService(UserRepository userRepo, RoleService roleService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    public boolean isUniqueUsername(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    public User registerUser(NewUserRequest req) {
        //must create Role - automatically set newUserRequest to be a user
        Role defaultRole = roleService.findByName("user");

        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

        User newUser = new User(req.getUsername(), hashed, defaultRole);
        return userRepo.save(newUser);
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

}
