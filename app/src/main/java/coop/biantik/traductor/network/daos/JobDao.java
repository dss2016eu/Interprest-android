package coop.biantik.traductor.network.daos;

import coop.biantik.traductor.network.beans.Response;
import retrofit.http.GET;

public interface JobDao {

    @GET("/jobs")
    Response findAll();


 }