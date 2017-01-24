package coop.biantik.traductor.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import coop.biantik.traductor.Application;
import coop.biantik.traductor.Globals;
import coop.biantik.traductor.network.services.EventService;
import coop.biantik.traductor.network.services.GeoService;
import coop.biantik.traductor.network.services.PostService;
import coop.biantik.traductor.network.services.UserService;
import coop.biantik.traductor.utils.SessionManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment {

    @Inject
    Bus bus;

    @Inject
    PostService postService;

    @Inject
    EventService eventService;

    @Inject
    UserService userService;

    @Inject
    GeoService geoService;

    @Inject
    Globals globals;

    @Inject
    SessionManager sessionManager;

    Snackbar snackBar;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application)getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }


}