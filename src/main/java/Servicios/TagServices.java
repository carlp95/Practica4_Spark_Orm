package Servicios;

import Entidades.Tag;

import javax.persistence.EntityManager;
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

    public Tag findByTagName(String tagName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tag> query;
            query = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class);
            query.setParameter("tagName", tagName);

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void deleteAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete Tag").executeUpdate();
            em.getTransaction().commit();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
