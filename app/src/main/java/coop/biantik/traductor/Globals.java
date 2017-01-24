package coop.biantik.traductor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;

import com.google.gson.Gson;

import coop.biantik.traductor.model.Event;

public class Globals {


    SharedPreferences pref;

    Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Globals";
    private static final String PREF_EVENT = "Event";
    private static final String PREF_IS_PRIVATE = "isPrivate";

    public Globals(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public Event getEvent() {
        Gson gson = new Gson();
        String json = pref.getString(PREF_EVENT, null);
        return gson.fromJson(json, Event.class);
    }

    public void setEvent(Event event) {
        Gson gson = new Gson();
        editor.putString(PREF_EVENT, gson.toJson(event));
        editor.commit();
    }

    public boolean getIsPrivate() {
        return pref.getBoolean(PREF_IS_PRIVATE, false);
    }

    public void setIsPrivate(boolean isPrivate) {
        Gson gson = new Gson();
        editor.putBoolean(PREF_IS_PRIVATE, isPrivate);
        editor.commit();
    }

    public Typeface getFontLight() {
        return Typeface.createFromAsset(context.getAssets(), "CharlevoixPro-Light.otf");
    }


}