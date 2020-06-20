package controller;

import com.google.gson.Gson;
import model.BaseResponse;

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
    }
}
