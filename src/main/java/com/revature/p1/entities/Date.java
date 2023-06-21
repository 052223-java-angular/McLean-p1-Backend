package com.revature.p1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dates")
public class Date {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private long created_at;

    @Column
    private String mercury_const;

    @Column
    private String venus_const;

    @Column
    private String mars_const;

    @Column
    private String jupiter_const;

    @Column
    private String saturn_const;

    @Column
    private String uranus_const;

    @Column
    private String neptune_const;

    @Column
    private String pluto_const;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "date", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Note> notes;

    public Date(String name, long created_at, String mercury_const, String venus_const, String mars_const, String jupiter_const, String saturn_const, String uranus_const, String neptune_const, String pluto_const, User user) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.created_at = created_at;
        this.mercury_const = mercury_const;
        this.venus_const = venus_const;
        this.mars_const = mars_const;
        this.jupiter_const = jupiter_const;
        this.saturn_const = saturn_const;
        this.uranus_const = uranus_const;
        this.neptune_const = neptune_const;
        this.pluto_const = pluto_const;
        this.user = user;
    }

}
