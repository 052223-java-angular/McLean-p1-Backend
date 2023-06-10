package com.revature.p1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    private String id;

    @Column
    private String constellation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Favorite(String constellation, User user) {
        this.id = UUID.randomUUID().toString();
        this.constellation = constellation;
        this.user = user;
    }

}
