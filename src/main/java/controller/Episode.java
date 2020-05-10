package controller;

import com.google.gson.Gson;
import model.EpisodeViewModel;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;

public class Episode {
    public static void routes() {
        Gson gson = new Gson();

        get("/episode", (req, res) -> {
            List<EpisodeViewModel> result = new ArrayList<>();
            try {
                List<model.Episode> episodes;
                if(req.queryParams("q") != null) {
                    episodes = repository.Episode.find(req.queryParams("q"));
                } else {
                    episodes = repository.Episode.list();
                }
                for (model.Episode episode : episodes) {
                    result.add(new EpisodeViewModel(episode));
                }
            } catch (Exception ex) {
                return Spark.halt(500);
            }
            return result;
        }, gson::toJson);

    }
}
