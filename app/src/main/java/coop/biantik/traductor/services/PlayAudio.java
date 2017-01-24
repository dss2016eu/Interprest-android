package coop.biantik.traductor.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;

import coop.biantik.traductor.R;
import coop.biantik.traductor.activities.MainActivity;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayAudio extends Service implements IjkMediaPlayer.OnPreparedListener, IjkMediaPlayer.OnErrorListener, IjkMediaPlayer.OnCompletionListener{


    private static final String LOG_TAG = "PlayAudio";
    private static final int NOTIFICATION_ID = 12;

    IjkMediaPlayer mediaPlayer;
    String dataSource;
    WifiManager.WifiLock wifiLock;
    String playingLanguage;

    public void onCreate() {
        super.onCreate();
        createMediaPlayer();
    }

    private void createMediaPlayer() {

        mediaPlayer = new IjkMediaPlayer();
        //mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        String dataSource = intent.getStringExtra("dataSource");

        if (getString(R.string.url_sdp) != null && !getString(R.string.url_sdp).trim().isEmpty()) {
            dataSource = getString(R.string.url_sdp);
        }
        if (mediaPlayer == null) createMediaPlayer();
        if (!mediaPlayer.isPlaying()) {

            Log.i("######ataSource:", dataSource);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(dataSource));
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                Log.d(LOG_TAG, "Problem in Playing Audio");
            }
            Log.d(LOG_TAG, "Media Player started!");
        }else{
            sendStartAudioBroadcast();
        }
        return 1;
    }


    public void onDestroy() {

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (wifiLock != null) wifiLock.release();
    }

    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }

    /*
    @Override
    public void onPrepared(MediaPlayer mp) {


        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
        wifiLock.acquire();
        mediaPlayer.start();
        showForegroundNotification("Interprest en ejecución");
        sendStartAudioBroadcast();
        if (mediaPlayer.isLooping() != true) {
            Log.d(LOG_TAG, "Problem in Playing Audio");
        }

    }
*/


    private void showForegroundNotification(String contentText) {
        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher
        Intent showTaskIntent = new Intent(getApplicationContext(), MainActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showTaskIntent.putExtra("playingLanguage", playingLanguage);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_action_headset_white_24dp)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private void sendStartAudioBroadcast() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("coop.biantik.traductor.StartAudioBroadcast");
        getApplicationContext().sendBroadcast(broadcastIntent);
    }

    /*
    @Override

    public boolean onError(MediaPlayer mp, int what, int extra) {
        releaseMediaPlayer();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("coop.biantik.traductor.AudioErrorBroadcast");
        getApplicationContext().sendBroadcast(broadcastIntent);
        return true;
    }
*/
    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        releaseMediaPlayer();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("coop.biantik.traductor.AudioErrorBroadcast");
        getApplicationContext().sendBroadcast(broadcastIntent);
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
        wifiLock.acquire();
        mediaPlayer.start();
        showForegroundNotification("Interprest en ejecución");
        sendStartAudioBroadcast();
        if (mediaPlayer.isLooping() != true) {
            Log.d(LOG_TAG, "Problem in Playing Audio");
        }
    }


    @Override
    public void onCompletion(IMediaPlayer mp) {
        releaseMediaPlayer();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("coop.biantik.traductor.AudioErrorBroadcast");
        getApplicationContext().sendBroadcast(broadcastIntent);
    }
}