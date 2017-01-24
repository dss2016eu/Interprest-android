package coop.biantik.traductor.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.adapters.AdminPostsAdapter;
import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import coop.biantik.traductor.network.enums.PostStatusEnum;
import coop.biantik.traductor.utils.NetworkUtils;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.LoadPostsAnswerEvent;
import coop.biantik.traductor.utils.bus.SendPostAnswerEvent;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdminPostsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "AdminPostsFragment";

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.posts_recycler_view)
    RecyclerView mRecyclerView;

    ProgressDialog progressDialog;

    private AdminPostsAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private int page = 1;

    private int pageSize;

    public AdminPostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageSize = getResources().getInteger(R.integer.page_size);
        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.loading);
        loadPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_posts, container, false);
        ButterKnife.bind(this, v);


        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);


        mAdapter = new AdminPostsAdapter(new ArrayList<Post>(), globals, bus);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);


        return v;
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView.OnScrollListener recyclerOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        page = page + 1;
                        loadPosts();
                    }
                }
            }

        }


    };

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
        page = 1;
        loadPosts();
    }


    private void loadPosts() {

        if (NetworkUtils.isNetworkAvailable(getActivity())) {
            new LoadPostsAsyncTask().execute();
        }
    }


    private class LoadPostsAsyncTask extends AsyncTask<String, Void, List<Post>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Post> doInBackground(String... params) {

            PostCriteria criteria = new PostCriteria();
            criteria.setPage(page);
            criteria.setSize(getResources().getInteger(R.integer.page_size));
            criteria.setLanguage(Locale.getDefault().getLanguage());
            criteria.setStatuses(Arrays.asList(PostStatusEnum.PUBLISHED.getValue(), PostStatusEnum.PREPARED.getValue()));

            Log.i(LOG_TAG, "criteriaJson:" + new Gson().toJson(criteria));
            return postService.findAllByCriteria(criteria);
        }

        @Override
        protected void onPostExecute(List<Post> posts) {

            if (isAdded()) {
                bus.post(new LoadPostsAnswerEvent(posts));
            }

        }

    }

    @Subscribe
    public void answerAvailable(LoadPostsAnswerEvent event) {
        progressDialog.dismiss();
        List<Post> posts = event.getPosts();
        if (posts != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.updateList(posts);

            } else {
                loading = true;
                mAdapter.getmPosts().addAll(posts);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void sendPostAvailable(final SendPostAnswerEvent event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new SendPostAsyncTask(getActivity(), event.getPost()).execute();

            }
        });
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setTitle(getActivity().getString(R.string.send_message_title));
        builder.setMessage(R.string.send_message_description);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class SendPostAsyncTask extends AsyncTask<Void, Void, Post> {

        Context context;
        Post post;

        SendPostAsyncTask(Context context, Post post){
            this.context = context;
            this.post = post;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = UIUtils.showProgressMessage(context, R.string.saving);
        }

        @Override
        protected Post doInBackground(Void... params) {


            return postService.send(this.post.getId());
        }

        @Override
        protected void onPostExecute(Post response) {

            if (isAdded()){
                progressDialog.dismiss();
                if (response != null) {
                    post.setStatus(response.getStatus());
                    mAdapter.notifyDataSetChanged();
                }
            }

        }

    }
}
