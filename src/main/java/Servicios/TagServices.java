package Servicios;

import Entidades.Tag;

import javax.persistence.EntityManager;

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
