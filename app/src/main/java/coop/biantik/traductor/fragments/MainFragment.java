package coop.biantik.traductor.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.activities.BaseActivity;
import coop.biantik.traductor.adapters.MainPagerAdapter;
import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Track;
import coop.biantik.traductor.services.PlayAudio;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.ErrorAnswerEvent;
import coop.biantik.traductor.utils.bus.LoadEventAnswerEvent;
import coop.biantik.traductor.utils.bus.SelectedLanguageAnswerEvent;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends BaseFragment {

    private static final String LOG_TAG = "MainFragment";

    private Event event;

    private IntentFilter startAudioFilter = new IntentFilter("coop.biantik.traductor.StartAudioBroadcast");
    private IntentFilter audioErrorFilter = new IntentFilter("coop.biantik.traductor.AudioErrorBroadcast");


    MainPagerAdapter mMainPagerAdapter;


    @Bind(R.id.main)
    View mainView;

    @Bind(R.id.pager)
    ViewPager mViewPager;


    @Bind(R.id.tab_layout)
    TabLayout mTabs;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.play_pause_button)
    FrameLayout playPauseButton;

    @Bind(R.id.play_button)
    ImageButton playButton;

    @Bind(R.id.pause_button)
    ImageButton pauseButton;


    private String selectedLanguage;

    private boolean languageChanged;

    private boolean isPlaying;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(startAudioReceiver, startAudioFilter);
        getActivity().registerReceiver(audioErrorReceiver, audioErrorFilter);
        if (isPlaying) {
            stopAudio();
            playAudio();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);
        playButton.setOnClickListener(playButtonListener);
        pauseButton.setOnClickListener(pauseButtonListener);

        Intent intent = getActivity().getIntent();
        selectedLanguage = intent.getStringExtra("playingLanguage");
        if (selectedLanguage != null) {
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            isPlaying = true;
        }

        createTabs();

        return v;
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(audioErrorReceiver);
        getActivity().unregisterReceiver(startAudioReceiver);
        super.onPause();
    }


    @Subscribe
    public void onTaskResult(LoadEventAnswerEvent result) {
        Event event = result.getEvent();
        this.event = event;
    }

    private void createTabs() {
        // Initilization

        mMainPagerAdapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), event);
        // Set up the ViewPager with the sections adapter.

        mViewPager.setAdapter(mMainPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        mViewPager.setCurrentItem(0);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    private View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (selectedLanguage != null) {
                playAudio();
            } else {
                UIUtils.showErrorMessage(getContext(), getString(R.string.error_select_language));
            }

        }
    };

    private View.OnClickListener pauseButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopAudio();
        }
    };

    private void playAudio() {


        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.initializing);
        String dataSource = getDataSource(selectedLanguage);

        Intent intent = new Intent(getActivity(), PlayAudio.class);
        intent.putExtra("dataSource", dataSource);
        intent.putExtra("playingLanguage", selectedLanguage);
        isPlaying = false;
        getActivity().startService(intent);
        new StartAudioAsyncTask().execute(selectedLanguage);


    }

    private String getDataSource(String selectedLanguage) {
        String dataSource = null;
        for (Track track : this.event.getLanguages()) {
            if (track.getCode().equals(selectedLanguage)) {
                dataSource = track.getStream();
                break;
            }
        }
        //dataSource = "rtsp://192.168.43.162:1935/live/prueba.stream";
        return dataSource;
    }


    private void stopAudio() {
        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.stopping);
        pauseButton.setVisibility(View.GONE);
        playButton.setVisibility(View.VISIBLE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent objIntent = new Intent(getActivity(), PlayAudio.class);
        getActivity().stopService(objIntent);
        new StopAudioAsyncTask().execute();
        isPlaying = false;
        progressDialog.dismiss();
    }


    @Subscribe
    public void onTaskResult(SelectedLanguageAnswerEvent result) {
        selectedLanguage = result.getSelectedLanguage();
        languageChanged = true;
        if (isPlaying) {
            stopAudio();
            playAudio();
        }


    }

    @Subscribe
    public void onTaskResult(ErrorAnswerEvent result) {

        snackBar = UIUtils.showSnackBarWithAction(mainView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        }, R.string.error_general, R.string.action_ok);

    }

    private BroadcastReceiver startAudioReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isPlaying = true;
            languageChanged = false;
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            progressDialog.hide();
        }
    };

    private BroadcastReceiver audioErrorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isPlaying = false;
            progressDialog.hide();
            snackBar = UIUtils.showSnackBarWithAction(mainView, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            }, R.string.error_general, R.string.action_ok);
        }
    };


    private class StartAudioAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            eventService.startTranslation(params[0]);
            return true;
        }
    }

    private class StopAudioAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            eventService.stopTranslation();
            return true;
        }
    }

}
