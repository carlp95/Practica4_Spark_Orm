package Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class CommentVote implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private Vote value;

    @ManyToOne()
    private User author;

    @ManyToOne()
    private Comment comment;

    public CommentVote() {
    }

    public CommentVote(Vote value, User author, Comment comment) {
        this.value = value;
        this.author = author;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vote getValue() {
        return value;
    }

    public void setValue(Vote value) {
        this.value = value;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
