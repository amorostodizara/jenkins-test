package net.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtility {
	 private static final SessionFactory sessionFactory = buildSessionFactory();

	    private static SessionFactory buildSessionFactory() {
	        try {
	            // Vérifier si Hibernate trouve bien le fichier de configuration
	            System.out.println("Fichier Hibernate trouvé : " +
	                HibernateUtility.class.getClassLoader().getResource("hibernate.cfg.xml"));

	            return new Configuration().configure().buildSessionFactory();
	        } catch (Throwable ex) {
	            System.err.println("Erreur initialisation SessionFactory: " + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

}


