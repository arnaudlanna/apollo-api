package controller;
import com.google.gson.Gson;
import javassist.NotFoundException;
import model.*;
import model.Episode;

import java.util.ArrayList;
import java.util.List;

import static utils.HTTPUtils.getUser;
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
                for (model.Playlist playlist1 : playlist) {
                    result.add(new PlaylistViewModel(playlist1));
                }
            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        post("/playlist", (req, res) -> {
            model.Playlist createdPlaylist;
            try {
                PlaylistViewModel playlist = gson.fromJson(req.body(), PlaylistViewModel.class);
                playlist.setUser_id(getUser(req));
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

        post("/playlist/add", (req, res) -> {
            model.PlaylistItem createdItem;
            try {
                PlaylistItemViewModel history = gson.fromJson(req.body(), PlaylistItemViewModel.class);
                createdItem = repository.Playlist.create(history);
            } catch (NotFoundException ex) {
                res.status(404);
                return new BaseResponse(false, "Episódio ou usuário não encontrados.");
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new PlaylistItemViewModel(createdItem));
        }, gson::toJson);

        post("/playlist/delete", (req, res) -> {
            model.PlaylistItem createdItem;
            try {
                PlaylistItemViewModel history = gson.fromJson(req.body(), PlaylistItemViewModel.class);
                repository.Playlist.delete(history);
            } catch (NotFoundException ex) {
                res.status(404);
                return new BaseResponse(false, "Episódio ou usuário não encontrados.");
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, "Removido");
        }, gson::toJson);

        get("/playlist/items/:id", (req, res) -> {
            List<PlaylistItemViewModel> result = new ArrayList<>();
            try {
                List<PlaylistItem> resu = repository.Playlist.getItems(Integer.parseInt(req.params(":id")));
                for (model.PlaylistItem playlistItem : resu) {
                    result.add(new PlaylistItemViewModel(playlistItem));
                }
            } catch (NotFoundException ex) {
                res.status(404);
                return new BaseResponse(false, "Episódio ou usuário não encontrados.");
            } catch (Exception ex) {
                ex.printStackTrace();
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        options("/playlist/:id", ((request, response) -> response));
        options("/playlist/items/:id", ((request, response) -> response));
        options("/playlist", ((request, response) -> response));
        options("/playlist/add", ((request, response) -> response));
        options("/playlist/delete", ((request, response) -> response));
        options("/playlist", ((request, response) -> response));

    }
}
