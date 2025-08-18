package net.example.dao;

import net.example.model.OccuperModel; // Mettre à jour l'import
import net.example.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Date;

public class OccuperDAO {

    public List<OccuperModel> getAllOccupers() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<OccuperModel> occupers = session.createQuery("FROM OccuperModel", OccuperModel.class).list(); // Mettre à jour la requête
        session.close();
        return occupers;
    }

    public OccuperModel getOccuperById(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        OccuperModel occuper = session.createQuery("FROM OccuperModel o WHERE o.id = :id", OccuperModel.class) // Mettre à jour la requête
                                      .setParameter("id", id)
                                      .uniqueResult();
        session.close();
        return occuper;
    }

    public void saveOccuper(OccuperModel occuper) { // Mettre à jour le paramètre
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(occuper);
        transaction.commit();
        session.close();
    }
    
    public void updateOccuper(int id, String newCodeprof, String newCodesal, Date newDate) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        // Récupérer l'occupation à mettre à jour
        OccuperModel occuper = session.get(OccuperModel.class, id);
        if (occuper != null) {
            // Mettre à jour les champs
            occuper.setCodeprof(newCodeprof);
            occuper.setCodesal(newCodesal);
            occuper.setDate(newDate);
            session.update(occuper); // Mise à jour de l'objet OccuperModel
        }

        transaction.commit();
        session.close();
    }

    public void deleteOccuper(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM OccuperModel o WHERE o.id = :id") // Mettre à jour la requête
               .setParameter("id", id)
               .executeUpdate();
        transaction.commit();
        session.close();
    }
}