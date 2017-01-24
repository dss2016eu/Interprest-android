package coop.biantik.traductor.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.activities.AdminPostsActivity;
import coop.biantik.traductor.activities.TranslationActivity;
import coop.biantik.traductor.activities.TranslatorsActivity;
import coop.biantik.traductor.utils.UIUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdminMainFragment extends BaseFragment{

    private static final String LOG_TAG = "AdminMainFragment";

    public AdminMainFragment() {
    }

    @Bind(R.id.title_text)
    TextView titleText;

    @Bind(R.id.translators_text)
    TextView translatorsText;

    @Bind(R.id.translators_button)
    ImageButton translatorsButton;

    @Bind(R.id.translation_text)
    TextView translationText;

    @Bind(R.id.translation_button)
    ImageButton translationButton;

    @Bind(R.id.messages_text)
    TextView messagesText;

    @Bind(R.id.messages_button)
    ImageButton messagesButton;

    @Bind(R.id.help_text)
    TextView helpText;

    @Bind(R.id.help_button)
    ImageButton helpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_main, container, false);
        ButterKnife.bind(this, v);
        Typeface typeFace = globals.getFontLight();
        titleText.setTypeface(typeFace);
        translationText.setTypeface(typeFace);
        translationButton.setOnClickListener(translationButtonListener);
        translatorsText.setTypeface(typeFace);
        translatorsButton.setOnClickListener(translatorsButtonListener);
        messagesText.setTypeface(typeFace);
        messagesButton.setOnClickListener(messagesButtonListener);
        helpText.setTypeface(typeFace);
        helpButton.setOnClickListener(helpButtonListener);

        return v;
    }

    private View.OnClickListener translationButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), TranslationActivity.class));
        }
    };

    private View.OnClickListener translatorsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), TranslatorsActivity.class));
        }
    };

    private View.OnClickListener messagesButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), AdminPostsActivity.class));
        }
    };

    private View.OnClickListener helpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UIUtils.showMessage(getActivity(), getActivity().getString(R.string.to_do));
        }
    };
}
