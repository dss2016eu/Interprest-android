package coop.biantik.traductor.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.adapters.TrackListAdapter;
import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.Track;
import coop.biantik.traductor.utils.bus.LoadEventAnswerEvent;


public class ConferenceFragment extends BaseFragment {


    private static final String LOG_TAG = "ConferenceFragment";

    @Bind(R.id.languages)
    RecyclerView mRecyclerView;

    @Bind(R.id.languages_layout)
    LinearLayout mLanguagesLayout;

    @Bind(R.id.no_languages_available_layout)
    View mNoLanguagesView;

    @Bind(R.id.title_text)
    TextView titleText;

    @Bind(R.id.no_event)
    TextView noEventText;

    private TrackListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private Event event;


    // TODO: Rename and change types and number of parameters
    public static ConferenceFragment newInstance() {
        ConferenceFragment fragment = new ConferenceFragment();
        return fragment;
    }

    public ConferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_conference, container, false);
        ButterKnife.bind(this, v);

        Intent intent = getActivity().getIntent();
        String selectedLanguage = intent.getStringExtra("playingLanguage");

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addOnScrollListener(new OnScrollListener());

        mAdapter = new TrackListAdapter(new ArrayList<Track>(), bus, selectedLanguage);
        mRecyclerView.setAdapter(mAdapter);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "CharlevoixPro-Medium.otf");
        titleText.setTypeface(typeFace);
        noEventText.setTypeface(typeFace);


        return v;
    }


    private class OnScrollListener extends RecyclerView.OnScrollListener {

    }

    @Produce
    public String produceEvent() {
        return "Starting up";
    }


    @Subscribe
    public void onTaskResult(LoadEventAnswerEvent result) {
        Event event = result.getEvent();
        this.event = event;
        if (event == null) {
            mLanguagesLayout.setVisibility(View.GONE);
            noEventText.setVisibility(View.VISIBLE);
        } else {
            mAdapter.updateList(event.getLanguages());
            mLanguagesLayout.setVisibility(View.VISIBLE);
            noEventText.setVisibility(View.GONE);
            mNoLanguagesView.setVisibility(event.getLanguages() == null || event.getLanguages().size() == 0 ? View.VISIBLE : View.GONE);
            mLanguagesLayout.setVisibility(View.VISIBLE);
        }
    }



}
