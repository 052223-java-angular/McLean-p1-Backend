package com.revature.p1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="notes")
public class Note {

    @Id
    private String id;

    @Column
    private String note;

    @Column
    private long created_at;

    @Column
    private long edited_at;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Date date;
}
