package pl.polsl.controller;

import pl.polsl.MyManager;

import javax.persistence.EntityManager;
import java.util.List;

public interface ManageDataBase {





    public default void applyChanges(List<Object> toUpdate, List<Object> toPersist, List<Object> toDelete) {
        EntityManager em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            for (Object o : toUpdate)
                em.merge(o);
            for (Object o : toPersist)
                em.persist(o);
            for (Object o : toDelete)
                em.remove(o);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    /**
     * Modifies existing student in database
     *
     * @param object new object
     */
    public default void update(Object object) {
        EntityManager em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public default void persist(Object object) {
        EntityManager  em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public default void persist(List<Object> objects) {
        EntityManager  em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            for (Object o : objects)
                em.persist(o);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public default void delete(Object object) {
        EntityManager   em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public default void delete(List<Object> objects) {
        EntityManager   em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            for (Object o : objects)
                em.remove(o);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }


}
