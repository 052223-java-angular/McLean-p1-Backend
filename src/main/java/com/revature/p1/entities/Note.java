package com.revature.p1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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

    public Note(String note, long created_at, long edited_at, Date date) {
        this.id = UUID.randomUUID().toString();
        this.note = note;
        this.created_at = created_at;
        this.edited_at = edited_at;
        this.date = date;
    }

}
