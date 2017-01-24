package coop.biantik.traductor.network.daos;

import coop.biantik.traductor.network.beans.maps.GeoResponse;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GeoDao {

    @GET("/maps/api/geocode/json?ka&sensor=false")
    GeoResponse findLocationByAddress(@Query("address") String address);

    @GET("/maps/api/geocode/json?ka&sensor=false")
    GeoResponse findLocationByCoordinates(@Query("latlng") String coordinates);

}

