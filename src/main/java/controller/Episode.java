package controller;

import com.google.gson.Gson;
import model.BaseResponse;
import model.EpisodeViewModel;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Episode {
    public static void routes() {
        Gson gson = new Gson();

        get("/episode", (req, res) -> {
            List<EpisodeViewModel> result = new ArrayList<>();
            try {
                List<model.Episode> episodes;
                if(req.queryParams("id") != null) {
                    episodes = repository.Episode.byPodcastId(Integer.parseInt(req.queryParams("id")));
                } else if(req.queryParams("q") != null) {
                    episodes = repository.Episode.find(req.queryParams("q"));
                } else {
                    episodes = repository.Episode.list();
                }
                for (model.Episode episode : episodes) {
                    result.add(new EpisodeViewModel(episode));
                }
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        post("/episodes", (req, res) -> {
            EpisodeViewModel episode = gson.fromJson(req.body(), model.EpisodeViewModel.class);
            model.Episode createdEpisode;
            try {
                createdEpisode = repository.Episode.create(episode);
            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new EpisodeViewModel(createdEpisode));
        }, gson::toJson);

        delete("/episodes/:id", (req, res) -> {
            try {
                repository.Episode.delete(Integer.parseInt(req.params(":id")));
            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, null);
        }, gson::toJson);

        options("/episodes/:id", ((request, response) -> response));
        options("/episodes", ((request, response) -> response));

    }
}
