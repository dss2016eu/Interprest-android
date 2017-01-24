package coop.biantik.traductor.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.squareup.otto.Bus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.Track;
import coop.biantik.traductor.utils.bus.SelectedLanguageAnswerEvent;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private Context context;

    private List<Track> tracks;

    private int selectedPosition = -1;

    private int originalFontColor;

    private String selectedLanguage;

    private Bus bus;

    public TrackListAdapter(List<Track> tracks, Bus bus, String selectedLanguage) {
        this.tracks = tracks;
        this.bus = bus;
        this.selectedLanguage = selectedLanguage;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = tracks.get(position);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "CharlevoixPro-Medium.otf");
        holder.languageButton.setTypeface(typeFace);
        holder.languageButton.setText(track.getCode());
        holder.languageButton.setOnClickListener(selectTrackOnClickListener);
        holder.languageButton.setTag(position);
        if (position == getSelectedPosition() || (selectedLanguage !=null && selectedLanguage.equals(track.getCode()))){
            selectedLanguage = null;
            holder.languageButton.setBackgroundResource(R.color.primary);
            holder.languageButton.setTextColor(Color.WHITE);
        }
        else{
            holder.languageButton.setBackgroundResource(R.drawable.rect_primary_color);
            holder.languageButton.setTextColor(ContextCompat.getColorStateList(context, R.color.gray_dark));
        }
        if (position % 2 != 0){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.languageButton.getLayoutParams();

            params.gravity = Gravity.LEFT;

            holder.languageButton.setLayoutParams(params);
        }

    }

    View.OnClickListener selectTrackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            selectedPosition = (Integer) v.getTag();
            notifyDataSetChanged();
            bus.post(new SelectedLanguageAnswerEvent(getItem((Integer)v.getTag()).getCode()));

        }
    };

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void updateList(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tracks == null ? 0 : tracks.size();
    }


    public Track getItem(int position) {
        return tracks.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_language)
        Button languageButton;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


}
