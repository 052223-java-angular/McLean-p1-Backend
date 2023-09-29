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
        //make sure only 1 favorite per account
        favoriteRepo.delete(findByUser(user));
        return favoriteRepo.save(new Favorite(req.getConstellation(), user));
    }

    public Favorite findByUser(User user) {
        return favoriteRepo.findByUser(user);
    }

    public Favorite update(NewFavoriteRequest req, User user) {
        favoriteRepo.delete(favoriteRepo.findByUser(user));
        return favoriteRepo.save(new Favorite(req.getConstellation(), user));
    }

}
