package coop.biantik.traductor;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import coop.biantik.traductor.utils.SessionManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Provides @Singleton
    public SessionManager provideSessionManager() {
        return SessionManager.getInstance(context);
    }



    @Provides @Singleton
    public Globals provideGlobals() {
        return new Globals(context);
    }

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }




}