package net.example.dao;

import net.example.model.ProfModel; 
import net.example.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProfDAO {

    public List<ProfModel> getAllProfs() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<ProfModel> profs = session.createQuery("FROM ProfModel", ProfModel.class).list(); 
        session.close();
        return profs;
    }

    public List<ProfModel> searchProf(String keyword) {
        Session session = HibernateUtility.getSessionFactory().openSession();

        String hql = "FROM ProfModel p WHERE p.codeprof LIKE :keyword OR p.nom LIKE :keyword OR p.prenom LIKE :keyword"; // Mettre à jour la requête

        List<ProfModel> profs = session.createQuery(hql, ProfModel.class)
                                      .setParameter("keyword", "%" + keyword + "%")
                                      .list();

        session.close();
        return profs;
    }

    public ProfModel getProfById(String codeprof) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        ProfModel prof = session.createQuery("FROM ProfModel p WHERE p.codeprof = :codeprof", ProfModel.class) // Mettre à jour la requête
                                .setParameter("codeprof", codeprof)
                                .uniqueResult();
        session.close();
        return prof;
    }

    public void saveProf(ProfModel prof) { 
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(prof);
        transaction.commit();
        session.close();
    }

    public void updateProf(String codeprof, String newnom, String newprenom, String newgrade) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE ProfModel p SET p.nom = :nom, p.prenom = :prenom, p.grade = :grade WHERE p.codeprof = :codeprof") // Mettre à jour la requête
               .setParameter("nom", newnom)
               .setParameter("prenom", newprenom)
               .setParameter("grade", newgrade)
               .setParameter("codeprof", codeprof)
               .executeUpdate();

        transaction.commit();
        session.close();
    }

    public void deleteProf(String codeprof) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM ProfModel p WHERE p.codeprof = :codeprof") 
               .setParameter("codeprof", codeprof)
               .executeUpdate();
        transaction.commit();
        session.close();
    }
}