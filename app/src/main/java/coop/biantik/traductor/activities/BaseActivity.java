package coop.biantik.traductor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.Bind;
import coop.biantik.traductor.Application;
import coop.biantik.traductor.Globals;
import coop.biantik.traductor.R;
import coop.biantik.traductor.network.services.EventService;
import coop.biantik.traductor.network.services.PostService;
import coop.biantik.traductor.network.services.UserService;
import coop.biantik.traductor.utils.SessionManager;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.ErrorAnswerEvent;


public class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN = 999;

    private static final String LOG_TAG = "BaseActivity";

    @Inject
    Globals globals;

    @Inject
    Bus bus;

    @Inject
    Gson gson;

    @Inject
    EventService eventService;

    @Inject
    PostService postService;

    @Inject
    UserService userService;

    @Inject
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                CharSequence charSequence = "Lets See if it Works !!!" + paramThrowable.toString();
                Log.e("vaya mierda: ", paramThread.toString());
            }
        });
        */

        ((Application) getApplication()).getComponent().inject(this);

        setupActionBar();
        getWindow().setSoftInputMode(EditorInfo.IME_ACTION_DONE);
        //Hack para evitar tener que utilizar botón fisico cuando oveflow de ActionBar
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo_top);
        actionBar.setLogo(R.drawable.logo_top);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_LOGIN && resultCode == Activity.RESULT_OK && null != data) {
            finish();
            startActivity(getIntent());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final String[] secureActivities = {"UserActivity", "ChatListActivity", "ChatActivity"};

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }






/*
    public void startActivityWithSecurityCheck(Intent intent) {

        if (intent.getComponent() == null) {
            super.startActivity(intent);
        } else {
            String className = intent.getComponent().getClassName();
            boolean checkLogin = false;
            for (String secureActivity : secureActivities) {
                if (className.equals("coop.biantik.lets.activities." + secureActivity)) {
                    checkLogin = true;
                }
            }
            SessionManager session = SessionManager.getInstance(getApplicationContext());
            if (checkLogin && !session.isLoggedIn()) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(i, REQUEST_LOGIN);
                UIUtils.showMessage(this, R.string.login_required);
            } else {
                super.startActivity(intent);
            }
        }
    }

*/


}
