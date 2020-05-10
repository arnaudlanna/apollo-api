package controller;

import com.google.gson.Gson;
import model.PodcastViewModel;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;

public class Podcast {
    public static void routes() {
        Gson gson = new Gson();

        get("/podcast", (req, res) -> {
            List<PodcastViewModel> result = new ArrayList<PodcastViewModel>();
            try {
                List<model.Podcast> podcasts;
                if(!req.queryParams("q").isEmpty()) {
                    podcasts = repository.Podcast.find(req.queryParams("q"));
                } else {
                    podcasts = repository.Podcast.list();
                }
                for (model.Podcast podcast : podcasts) {
                    result.add(new PodcastViewModel(podcast));
                }
            } catch (Exception ex) {
                return Spark.halt(500);
            }
            return result;
        }, gson::toJson);

    }
}
