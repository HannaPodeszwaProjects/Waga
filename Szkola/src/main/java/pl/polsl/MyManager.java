package pl.polsl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class MyManager {

    private static MyManager mm;
    private static EntityManager em;

    private MyManager(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("groupId_Szkola_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
    }

    public static MyManager getInstance() {
        if (mm == null) {
            mm = new MyManager();
        }
        return mm;
    }

    public static EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void finalize() {
        if (em.isOpen())
            em.close();
    }

}