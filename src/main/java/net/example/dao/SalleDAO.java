package net.example.dao;

import net.example.model.SalleModel; 
import net.example.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SalleDAO {

    public List<SalleModel> getAllSalles() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<SalleModel> salles = session.createQuery("FROM SalleModel", SalleModel.class).list(); // Mettre à jour la requête
        session.close();
        return salles;
    }

    public SalleModel getSalleById(String codesal) { // codesal est désormais un String
        Session session = HibernateUtility.getSessionFactory().openSession();
        SalleModel salle = session.createQuery("FROM SalleModel s WHERE s.codesal = :codesal", SalleModel.class) // Mettre à jour la requête
                                  .setParameter("codesal", codesal)
                                  .uniqueResult();
        session.close();
        return salle;
    }

    public void saveSalle(SalleModel salle) { // Mettre à jour le paramètre
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(salle);
        transaction.commit();
        session.close();
    }

    public void updateSalle(String codesal, String designation) { // codesal est désormais un String
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE SalleModel s SET s.designation = :designation WHERE s.codesal = :codesal") // Mettre à jour la requête
               .setParameter("designation", designation)
               .setParameter("codesal", codesal)
               .executeUpdate();

        transaction.commit();
        session.close();
    }

    public void deleteSalle(String codesal) { // codesal est désormais un String
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM SalleModel s WHERE s.codesal = :codesal") // Mettre à jour la requête
               .setParameter("codesal", codesal)
               .executeUpdate();
        transaction.commit();
        session.close();
    }
}