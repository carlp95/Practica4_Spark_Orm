package Servicios;

import Entidades.ArticleVote;


public class ArticleVoteServices extends Dao<ArticleVote> {
    private static ArticleVoteServices instance;

    private ArticleVoteServices() {
        super(ArticleVote.class);
    }

    public static ArticleVoteServices getInstance() {
        if (instance == null) {
            instance = new ArticleVoteServices();
        }
        return instance;
    }


}
