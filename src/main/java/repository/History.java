package repository;

import model.HistoryViewModel;
import model.PlaylistViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class History {
    private static Exception UserNotFoundException;

    public static List<model.History> list(){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.History> cq = cb.createQuery(model.History.class);
        Root<model.History> rootEntry = cq.from(model.History.class);
        CriteriaQuery<model.History> all = cq.select(rootEntry);

        TypedQuery<model.History> allQuery = session.createQuery(all).setMaxResults(5);
        List<model.History> result = allQuery.getResultList();
        session.close();
        return result;
    }

    public static List<model.History> byUserId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.History> cq = cb.createQuery(model.History.class);
        Root<model.History> pod = cq.from(model.History.class);
        CriteriaQuery<model.History> like = cq.select(pod);

        TypedQuery<model.History> likeResult = session.createQuery(like.where(cb.equal(pod.<Integer>get("user"), id))).setMaxResults(5);
        List<model.History> result = likeResult.getResultList();
        session.close();
        return result;
    }

    public static List<model.History> byEpisodeId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.History> cq = cb.createQuery(model.History.class);
        Root<model.History> pod = cq.from(model.History.class);
        CriteriaQuery<model.History> like = cq.select(pod);

        TypedQuery<model.History> likeResult = session.createQuery(like.where(cb.equal(pod.<Integer>get("episode"), id))).setMaxResults(5);
        List<model.History> result = likeResult.getResultList();
        session.close();
        return result;
    }

    public static model.History byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        model.History result = session.get(model.History.class, id);
        session.close();
        return result;
    }

    public static model.History create(HistoryViewModel HistoryInput) throws Exception {
        Session session = Hibernate.getSessionFactory().openSession();

        model.History history = new model.History();
        model.User user = User.byId(HistoryInput.getUser_id());
        model.Episode episode = Episode.byId(HistoryInput.getEpisode_id());
        if (user.getId() == null || episode.getId() == null) {
            throw UserNotFoundException;
        }
        history.setUser(user);
        history.setEpisode(episode);
        session.saveOrUpdate(history);
        session.persist(history);
        session.close();

        return history;
    }
}
