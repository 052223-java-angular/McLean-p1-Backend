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
@Table(name="comments")
public class Comment {

    @Id
    private String id;

    @Column
    private String comment;

    @Column
    private long created_at;

    @Column
    private long edited_at;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public Comment(String comment, long created_at, long edited_at, User user) {
        this.id = UUID.randomUUID().toString();
        this.comment = comment;
        this.created_at = created_at;
        this.edited_at = edited_at;
        this.user = user;
    }

}
