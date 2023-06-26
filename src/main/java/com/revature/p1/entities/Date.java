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
    private double mercury_phase;

    @Column
    private double venus_phase;

    @Column
    private double mars_phase;

    @Column
    private double jupiter_phase;

    @Column
    private double saturn_phase;

    @Column
    private double uranus_phase;

    @Column
    private double neptune_phase;

    @Column
    private double pluto_phase;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "date", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Note> notes;

    public Date(String name, long created_at, double mercury_phase, double venus_phase, double mars_phase, double jupiter_phase, double saturn_phase, double uranus_phase, double neptune_phase, double pluto_phase, User user) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.created_at = created_at;
        this.mercury_phase = mercury_phase;
        this.venus_phase = venus_phase;
        this.mars_phase = mars_phase;
        this.jupiter_phase = jupiter_phase;
        this.saturn_phase = saturn_phase;
        this.uranus_phase = uranus_phase;
        this.neptune_phase = neptune_phase;
        this.pluto_phase = pluto_phase;
        this.user = user;
    }

}
