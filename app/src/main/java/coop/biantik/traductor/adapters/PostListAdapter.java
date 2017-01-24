package coop.biantik.traductor.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.activities.PhotoViewActivity;
import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.utils.DateTimeUtils;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private Context context;

    public List<Post> getmPostList() {
        return mPostList;
    }

    private List<Post> mPostList;

    private Boolean isPrivate;

    public PostListAdapter(List<Post> postList, Boolean isPrivate) {
        this.mPostList = postList;
        this.isPrivate = isPrivate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "CharlevoixPro-Light.otf");
        holder.title.setTypeface(typeFace);
        holder.title.setText(post.getTitle());
        holder.description.setTypeface(typeFace);
        holder.description.setText(post.getDescription());
        holder.date.setTypeface(typeFace);
        holder.date.setText(DateTimeUtils.getPostDisplayTime(context, post.getUpdatedAt(), Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime()));
        holder.image.setOnClickListener(imageOnClickListener);
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            String server =  context.getString(R.string.server_public_url);
            if (isPrivate) server = context.getString(R.string.server_private_url);
            Log.i("Server", server);
            Picasso.with(context).load(server + post.getImage()).into(holder.image);
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setTag(post.getImage());
        }
    }

    public void updateList(List<Post> posts) {
        mPostList = posts;
        notifyDataSetChanged();
    }

    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            Intent i = new Intent(context, PhotoViewActivity.class);
            i.putExtra("url", (String) v.getTag());
            context.startActivity(i);

        }
    };

    @Override
    public int getItemCount() {
        return mPostList.size();
    }


    public Post getItem(int position) {
        return mPostList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_post_title)
        TextView title;

        @Bind(R.id.item_post_description)
        TextView description;

        @Bind(R.id.item_post_date)
        TextView date;


        @Bind(R.id.item_post_image)
        ImageView image;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
