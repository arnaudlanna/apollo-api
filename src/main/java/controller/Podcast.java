package controller;

import com.google.gson.Gson;
import model.BaseResponse;
import model.PodcastViewModel;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Podcast {
    public static void routes() {
        Gson gson = new Gson();

        get("/podcasts", (req, res) -> {
            List<PodcastViewModel> result = new ArrayList<>();
            try {
                List<model.Podcast> podcasts = new ArrayList<>();
                if(req.queryParams("id") != null) {
                    podcasts.add(repository.Podcast.byId(Integer.parseInt(req.queryParams("id"))));
                } else if(req.queryParams("user_id") != null) {
                    podcasts = repository.Podcast.byUserId(Integer.parseInt(req.queryParams("user_id")));
                } else if(req.queryParams("q") != null) {
                    podcasts = repository.Podcast.find(req.queryParams("q"));
                } else {
                    podcasts = repository.Podcast.list();
                }
                for (model.Podcast podcast : podcasts) {
                    result.add(new PodcastViewModel(podcast));
                }
            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        post("/podcasts", (req, res) -> {
            PodcastViewModel podcast = gson.fromJson(req.body(), model.PodcastViewModel.class);
            model.Podcast createdPodcast;
            try {
                createdPodcast = repository.Podcast.create(podcast);
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new PodcastViewModel(createdPodcast));
        }, gson::toJson);

        delete("/podcasts/:id", (req, res) -> {
            try {
                repository.Podcast.delete(Integer.parseInt(req.params(":id")));
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, null);
        }, gson::toJson);

        options("/podcasts/:id", ((request, response) -> response));
        options("/podcasts", ((request, response) -> response));

    }
}
