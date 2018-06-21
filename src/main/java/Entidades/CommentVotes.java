package Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommentVotes {
    @Id
    @GeneratedValue
    private int id;

    private int value;

    @ManyToOne()
    private User author;

    @ManyToOne()
    private Article article;
}
