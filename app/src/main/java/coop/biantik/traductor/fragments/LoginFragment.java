package coop.biantik.traductor.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.User;
import coop.biantik.traductor.utils.SessionManager;
import coop.biantik.traductor.utils.UIUtils;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.username_edit_text)
    EditText usernameEditText;

    @Bind(R.id.btnLogin)
    Button btnLogin;

    @Bind({R.id.clickable_span})
    TextView conditionsView;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        btnLogin.setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnLogin) {
            UIUtils.closeKeyboard(getActivity());

            String username = usernameEditText.getText().toString();

            if (username.trim().length() > 0) {
                login(username);
            } else {
                UIUtils.showErrorMessage(getActivity(), getContext().getString(R.string.error_user_required));

            }
        }

    }


    private void login(String username) {

        new LoginUser(getActivity()).execute(username);

    }



    private class LoginUser extends AsyncTask<String, Void, User> {

        ProgressDialog progressDialog;
        Activity activity = null;

        LoginUser(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = UIUtils.showProgressMessage(activity, R.string.loading);
        }

        @Override
        protected User doInBackground(String... data) {
            User user = userService.login(data[0]);
            if (user != null) {
                SessionManager.getInstance(activity).setUser(user);
            }
            return user;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(User user) {
            if (isAdded()) {
                progressDialog.dismiss();
                if (user == null) {
                    UIUtils.showErrorMessage(getActivity(), R.string.no_login);
                } else {
                    getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
                    getActivity().finish();
                }
            }
        }
    }
}
