package repository;

import model.PodcastViewModel;
import org.hibernate.Session;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Podcast {
    private static Exception UserNotFoundException;

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

    public static List<model.Podcast> byUserId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Podcast> cq = cb.createQuery(model.Podcast.class);
        Root<model.Podcast> pod = cq.from(model.Podcast.class);
        CriteriaQuery<model.Podcast> like = cq.select(pod);

        TypedQuery<model.Podcast> likeResult = session.createQuery(like.where(cb.equal(pod.<Integer>get("user"), id))).setMaxResults(5);
        return likeResult.getResultList();
    }

    public static model.Podcast byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        return session.get(model.Podcast.class, id);
    }

    public static model.Podcast create(PodcastViewModel podcastInput) throws Exception {
        Session session = Hibernate.getSessionFactory().openSession();

        model.Podcast podcast = new model.Podcast();
        podcast.setName(podcastInput.getName());
        model.User user = repository.User.byId(podcastInput.getUserId());
        if (user.getId() == null) {
            throw UserNotFoundException;
        }
        podcast.setUser(user);
        session.saveOrUpdate(podcast);

        return podcast;
    }

    public static void delete(Integer id) {
        Session session = Hibernate.getSessionFactory().openSession();
        session.delete(byId(id));
    }
}
