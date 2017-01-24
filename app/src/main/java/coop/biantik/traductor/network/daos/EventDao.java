package coop.biantik.traductor.network.daos;


import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Info;
import coop.biantik.traductor.network.beans.JsonResponse;
import retrofit.http.GET;
import retrofit.http.Path;


public interface EventDao {

    @GET("/api/v1/info")
    Info findInfo();

    @GET("/api/v1/events/current")
    Event findCurrent();

    @GET("/api/v1/play/{lang}")
    JsonResponse startTranslation(@Path("lang") String lang);

    @GET("/api/v1/stop")
    JsonResponse stopTranslation();

}