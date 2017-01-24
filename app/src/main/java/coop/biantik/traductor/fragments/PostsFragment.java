package coop.biantik.traductor.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.adapters.PostListAdapter;
import coop.biantik.traductor.listeners.RecyclerItemClickListener;
import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import coop.biantik.traductor.network.enums.PostStatusEnum;
import coop.biantik.traductor.utils.NetworkUtils;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.LoadPostsAnswerEvent;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "PostsFragment";



    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.fragment_job_list_recycler_view)
    RecyclerView mRecyclerView;


    @Bind(R.id.no_messages_text)
    TextView mNoMessagesText;

    private PostListAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private Button mButtonStart;

    private int page = 1;

    private boolean isRefreshing = false;

    private int pageSize;

    ProgressDialog progressDialog;

    public PostsFragment() {
    }

    public static PostsFragment newInstance() {
        PostsFragment fragment = new PostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageSize = getResources().getInteger(R.integer.page_size);
        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.loading);
        final Handler h = new Handler();
        final int delay = getResources().getInteger(R.integer.posts_refresh); //milliseconds
        onRefresh();
        h.postDelayed(new Runnable(){
            public void run(){
                onRefresh();
                h.postDelayed(this, delay);
            }
        }, delay);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, v);


        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new PostListAdapter(new ArrayList<Post>(), globals.getIsPrivate());
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
        mRecyclerView.addOnScrollListener(recyclerOnScrollListener);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "CharlevoixPro-Medium.otf");
        mNoMessagesText.setTypeface(typeFace);

        return v;
    }


    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {


        }
    });


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
                    Log.i(LOG_TAG, "" + visibleItemCount + ":" + pastVisiblesItems + ":" + totalItemCount);
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.i(LOG_TAG, "aqui entramos");
                        page = page + 1;
                        loadPosts();
                    }
                }
            }

        }


    };


    @Override
    public void onRefresh() {
        isRefreshing = true;
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
            criteria.setSize(pageSize);
            criteria.setLanguage(Locale.getDefault().getLanguage());
            criteria.setStatuses(Arrays.asList(PostStatusEnum.PUBLISHED.getValue()));

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
    public void onTaskResult(LoadPostsAnswerEvent result) {

        progressDialog.dismiss();
        List<Post> posts = result.getPosts();
        if (posts != null) {
            if (isRefreshing) {
                isRefreshing = false;
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.updateList(posts);

            } else {
                loading = true;
                mAdapter.getmPostList().addAll(posts);
            }
        }
        mAdapter.notifyDataSetChanged();
        if (posts != null && posts.size() > 0) {
            mNoMessagesText.setVisibility(View.GONE);
        } else {
            mNoMessagesText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
