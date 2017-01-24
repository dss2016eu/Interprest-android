package coop.biantik.traductor.network.daos;


import java.util.List;

import coop.biantik.traductor.model.User;
import coop.biantik.traductor.network.beans.Login;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface UserDao {

    @GET("/api/v1/users/login/{user}")
    User findOne(@Path("user") String user);

    @GET("/api/v1/user/me")
    User findMe();

    @POST("/api/v1/users/login")
    User login(@Body Login login);

    @GET("/api/v1/users/translators")
    List<User> findTranslators();

}