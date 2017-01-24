package coop.biantik.traductor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.fragments.LoginDialogFragment;
import coop.biantik.traductor.model.Event;
import coop.biantik.traductor.model.User;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.ErrorAnswerEvent;
import coop.biantik.traductor.utils.bus.LoadEventAnswerEvent;
import coop.biantik.traductor.utils.bus.LoginAnswerEvent;

public class MainActivity extends BaseActivity implements LoginDialogFragment.OnFragmentInteractionListener {

    private boolean doubleBackToExitPressedOnce = false;

    private static final int REQUEST_WIFI_CONNECT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        loadEvent();


    }

    private void loadEvent() {
        new LoadEventAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (globals.getIsPrivate()) {
            if (!sessionManager.isLoggedIn()){
                menu.removeItem(R.id.action_close_session);
            }
        } else {
            menu.removeItem(R.id.action_login);
            menu.removeItem(R.id.action_close_session);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.action_refresh:
                loadEvent();
                return true;
            case R.id.action_login:

                if (sessionManager.isLoggedIn()) {
                    new LoginAsyncTask(sessionManager.getUser().getUsername()).execute();

                } else {
                    showLoginDialog();
                }
                return true;
            case R.id.action_close_session:
                sessionManager.logoutUser();
                i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            case R.id.action_settings:
                i = new Intent(this, PreferenceActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_WIFI_CONNECT && resultCode == Activity.RESULT_OK) {
            Intent i = null;
            User user = sessionManager.getUser();
            if (user.isAdmin())
                i = new Intent(this, AdminMainActivity.class);
            else if (user.isTranslator())
                i = new Intent(this, TranslationActivity.class);
            if (i != null) startActivity(i);
        } else {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, result);
    }


    @Subscribe
    public void onTaskResult(LoginAnswerEvent event) {
        User user = event.getUser();
        if (user == null) {
            UIUtils.showErrorMessage(this, R.string.no_login);
        } else {
            sessionManager.setUser(user);
            Intent i = new Intent(MainActivity.this, WifiConnectActivity.class);
            i.putExtra("IS_WIFI_SECURE", true);
            startActivityForResult(i, REQUEST_WIFI_CONNECT);
        }
    }

    private void showLoginDialog() {

        LoginDialogFragment loginFragment = new LoginDialogFragment();
        loginFragment.show(getFragmentManager(), getResources().getString(R.string.action_login));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                if (doubleBackToExitPressedOnce) {
                    finish();
                    return super.onKeyDown(keyCode, event);
                }

                this.doubleBackToExitPressedOnce = true;
                UIUtils.showErrorMessage(this, R.string.action_back_twice);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLoginSucceed(String username) {

        new LoginAsyncTask(username).execute();

    }


    public class LoadEventAsyncTask extends AsyncTask<Void, Void, Event> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Event doInBackground(Void... params) {

            Event event = null;
            try {
                event = eventService.findCurrent();
            } catch (Throwable e) {
                bus.post(new ErrorAnswerEvent(1));
            }


            return event;
        }

        @Override
        protected void onPostExecute(Event event) {
            bus.post(new LoadEventAnswerEvent(event));
        }
    }


    private class LoginAsyncTask extends AsyncTask<Void, Void, User> {


        private String username;

        LoginAsyncTask(String username) {
            this.username = username;
        }


        @Override
        protected User doInBackground(Void... data) {
            User user = userService.login(username);
            return user;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(User user) {
            bus.post(new LoginAnswerEvent(user));
        }
    }
}
