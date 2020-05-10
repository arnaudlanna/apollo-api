package repository;

import org.hibernate.Session;
import utils.Hibernate;

public class User {
    public model.User create(String name, String email, String password) {
        Session session = Hibernate.getSessionFactory().openSession();

        model.User user = new model.User(name, email, password);
        session.save(user);

        Hibernate.shutdown();
        return user;
    }
}
