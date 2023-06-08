package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewRoleRequest;
import com.revature.p1.entities.Role;
import com.revature.p1.repositories.RoleRepository;
import com.revature.p1.utils.custom_exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    //for admins - returns true if role not found (unique)
    public boolean isUniqueRole(String name) {
        return roleRepo.findByName(name).isEmpty();
    }

    //for admins
    public Role saveRole(NewRoleRequest req) {
        Role newRole = new Role(req.getName());
        return roleRepo.save(newRole);
    }

    //returns Role by role name since role_id column of User entity is of type Role
    public Role findByName(String name) {
        //.orElseThrow() is optional class method. If there is no value present in this Optional instance,
        //then this method throws the exception generated from the specified supplier.
        return roleRepo.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role " + name + " not found."));
    }

}
