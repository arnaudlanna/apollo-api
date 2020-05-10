import controller.Podcast;
import utils.Hibernate;

public class Main {
    public static void main(String[] args) {
        Hibernate.getSessionFactory();
        Podcast.routes();
    }
}
