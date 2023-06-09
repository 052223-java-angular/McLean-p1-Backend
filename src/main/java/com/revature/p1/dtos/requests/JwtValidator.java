package com.revature.p1.dtos.requests;

import com.revature.p1.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtValidator {

    private String id;
    private String username;
    private Role role;

}
