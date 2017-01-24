package coop.biantik.traductor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.Info;
import coop.biantik.traductor.network.SessionRequestInterceptor;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.LoadInfoAnswerEvent;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class SplashActivity extends BaseActivity {


    private static final String LOG_TAG = "SplashActivity";

    @Bind(R.id.main)
    RelativeLayout mainLayout;

    private static final int REQUEST_WIFI_CONNECT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        /*Intent intent = new Intent(this, PlayAudio.class);
        intent.putExtra("dataSource", "");
        intent.putExtra("playingLanguage", "es");

        startService(intent);
        */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new LoadInfoAsyncTask().execute();

            }
        }, 2000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_WIFI_CONNECT && resultCode == Activity.RESULT_OK) {
            startApp();
        } else {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void startApp() {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


    public class LoadInfoAsyncTask extends AsyncTask<Void, Void, Info> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Info doInBackground(Void... params) {

            Info info = null;
            try {
                info = eventService.findInfo();
            } catch (Throwable e) {
                return null;
            }

            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            bus.post(new LoadInfoAnswerEvent(info));
        }
    }

    @Subscribe
    public void onTaskResult(LoadInfoAnswerEvent event) {
        Info info = event.getInfo();
        Boolean info2 = globals.getIsPrivate();
        if (info == null) {
            UIUtils.showSnackBarWithAction(mainLayout, closeAppListener, R.string.error_no_wifi, R.string.action_ok);
        } else {
            if (info.isPrivate()) {
                RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(new SessionRequestInterceptor(getApplicationContext())).setEndpoint(getResources().getString(R.string.server_private_url)).setConverter(new GsonConverter(gson)).setLogLevel(RestAdapter.LogLevel.FULL).build();
                eventService.setRestAdapter(restAdapter);
                postService.setRestAdapter(restAdapter);
                userService.setRestAdapter(restAdapter);
                globals.setIsPrivate(true);
            }
            else{
                globals.setIsPrivate(false);
            }
            startApp();
        }

    }

    private View.OnClickListener closeAppListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
