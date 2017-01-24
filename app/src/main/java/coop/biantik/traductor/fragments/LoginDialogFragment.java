package coop.biantik.traductor.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.utils.UIUtils;


public class LoginDialogFragment extends BaseDialogFragment {

    @Bind(R.id.username_edit_text)
    EditText usernameEditText;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static LoginDialogFragment newInstance(String param1, String param2) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        return fragment;
    }

    public LoginDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_login_dialog, null);
        ButterKnife.bind(this, v);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.action_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        UIUtils.closeKeyboard(getActivity());
                        String username = usernameEditText.getText().toString().trim();

                        if (username.trim().length() > 0) {

                            mListener.onLoginSucceed(username);
                            LoginDialogFragment.this.getDialog().cancel();
                        } else {
                            UIUtils.showErrorMessage(getActivity(), R.string.error_user_required);

                        }
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLoginSucceed(String username);
    }





}
