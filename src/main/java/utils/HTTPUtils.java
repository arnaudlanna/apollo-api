package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import model.BaseResponse;
import spark.Request;

public class HTTPUtils {

    static Exception AcessoNegado;

    public static Integer getUser(Request req) throws Exception {
        if (req.headers("Authorization") == null || req.headers("Authorization").isEmpty()){
            throw AcessoNegado;
        }
        String token = req.headers("Authorization").split(" ")[1];
        if (token.isEmpty()) {
            throw AcessoNegado;
        }
        Algorithm algorithm = Algorithm.HMAC256("ap0ll0s3cret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Apollo")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        Integer user = Integer.parseInt(jwt.getSubject());

        return user;
    }
}
