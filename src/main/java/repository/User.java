package repository;

import org.hibernate.Session;
import utils.Hibernate;

public class User {
    public model.User createUser(String name, String email, String password) {
        Session session = Hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        model.User user = new model.User(name, email, password);
        session.save(user);

        session.getTransaction().commit();
        Hibernate.shutdown();
        return user;
    }
}
