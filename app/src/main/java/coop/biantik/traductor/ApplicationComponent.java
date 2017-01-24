package coop.biantik.traductor;

import javax.inject.Singleton;

import coop.biantik.traductor.activities.BaseActivity;
import coop.biantik.traductor.fragments.BaseDialogFragment;
import coop.biantik.traductor.fragments.BaseFragment;
import coop.biantik.traductor.network.NetworkModule;
import coop.biantik.traductor.services.PlayAudio;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(Application application);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(BaseDialogFragment baseDialogFragment);

    void inject(PlayAudio playAudio);

}