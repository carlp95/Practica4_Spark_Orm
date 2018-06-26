package Servicios;

import Entidades.ArticleVote;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;


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

    public ArticleVote findByAuthorArticle(ArticleVote vote) {
        EntityManager em = getEntityManager();
        TypedQuery<ArticleVote> query = em.createQuery("select av from ArticleVote av where av.article.id = :article_id and av.author.username = :author_id", ArticleVote.class);
        query.setParameter("article_id", vote.getArticle().getId());
        query.setParameter("author_id", vote.getAuthor().getUsername());

        List<ArticleVote> results = query.getResultList();

        if (results.size() == 0) {
            return null;
        }
        else if (results.size() > 1) throw new NonUniqueResultException();

        return results.get(0);
    }

}
