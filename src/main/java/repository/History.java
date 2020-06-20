package repository;

import javassist.NotFoundException;
import model.HistoryViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class History {

    private static NotFoundException NotFoundException;

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

    public static model.History createOrUpdate(HistoryViewModel HistoryInput) throws Exception {
        Session session = Hibernate.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        model.History history = new model.History();
        model.User user = User.byId(HistoryInput.getUser_id());
        model.Episode episode = Episode.byId(HistoryInput.getEpisode_id());
        if (user == null || user.getId() == null || episode == null || episode.getId() == null) {
            throw NotFoundException;
        }
        history.setUser(user);
        history.setEpisode(episode);
        history.setProgress(HistoryInput.getProgress());
        String hql = "delete from History where user = :user and episode = :episode";
        session.createQuery(hql).setParameter("user", user).setParameter("episode", episode).executeUpdate();
        session.saveOrUpdate(history);
        session.persist(history);
        tx.commit();    
        session.close();

        return history;
    }
}
