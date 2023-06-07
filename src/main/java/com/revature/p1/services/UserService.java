package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

//spring bean - singleton design pattern by default - only instantiated by itself with a private constructor
//uses static method
@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isUniqueUsername(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    public Principal login(NewLoginRequest req) {
        Optional<User> userOpt = userRepo.findByUsername(req.getUsername());
        if(userOpt.isPresent()) {
            User foundUser = userOpt.get();
            if(BCrypt.checkpw(req.getPassword(), foundUser.getPassword())) {
                return new Principal(foundUser);
            }
        }
    }

}
