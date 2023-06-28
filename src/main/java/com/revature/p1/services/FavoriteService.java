package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewFavoriteRequest;
import com.revature.p1.entities.Favorite;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepo;

    public FavoriteService(FavoriteRepository favoriteRepo) {
        this.favoriteRepo = favoriteRepo;
    }

    public Favorite save(NewFavoriteRequest req, User user) {
        Favorite newFav = new Favorite(req.getConstellation(), user);
        return favoriteRepo.save(newFav);
    }

    public Favorite findByUser(User user) {
        return favoriteRepo.findByUser(user);
    }


}
