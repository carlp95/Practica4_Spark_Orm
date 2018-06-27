package Entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String body;

    @ManyToOne()
    private User author;

    @ManyToOne()
    private Article article;

    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentVote> commentVoteList;

    public Comment() { }

    public Comment(String body, User author, Article article) {
        this.body = body;
        this.author = author;
        this.article = article;
    }


    public List<CommentVote> getCommentVoteList() {
        return commentVoteList;
    }

    public void setCommentVoteList(List<CommentVote> commentVoteList) {
        this.commentVoteList = commentVoteList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
