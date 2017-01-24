package coop.biantik.traductor;

import android.support.multidex.MultiDexApplication;

import coop.biantik.traductor.network.NetworkModule;

public class Application extends MultiDexApplication {


    private ApplicationComponent applicationComponent;


	@Override
	public void onCreate() {
		super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext())).networkModule(new NetworkModule(getApplicationContext())).build();
        applicationComponent.inject(this);
	}


    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

}
