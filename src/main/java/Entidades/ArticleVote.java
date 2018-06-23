package Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class ArticleVote implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private Vote value;

    @ManyToOne()
    private User author;

    @ManyToOne()
    private Article article;

    public ArticleVote() {
    }

    public ArticleVote(Vote value, User author, Article article) {
        this.value = value;
        this.author = author;
        this.article = article;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
