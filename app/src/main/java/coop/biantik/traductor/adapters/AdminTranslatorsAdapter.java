package coop.biantik.traductor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.Globals;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.User;

public class AdminTranslatorsAdapter extends  RecyclerView.Adapter<AdminTranslatorsAdapter.ViewHolder> {

    private Context context;

    private Globals globals;

    private List<User> mTranslators;

    public AdminTranslatorsAdapter(List<User> translators, Globals globals) {
        this.mTranslators = translators;
        this.globals = globals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin_translator, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mTranslators.get(position);
        holder.username.setTypeface(globals.getFontLight());
        holder.username.setText(user.getUsername());
        holder.language.setTypeface(globals.getFontLight());
        holder.language.setText(user.getTranslationLanguage());
    }

    public void updateList(List<User> translators) {
        mTranslators = translators;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTranslators.size();
    }


    public User getItem(int position) {
        return mTranslators.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.username)
        TextView username;

        @Bind(R.id.language)
        TextView language;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
