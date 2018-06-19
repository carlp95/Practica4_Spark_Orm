package Servicios;

import Entidades.Article;

public class ArticleServices extends Dao<Article> {
    private static ArticleServices instance;

    private ArticleServices() {
        super(Article.class);
    }

    public static ArticleServices getInstance() {
        if (instance == null) {
            instance = new ArticleServices();
        }
        return instance;
    }
}
