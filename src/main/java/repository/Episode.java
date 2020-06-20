package repository;

import model.EpisodeViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Episode {
    private static Exception PodcastNotFoundException;

    public static List<model.Episode> list(){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Episode> cq = cb.createQuery(model.Episode.class);
        Root<model.Episode> rootEntry = cq.from(model.Episode.class);
        CriteriaQuery<model.Episode> all = cq.select(rootEntry);

        TypedQuery<model.Episode> allQuery = session.createQuery(all).setMaxResults(6);
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

    public static List<model.Episode> byPodcastId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Episode> cq = cb.createQuery(model.Episode.class);
        Root<model.Episode> pod = cq.from(model.Episode.class);
        CriteriaQuery<model.Episode> like = cq.select(pod);

        TypedQuery<model.Episode> likeResult = session.createQuery(like.where(cb.equal(pod.<Integer>get("podcast"), id))).setMaxResults(5);
        return likeResult.getResultList();
    }

    public static model.Episode byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        return session.get(model.Episode.class, id);
    }

    public static model.Episode create(EpisodeViewModel episodeInput) throws Exception {
        Session session = Hibernate.getSessionFactory().openSession();

        model.Episode episode = new model.Episode();
        episode.setTitle(episodeInput.getTitle());
        episode.setBanner(episodeInput.getBanner());
        episode.setDescription(episodeInput.getDescription());
        episode.setDuration(episodeInput.getDuration());
        model.Podcast podcast = repository.Podcast.byId(episodeInput.getPodcastId());
        if (podcast.getId() == null) {
            throw PodcastNotFoundException;
        }
        episode.setPodcast(podcast);
        episode.setLikes(0);
        episode.setViews(0);
        session.save(episode);

        return episode;
    }

    public static void delete(Integer id) {
        Session session = Hibernate.getSessionFactory().openSession();
        model.Episode episode = byId(id);
        if (episode == null || episode.getId() == null) {
            return;
        }
        Transaction tx = session.beginTransaction();
        session.delete(episode);
        session.flush();
        session.clear();
        tx.commit();
    }
}
