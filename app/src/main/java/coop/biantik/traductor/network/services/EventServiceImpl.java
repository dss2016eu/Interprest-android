package coop.biantik.traductor.network.services;

import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Info;
import coop.biantik.traductor.network.daos.EventDao;
import retrofit.RestAdapter;

public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    public EventServiceImpl(RestAdapter restAdapter) {

        eventDao = restAdapter.create(EventDao.class);
    }

    public void setRestAdapter(RestAdapter restAdapter){
        eventDao = restAdapter.create(EventDao.class);
    }


    @Override
    public Info findInfo() {
        return eventDao.findInfo();
    }

    @Override
    public Event findCurrent() {
        return eventDao.findCurrent();
    }

    @Override
    public void startTranslation(String lang) {
        try {
            eventDao.startTranslation(lang);
        }catch(Exception e){
            ;
        }
    }

    @Override
    public void stopTranslation() {
        try {
            eventDao.stopTranslation();
        }catch(Exception e){
            ;
        }

    }
}