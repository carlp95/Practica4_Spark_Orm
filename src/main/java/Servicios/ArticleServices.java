package Servicios;

import Entidades.Article;
import Entidades.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
            TypedQuery<Article> query;
            query = em.createQuery("select distinct a from Article a join fetch a.tagList", Article.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Article> findAllWithTag(String tagName, int startPosition, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            Tag tag = TagServices.getInstance().findByTagName(tagName);

            Query query = em.createNativeQuery("select ARTICLELIST_ID from ARTICLE_TAG where TAGLIST_ID = "+tag.getId());

            query.setFirstResult(startPosition);
            query.setMaxResults(maxResult);

            List<Article> articles = new ArrayList<>();

            for (Object elm : query.getResultList()) {
                articles.add(ArticleServices.getInstance().find(Long.parseLong(elm.toString())));
            }

            return articles;
        } finally {
            em.close();
        }
    }
}
