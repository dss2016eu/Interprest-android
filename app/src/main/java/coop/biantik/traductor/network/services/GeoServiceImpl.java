package coop.biantik.traductor.network.services;

import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import coop.biantik.traductor.model.Location;
import coop.biantik.traductor.network.beans.maps.GeoResponse;
import coop.biantik.traductor.network.daos.GeoDao;
import retrofit.RestAdapter;

public class GeoServiceImpl implements GeoService {

    private static final String GOOGLE_URL = "https://maps.google.com";

    private GeoDao geoDao;

    private Context context;

    public GeoServiceImpl(Context context) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(GOOGLE_URL).build();
        geoDao = restAdapter.create(GeoDao.class);
    }


    @Override
    public Location findLocationByAddress(String address) {
        Location location = null;

        try {

            address = URLEncoder.encode(address, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            return null;
        }

        GeoResponse geoResponse = geoDao.findLocationByAddress(address);

        location = formatGeoResponse(location, geoResponse);

        return location;
    }

    @Override
    public Location findLocationByCoordinates(Location location) {

        String coordinates = null;

        coordinates = location.getY() + "," + location.getX();


        GeoResponse geoResponse = geoDao.findLocationByCoordinates(coordinates);

        location = formatGeoResponse(location, geoResponse);
        Log.i("georesponse", "geo" + location.getTag());
        return location;
    }

    private Location formatGeoResponse(Location location, GeoResponse geoResponse) {
        if (geoResponse.getResults() != null && geoResponse.getResults().size() > 0) {

            double longitude = geoResponse.getResults().get(0).getGeometry().getLocation().getLng();
            double latitude = geoResponse.getResults().get(0).getGeometry().getLocation().getLat();

            location = new Location(latitude, longitude);
            location.setTag(geoResponse.getResults().get(0).getFormattedAddress());
        }
        return location;
    }

}