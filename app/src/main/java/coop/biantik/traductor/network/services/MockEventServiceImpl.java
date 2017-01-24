package coop.biantik.traductor.network.services;

import java.util.ArrayList;
import java.util.List;

import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Info;
import coop.biantik.traductor.model.Track;
import retrofit.RestAdapter;


public class MockEventServiceImpl implements EventService {


    @Override
    public void setRestAdapter(RestAdapter restAdapter) {

    }

    @Override
    public Info findInfo() {
        return null;
    }

    @Override
    public Event findCurrent() {
        Event event = new Event();
        event.setName("Presentación de Interprest");
        event.setDescription("Interprest es la primera app para traducción de eventos.....");
        List<Track> tracks = new ArrayList<>();
        Track track1 = new Track();
        track1.setCode("eu");
        track1.setName("Euskera");
        track1.setUrl("rtsp://192.168.1.130:1935/live/eu.stream");
        Track track2 = new Track();
        track2.setCode("es");
        track2.setName("Castellano");
        track2.setUrl("rtsp://192.168.1.130:1935/live/es.stream");
        Track track3 = new Track();
        track3.setCode("fr");
        track3.setName("Francés");
        track3.setUrl("rtsp://192.168.1.130:1935/live/fr.stream");

        tracks.add(track1);
        tracks.add(track2);
        tracks.add(track3);
        event.setLanguages(tracks);
        return event;
    }

    @Override
    public void startTranslation(String lang) {

    }

    @Override
    public void stopTranslation() {

    }
}