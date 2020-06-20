package controller;
import com.google.gson.Gson;
import model.BaseResponse;
import model.PlaylistViewModel;
import model.PodcastViewModel;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Playlist {
    public static void routes() {
        Gson gson = new Gson();

        get("/playlist", (req, res) -> {
            List<PlaylistViewModel> result = new ArrayList<>();
            try {
                List<model.Playlist> playlist = new ArrayList<>();
                if(req.queryParams("id") != null) {
                    playlist.add(repository.Playlist.byId(Integer.parseInt(req.queryParams("id"))));
                } else if(req.queryParams("user_id") != null) {
                    playlist = repository.Playlist.byUserId(Integer.parseInt(req.queryParams("user_id")));
                } else if(req.queryParams("q") != null) {
                    playlist = repository.Playlist.find(req.queryParams("q"));
                } else {
                    playlist = repository.Playlist.list();
                }

            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        post("/playlist", (req, res) -> {
            PlaylistViewModel playlist = gson.fromJson(req.body(), PlaylistViewModel.class);
            model.Playlist createdPlaylist;
            try {
                createdPlaylist = repository.Playlist.create(playlist);
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new PlaylistViewModel(createdPlaylist));
        }, gson::toJson);

        delete("/playlist/:id", (req, res) -> {
            try {
                repository.Playlist.delete(Integer.parseInt(req.params(":id")));
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, null);
        }, gson::toJson);

        options("/playlist/:id", ((request, response) -> response));

    }
}
