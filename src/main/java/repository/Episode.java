package repository;

import org.hibernate.Session;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Episode {
    public static List<model.Episode> list(){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Episode> cq = cb.createQuery(model.Episode.class);
        Root<model.Episode> rootEntry = cq.from(model.Episode.class);
        CriteriaQuery<model.Episode> all = cq.select(rootEntry);

        TypedQuery<model.Episode> allQuery = session.createQuery(all).setMaxResults(5);
        return allQuery.getResultList();
    }

    public static List<model.Episode> find(String q){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Episode> cq = cb.createQuery(model.Episode.class);
        Root<model.Episode> pod = cq.from(model.Episode.class);
        CriteriaQuery<model.Episode> like = cq.select(pod);

        TypedQuery<model.Episode> likeResult = session.createQuery(like.where(cb.like(pod.<String>get("title"), "%" + q + "%"))).setMaxResults(5);
        return likeResult.getResultList();
    }
}
