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

    public static model.User byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        return session.get(model.User.class, id);
    }
}
