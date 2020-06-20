package controller;
import com.google.gson.Gson;
import javassist.NotFoundException;
import model.BaseResponse;
import model.EpisodeViewModel;
import model.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

import static utils.HTTPUtils.getUser;
import static spark.Spark.*;

public class History {
    public static void routes() {
        Gson gson = new Gson();

        get("/history", (req, res) -> {
            List<HistoryViewModel> result = new ArrayList<>();
            try {
                List<model.History> historyresult = repository.History.byUserId(getUser(req));
                for (model.History history : historyresult) {
                    result.add(new HistoryViewModel(history));
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
                createdHistory = repository.History.createOrUpdate(history);
            } catch (NotFoundException ex) {
                res.status(404);
                return new BaseResponse(false, "Episódio ou usuário não encontrados.");
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new HistoryViewModel(createdHistory));
        }, gson::toJson);

        put("/history", (req, res) -> {
            model.History createdHistory;
            try {
                HistoryViewModel history = gson.fromJson(req.body(), HistoryViewModel.class);
                history.setUser_id(getUser(req));
                createdHistory = repository.History.createOrUpdate(history);
            } catch (NotFoundException ex) {
                res.status(404);
                return new BaseResponse(false, "Episódio ou usuário não encontrados.");
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new HistoryViewModel(createdHistory));
        }, gson::toJson);

        options("/history", ((request, response) -> response));
    }
}
