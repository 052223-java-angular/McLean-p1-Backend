package com.revature.p1.repositories;

import com.revature.p1.entities.Favorite;
import com.revature.p1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, String> {

    Favorite findByUser(User user);

}
