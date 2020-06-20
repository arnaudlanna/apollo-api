package repository;

import model.PlaylistViewModel;
import model.PodcastViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Hibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Playlist {
    private static Exception UserNotFoundException;

    public static List<model.Playlist> list(){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Playlist> cq = cb.createQuery(model.Playlist.class);
        Root<model.Playlist> rootEntry = cq.from(model.Playlist.class);
        CriteriaQuery<model.Playlist> all = cq.select(rootEntry);

        TypedQuery<model.Playlist> allQuery = session.createQuery(all).setMaxResults(5);
        List<model.Playlist> result = allQuery.getResultList();
        session.close();
        return result;
    }

    public static List<model.Playlist> find(String q){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Playlist> cq = cb.createQuery(model.Playlist.class);
        Root<model.Playlist> pod = cq.from(model.Playlist.class);
        CriteriaQuery<model.Playlist> like = cq.select(pod);

        TypedQuery<model.Playlist> likeResult = session.createQuery(like.where(cb.like(pod.<String>get("name"), "%" + q + "%"))).setMaxResults(5);
        List<model.Playlist> result = likeResult.getResultList();
        session.close();
        return result;
    }

    public static List<model.Playlist> byUserId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<model.Playlist> cq = cb.createQuery(model.Playlist.class);
        Root<model.Playlist> pod = cq.from(model.Playlist.class);
        CriteriaQuery<model.Playlist> like = cq.select(pod);

        TypedQuery<model.Playlist> likeResult = session.createQuery(like.where(cb.equal(pod.<Integer>get("user"), id))).setMaxResults(5);
        List<model.Playlist> result = likeResult.getResultList();
        session.close();
        return result;
    }

    public static model.Playlist byId(Integer id){
        Session session = Hibernate.getSessionFactory().openSession();
        model.Playlist result = session.get(model.Playlist.class, id);
        session.close();
        return result;
    }

    public static model.Playlist create(PlaylistViewModel playlistInput) throws Exception {
        Session session = Hibernate.getSessionFactory().openSession();

        model.Playlist playlist = new model.Playlist(playlistInput);
        model.User user = User.byId(playlistInput.getUser_id());
        if (user.getId() == null) {
            throw UserNotFoundException;
        }
        playlist.setUser(user);
        session.saveOrUpdate(playlist);
        session.persist(playlist);
        session.close();

        return playlist;
    }

    public static void delete(Integer id) {
        Session session = Hibernate.getSessionFactory().openSession();
        model.Playlist playlist = byId(id);
        if (playlist == null) {
            return;
        }
        Transaction tx = session.beginTransaction();
        session.delete(playlist);
        session.flush();
        session.clear();
        tx.commit();
        session.close();
    }
}
