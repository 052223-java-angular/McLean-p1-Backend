package com.revature.p1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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

}
