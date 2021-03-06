package Entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Article implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(columnDefinition = "CLOB")
    private String body;

    @ManyToOne
    private User author;

    private Date date;

    @ManyToMany()
    private List<Tag> tagList;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<ArticleVote> articleVoteList;

    public Article() { }

    public Article(String title, String body, User author, Date date, List<Tag> tagList) {
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

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<ArticleVote> getArticleVoteList() {
        return articleVoteList;
    }

    public void setArticleVoteList(List<ArticleVote> articleVotesList) {
        this.articleVoteList = articleVotesList;
    }

    public int countLike() {
        int n = 0;
        for (ArticleVote articleVote: this.articleVoteList) {
            if (articleVote.getValue() == Vote.LIKE) {
                n++;
            }
        }
        return n;
    }

    public int countDislike() {
        int n = 0;
        for (ArticleVote articleVote: this.articleVoteList) {
            if (articleVote.getValue() == Vote.DISLIKE) {
                n++;
            }
        }
        return n;
    }

    public List<Integer> commentLikesCountList() {
        List<Integer> countList = new ArrayList<>();
        for (Comment comment : this.getCommentList()) {
            countList.add(comment.countLike());
        }
        return countList;
    }

    public List<Integer> commentDislikesCountList() {
        List<Integer> countList = new ArrayList<>();
        for (Comment comment : this.getCommentList()) {
            countList.add(comment.countDislike());
        }
        return countList;
    }
}
