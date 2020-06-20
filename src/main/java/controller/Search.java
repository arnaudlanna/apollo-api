package controller;

import com.google.gson.Gson;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.options;

public class Search {

    public static void routes() {
        Gson gson = new Gson();

        get("/search", (req, res) -> {
            List<EpisodeViewModel> episodes = new ArrayList<>();
            List<PodcastViewModel> podcasts = new ArrayList<>();
            List<PlaylistViewModel> playlists = new ArrayList<>();
            try {
               if(req.queryParams("q") != null && req.queryParams("q").length() > 0) {
                   List<model.Episode> episodesModels;
                   List<model.Podcast> podcastsModels;
                   List<model.Playlist> playlistsModels;

                   episodesModels = repository.Episode.find(req.queryParams("q"));
                   podcastsModels = repository.Podcast.find(req.queryParams("q"));
                   playlistsModels = repository.Playlist.find(req.queryParams("q"));

                   for (model.Episode episode : episodesModels) {
                       episodes.add(new EpisodeViewModel(episode));
                   }
                   for (model.Podcast podcast : podcastsModels) {
                       podcasts.add(new PodcastViewModel(podcast));
                   }
                   for (model.Playlist playlist : playlistsModels) {
                       playlists.add(new PlaylistViewModel(playlist));
                   }
               } else {
                   res.status(400);
                   return new BaseResponse(false, null);
               }
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new SearchResponse(episodes, podcasts, playlists));
        }, gson::toJson);

        options("/search", ((request, response) -> response));

    }
}
