package Servicios;

import Entidades.Article;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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

    @Override
    public Article find(Object id) {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Article> query =
                    em.createQuery("select distinct a from Article a join fetch a.tagList where a.id = :id", Article.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Article> findAll() {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Article> query= em.createQuery("select distinct a from Article a join fetch a.tagList", Article.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
