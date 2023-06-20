package com.revature.p1.dtos.responses;

import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Principal {

    private String id;
    private String username;
    private String role;
    private Set<Location> locations;
    private String token;

    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().getName();
        this.locations = user.getLocations();
    }
}
