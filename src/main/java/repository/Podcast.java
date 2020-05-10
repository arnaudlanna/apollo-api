package repository;

import org.hibernate.Session;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Podcast {
    public static List<model.Podcast> list(){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Podcast> cq = cb.createQuery(model.Podcast.class);
        Root<model.Podcast> rootEntry = cq.from(model.Podcast.class);
        CriteriaQuery<model.Podcast> all = cq.select(rootEntry);

        TypedQuery<model.Podcast> allQuery = session.createQuery(all).setMaxResults(5);
        return allQuery.getResultList();
    }

    public static List<model.Podcast> find(String q){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Podcast> cq = cb.createQuery(model.Podcast.class);
        Root<model.Podcast> pod = cq.from(model.Podcast.class);
        CriteriaQuery<model.Podcast> like = cq.select(pod);

        TypedQuery<model.Podcast> likeResult = session.createQuery(like.where(cb.like(pod.<String>get("name"), "%" + q + "%"))).setMaxResults(5);
        return likeResult.getResultList();
    }
}
