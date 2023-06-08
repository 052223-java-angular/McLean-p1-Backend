package com.revature.p1.repositories;

import com.revature.p1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

//spring bean - singleton design pattern by default - only instantiated by itself with a private constructor
//uses static method
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User save(User user);

    //replaces DAO, syntax is always findBy and then the column name
    Optional<User> findByUsername(String username);

}
