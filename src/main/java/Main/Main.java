package Main;

import Entidades.Article;
import Entidades.Comment;
import Entidades.Tag;
import Entidades.User;
import Servicios.ArticleServices;
import Servicios.CommentServices;
import Servicios.TagServices;
import Servicios.UserServices;

import java.util.*;

import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {

        staticFileLocation("public");

        User user = new User("admin", "jefito", "admin123", true);
        User user2 = new User("tugolfa123", "Marielys", "yoshi", false);

        Tag tag1 = new Tag("que trabajo");
        Tag tag2 = new Tag("una vida");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);
        
        Article article = new Article("Titulo de prueba", "Nada en especial", user, new Date(), tags);
        Article article2 = new Article("El segundo", "Nada en especial", user, new Date(), tags);

        Comment comment1 = new Comment("Primero en comentar!", user, article);
        Comment comment2 = new Comment("Segundo en comentar!", user2, article);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        
        article.setCommentList(comments);

        UserServices.getInstance().create(user);
        for (Tag tag : tags) {
            TagServices.getInstance().create(tag);
        }
        ArticleServices.getInstance().create(article);
        for (Comment comment : comments) {
            CommentServices.getInstance().create(comment);
        }

        user.setName("Pedro");
        UserServices.getInstance().edit(user);

        article.setTitle("Nuevo titulo de prueba");
        ArticleServices.getInstance().edit(article);

        List<Tag> AllTags = TagServices.getInstance().findAll();
        System.out.println(AllTags);
    }
}
