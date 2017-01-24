package coop.biantik.traductor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Bus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.Globals;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.enums.PostStatusEnum;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.SendPostAnswerEvent;

public class AdminPostsAdapter extends RecyclerView.Adapter<AdminPostsAdapter.ViewHolder> {

    private Context context;

    private Globals globals;

    private List<Post> mPosts;

    private Bus bus;

    public AdminPostsAdapter(List<Post> posts, Globals globals, Bus bus) {
        this.mPosts = posts;
        this.globals = globals;
        this.bus = bus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin_post, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.title.setTypeface(globals.getFontLight());
        holder.description.setTypeface(globals.getFontLight());
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.sendButton.setOnClickListener(sendOnClickListener);
        holder.sendButton.setTag(position);
        holder.check.setVisibility(post.getStatus().equals(PostStatusEnum.PUBLISHED) ? View.VISIBLE : View.INVISIBLE);
    }

    public List<Post> getmPosts() {
        return mPosts;
    }

    View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            UIUtils.showMessage(context, R.string.action_delete);

        }
    };
    View.OnClickListener sendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            bus.post(new SendPostAnswerEvent(mPosts.get((Integer) v.getTag())));



        }
    };

    public void updateList(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public Post getItem(int position) {
        return mPosts.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_post_title)
        TextView title;

        @Bind(R.id.item_post_description)
        TextView description;

        @Bind(R.id.item_post_check)
        ImageView check;

        @Bind(R.id.send_button)
        ImageButton sendButton;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


}
