package coop.biantik.traductor.network.services;


import coop.biantik.traductor.model.Location;

public interface GeoService {

	 Location findLocationByAddress(String address);

	 Location findLocationByCoordinates(Location location);

}