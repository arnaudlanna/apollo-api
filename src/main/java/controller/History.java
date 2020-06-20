package controller;
import com.google.gson.Gson;
import model.BaseResponse;
import model.HistoryViewModel;
import model.PlaylistViewModel;

import java.util.ArrayList;
import java.util.List;

import static controller.Utils.getUser;
import static spark.Spark.*;

public class History {
    public static void routes() {
        Gson gson = new Gson();

        get("/history", (req, res) -> {
            List<HistoryViewModel> result = new ArrayList<>();
            try {
                List<model.History> history = new ArrayList<>();
                if(req.queryParams("id") != null) {
                    history.add(repository.History.byId(Integer.parseInt(req.queryParams("id"))));
                } else if(req.queryParams("user_id") != null) {
                    history = repository.History.byUserId(Integer.parseInt(req.queryParams("user_id")));
                } else if(req.queryParams("episode_id") != null) {
                    history = repository.History.byEpisodeId(Integer.parseInt(req.queryParams("episode_id")));
            } else {
                    history = repository.History.list();
                }

            } catch (Exception ex) {
                System.out.println(ex);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, result);
        }, gson::toJson);

        post("/history", (req, res) -> {
            model.History createdHistory;
            try {
                HistoryViewModel history = gson.fromJson(req.body(), HistoryViewModel.class);
                history.setUser_id(getUser(req));
                createdHistory = repository.History.create(history);
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new HistoryViewModel(createdHistory));
        }, gson::toJson);
    }
}
