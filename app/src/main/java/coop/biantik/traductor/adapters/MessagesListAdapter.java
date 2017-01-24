package coop.biantik.traductor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.Post;

public class MessagesListAdapter extends  RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    private Context context;

    private List<Post> mPostList;

    public MessagesListAdapter(List<Post> postList) {
        this.mPostList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        holder.title.setText(post.getTitle());
        Picasso.with(context).load(post.getImage()).into(holder.image);
    }

    public void updateList(List<Post> posts) {
        mPostList = posts;
        notifyDataSetChanged();
    }

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
        @Bind(R.id.item_post_image)
        ImageView image;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
