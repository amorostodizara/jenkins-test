package net.example.dao;

import net.example.model.UserModel; // Utilisation du bon package et de la classe UserModel
import net.example.util.HibernateUtility; // Utilisation du bon package pour HibernateUtil
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDAO {

    // Récupérer tous les utilisateurs
    public List<UserModel> getAllUsers() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<UserModel> users = session.createQuery("FROM UserModel", UserModel.class).list();
        session.close();
        return users;
    }

    // Récupérer un utilisateur par son ID
    public UserModel getUserById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        UserModel user = session.createQuery("FROM UserModel u WHERE u.id = :id", UserModel.class)
                                .setParameter("id", id)
                                .uniqueResult();
        session.close();
        return user;
    }

    // Sauvegarder un nouvel utilisateur
    public void saveUser(UserModel user) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

    // Mettre à jour l'email d'un utilisateur
    public void updateUser(Long id, String newEmail) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        
        session.createQuery("UPDATE UserModel u SET u.email = :email WHERE u.id = :id")
               .setParameter("email", newEmail)
               .setParameter("id", id)
               .executeUpdate();
        
        transaction.commit();
        session.close();
    }

    // Supprimer un utilisateur par son ID
    public void deleteUser(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        
        session.createQuery("DELETE FROM UserModel u WHERE u.id = :id")
               .setParameter("id", id)
               .executeUpdate();
        
        transaction.commit();
        session.close();
    }
    
    public UserModel getUserByEmailAndPassword(String email, String password) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        UserModel user = null;
        try {
            user = session.createQuery("FROM UserModel u WHERE u.email = :email AND u.password = :password", UserModel.class)
                           .setParameter("email", email)
                           .setParameter("password", password)
                           .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
    
    
}