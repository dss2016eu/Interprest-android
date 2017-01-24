package coop.biantik.traductor.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.adapters.AdminTranslatorsAdapter;
import coop.biantik.traductor.model.User;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.TranslatorsAnswerEvent;

/**
 * A placeholder fragment containing a simple view.
 */
public class TranslatorsFragment extends BaseFragment {


    @Bind(R.id.title_text)
    TextView titleText;

    @Bind(R.id.translators_recycler_view)
    RecyclerView mRecyclerView;

    private AdminTranslatorsAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog progressDialog;

    public TranslatorsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_translators, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new AdminTranslatorsAdapter(new ArrayList<User>(), globals);
        mRecyclerView.setAdapter(mAdapter);
        titleText.setTypeface(globals.getFontLight());
        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.loading);
        new LoadTranslatorsTask().execute();
        return v;
    }

    private class LoadTranslatorsTask extends AsyncTask<Void, Void, List<User>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground(Void... params) {

            return userService.findTranslators();
        }

        @Override
        protected void onPostExecute(List<User> translators) {

            if (isAdded()) {
                bus.post(new TranslatorsAnswerEvent(translators));

            }
        }
    }
    @Subscribe
    public void answerAvailable(TranslatorsAnswerEvent event) {
        progressDialog.dismiss();
        List<User> translators  = event.getTranslators();
        mAdapter.updateList(translators);
        mAdapter.notifyDataSetChanged();

    }

}
