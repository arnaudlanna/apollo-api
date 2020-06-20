package repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import model.UserViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class User {
    public model.User create(String name, String email, String password) throws NoSuchAlgorithmException {
        Session session = Hibernate.getSessionFactory().openSession();

        model.User user = new model.User(name, email, password);
        session.save(user);

        Hibernate.shutdown();
        return user;
    }

    public static String login(String email, String password) throws NoSuchAlgorithmException {
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.User> cq = cb.createQuery(model.User.class);
        Root<model.User> pod = cq.from(model.User.class);
        CriteriaQuery<model.User> like = cq.select(pod);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        password = sb.toString();

        TypedQuery<model.User> result = session.createQuery(like.where(cb.equal(pod.<String>get("email"), email), cb.equal(pod.<String>get("password"), password))).setMaxResults(5);
        model.User resUser = result.getSingleResult();
        Algorithm algorithm = Algorithm.HMAC256("ap0ll0s3cret");
        return JWT.create()
                .withSubject(resUser.getId().toString())
                .withIssuer("Apollo")
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static String signup(model.User user) throws NoSuchAlgorithmException {
        Session session = Hibernate.getSessionFactory().openSession();

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        user.setPassword(sb.toString());
        session.save(user);
        session.persist(user);
        session.close();

        Algorithm algorithm = Algorithm.HMAC256("ap0ll0s3cret");
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer("Apollo")
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static model.User update(model.User user) throws NoSuchAlgorithmException {
        Session session = Hibernate.getSessionFactory().openSession();

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        user.setPassword(sb.toString());

        model.User userInDB = session.get(model.User.class, user.getId());
        userInDB.updateUser(user);

        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(userInDB);
        tx.commit();
        session.close();
        return user;
    }

    public static model.User byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        return session.get(model.User.class, id);
    }
}
