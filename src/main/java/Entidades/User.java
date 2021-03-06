package Entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    private String username;

    private String name;

    private String password;

    private boolean isAdministrator;

    private boolean isAuthor;

    @OneToMany(mappedBy = "author")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "author")
    private List<Article> articleList;

    @OneToMany(mappedBy = "author")
    private List<ArticleVote> articleVotesList;

    @OneToMany(mappedBy = "author")
    private List<CommentVote> commentVoteList;

    public User() { }

    public User(String username, String name, String password, boolean isAdministrator, boolean isAuthor) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.isAdministrator = isAdministrator;
        this.isAuthor = isAuthor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    public List<ArticleVote> getArticleVotesList() {
        return articleVotesList;
    }

    public void setArticleVotesList(List<ArticleVote> articleVotesList) {
        this.articleVotesList = articleVotesList;
    }

    public List<CommentVote> getCommentVoteList() {
        return commentVoteList;
    }

    public void setCommentVoteList(List<CommentVote> commentVoteList) {
        this.commentVoteList = commentVoteList;
    }
}
