package com.revature.p1.dtos.responses;

import com.revature.p1.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private String id;
    private String username;
    private String comment;
    private long created_at;
    private long edited_at;
    private String userId;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.created_at = comment.getCreated_at();
        this.edited_at = comment.getEdited_at();
        this.userId = comment.getUser().getId();
    }

}
