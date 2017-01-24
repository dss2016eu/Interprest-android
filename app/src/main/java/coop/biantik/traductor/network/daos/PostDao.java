package coop.biantik.traductor.network.daos;


import java.util.List;

import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface PostDao {

    @POST("/api/v1/posts")
    List<Post> findPosts(@Body PostCriteria postCriteria);


    @GET("/api/v1/post/{id}/send")
    Post sendPost(@Path("id") String id);

}