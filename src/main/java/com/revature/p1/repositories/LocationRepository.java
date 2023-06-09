package com.revature.p1.repositories;

import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    Location save(Location location);

}
