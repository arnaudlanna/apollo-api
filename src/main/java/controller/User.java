package controller;

import com.google.gson.Gson;
import model.BaseResponse;
import model.UserViewModel;

import static utils.HTTPUtils.getUser;
import static spark.Spark.options;
import static spark.Spark.post;

public class User {

    public static void routes() {
        Gson gson = new Gson();

        post("/login", (req, res) -> {
            model.User user = gson.fromJson(req.body(), model.User.class);
            String token;
            try {
                token = repository.User.login(user.getEmail(), user.getPassword());
            } catch (Exception ex) {
                res.status(401);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, token);
        }, gson::toJson);

        post("/signup", (req, res) -> {
            model.User user = gson.fromJson(req.body(), model.User.class);
            String token;
            try {
                token = repository.User.signup(user);
            } catch (Exception ex) {
                res.status(400);
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, token);
        }, gson::toJson);

        post("/update", (req, res) -> {
            model.User updatedUser;
            try {
                model.User user = gson.fromJson(req.body(), model.User.class);
                user.setId(getUser(req));
                updatedUser = repository.User.update(user);
            } catch (Exception ex) {
                return new BaseResponse(false, null);
            }
            return new BaseResponse(true, new UserViewModel(updatedUser));
        }, gson::toJson);

        options("/login", ((request, response) -> response));
        options("/signup", ((request, response) -> response));
        options("/update", ((request, response) -> response));
    }
}
