package Servicios;

import Entidades.Article;
import Entidades.Tag;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class TagServices extends Dao<Tag> {
    private static TagServices instance;

    private TagServices() {
        super(Tag.class);
    }

    public static TagServices getInstance() {
        if (instance == null) {
            instance = new TagServices();
        }
        return instance;
    }

    // Warning: este metodo no funciona porque findByTagName no retorna null nunca sino que pasa una excepcion
//    @Override
//    public void create(Tag entity){
//        EntityManager em = getEntityManager();
//
//        try {
//            if (findByTagName(entity.getTagName()) != null) {
//                System.out.println("\t/!\\ This tag is already persisted. Creation canceled.");
//                return ;
//            }
//        }catch (IllegalArgumentException ie){
//            System.out.println("Illegal parameter.");
//        }
//
//        try {
//            em.getTransaction().begin();
//            em.persist(entity);
//            em.getTransaction().commit();
//        }catch (Exception ex){
//            em.getTransaction().rollback();
//            System.out.println(ex.getMessage());
//            throw  ex;
//        } finally {
//            em.close();
//        }
//    }

    public Tag findByTagName(String tagName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tag> query;
            query = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class);
            query.setParameter("tagName", tagName);

            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } finally {
            em.close();
        }
    }

    public void deleteAllFromArticle(Article article) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from ARTICLE_TAG where ARTICLELIST_ID = " + article.getId())
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
