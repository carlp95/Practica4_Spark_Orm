package Entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(columnDefinition = "CLOB")
    private String body;

    @ManyToOne
    private User author;

    private Date date;

    @Column(name = "tag_id")
    @ManyToMany()
    private Set<Tag> tagList;

    @OneToMany(mappedBy = "article")
    private List<Comment> commentList;

    public Article() { }

    public Article(String title, String body, User author, Date date, Set<Tag> tagList) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.date = date;
        this.tagList = tagList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(Set<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}