import controller.*;
import spark.Filter;
import utils.Hibernate;

import static spark.Spark.after;

public class Main {
    public static void main(String[] args) {
        Hibernate.getSessionFactory();
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS");
        });
        Podcast.routes();
        Episode.routes();
        Playlist.routes();
        Search.routes();
        User.routes();
    }
}
