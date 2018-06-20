package Entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String tagName;

    @ManyToMany(mappedBy = "tagList", fetch = FetchType.EAGER)
    private List<Article> articleList;

    public Tag() { }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", tagName='" + tagName + '\'' + ", articleList=" + articleList + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
