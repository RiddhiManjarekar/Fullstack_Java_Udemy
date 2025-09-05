package org.studyeasy.Hibernate1;



import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            User user = new User();
            user.setId(1);
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            session.save(user);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}