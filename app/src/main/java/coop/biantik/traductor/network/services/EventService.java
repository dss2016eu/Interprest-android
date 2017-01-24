package coop.biantik.traductor.network.services;

import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Info;
import retrofit.RestAdapter;


public interface EventService {

    void setRestAdapter(RestAdapter restAdapter);

	Info findInfo();

	Event findCurrent();

	void startTranslation(String lang);

	void stopTranslation();
}