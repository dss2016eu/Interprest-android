package coop.biantik.traductor.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import butterknife.Bind;
import butterknife.ButterKnife;
import coop.biantik.traductor.R;
import coop.biantik.traductor.model.User;
import coop.biantik.traductor.utils.UIUtils;
import coop.biantik.traductor.utils.bus.UserAnswerEvent;


public class TranslationFragment extends BaseFragment {

    private static final String LOG_TAG = "TranslationFragment";

    private static final int REQUEST_RECORD_AUDIO = 1;

    boolean isStreaming = false;

    @Bind(R.id.username)
    TextView username;

    @Bind(R.id.language)
    TextView language;

    @Bind(R.id.translation_text)
    TextView translationText;

    @Bind(R.id.recording_text)
    TextView recordingText;

    @Bind(R.id.translation_button)
    ImageButton translationButton;

    /**
     * By default AMR is the audio encoder.
     */

    private Context context;

    private PowerManager.WakeLock mWakeLock;

    private AudioManager audio;

    private AudioGroup audioGroup;

    private AudioStream audioStream;

    private Intent rtspIntent;

    public TranslationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audio.setMode(AudioManager.STREAM_VOICE_CALL);

            audioGroup = new AudioGroup();
            audioGroup.setMode(AudioGroup.MODE_ECHO_SUPPRESSION);

            audioStream = new AudioStream(InetAddress.getByName(getIpAddress()));
            //audioStream = new AudioStream(InetAddress.getByAddress(getLocalIPAddress()));
            audioStream.setCodec(AudioCodec.PCMA);
            audioStream.setMode(RtpStream.MODE_SEND_ONLY);


        } catch (Exception e) {
            snackBar = UIUtils.showSnackBarWithAction(getView(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            }, R.string.error_general, R.string.action_ok);

            Log.e("----------------------", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_translation, container, false);
        ButterKnife.bind(this, v);
        User user = sessionManager.getUser();
        Typeface typeFace = globals.getFontLight();
        username.setTypeface(typeFace);
        language.setTypeface(typeFace);
        translationText.setTypeface(typeFace);
        recordingText.setTypeface(typeFace);
        username.setText(user.getUsername());
        language.setText(user.getTranslationLanguage());
        translationButton.setOnClickListener(translationButtonListener);

        progressDialog = UIUtils.showProgressMessage(getActivity(), R.string.loading);
        new LoadUserTask().execute();

        PowerManager powerManager = (PowerManager) getActivity().getSystemService(getActivity().POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "coop.biantik.traductor");


        return v;

    }


    private View.OnClickListener translationButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                                            REQUEST_RECORD_AUDIO);
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_AUDIO);
                return;
            }

            toggleStream();


        }
    };

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.record_audio_permission)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    toggleStream();
                } else {

                    snackBar = UIUtils.showSnackBarWithAction(getView(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    }, R.string.record_audio_permission, R.string.action_ok);

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void toggleStream() {


        if (!isStreaming)

        {
            String rtpServer = getString(R.string.rtsp_server);
            Integer rtpPort = getResources().getInteger(R.integer.rtsp_server_port);
            if (rtpServer == null || rtpPort == null || rtpServer.isEmpty()){
                rtpServer = sessionManager.getUser().getIp();
                rtpPort = sessionManager.getUser().getPort();
            }
            try {
                Log.i(LOG_TAG, "Ip:"+ rtpServer + ":Port:" + rtpPort);
                //set receiver(vlc player) machine ip address(please update with your machine ip)
                audioStream.associate(InetAddress.getByName(rtpServer), rtpPort);
            } catch (Exception e) {
                snackBar = UIUtils.showSnackBarWithAction(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();
                    }
                }, R.string.error_general, R.string.action_ok);

                Log.e("----------------------", e.toString());
                e.printStackTrace();
            }
            startRecording();
            startRecordingUI();
            isStreaming = true;


        } else

        {
            stopRecording();
            stopRecordingUI();
            isStreaming = false;

        }

    }

    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress = inetAddress.getHostAddress().toString();
                        Log.e("IP address", "" + ipAddress);
                        Log.e("IP address", "" + ipAddress);
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        return null;
    }

    private void startRecording() {
        audioStream.join(audioGroup);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mWakeLock.acquire();
    }


    private void stopRecording() {
        if (mWakeLock.isHeld()) mWakeLock.release();
        if (audioStream != null) audioStream.join(null);
    }

    private void stopRecordingUI() {
        recordingText.setVisibility(View.GONE);
        recordingText.clearAnimation();
        translationText.setText(R.string.init_translation);
        translationButton.setImageResource(R.drawable.stop_translation);
        translationButton.setBackgroundResource(R.drawable.btn_circle_stroke_primary_color);
    }

    private void startRecordingUI() {
        recordingText.setVisibility(View.VISIBLE);
        recordingText.startAnimation(UIUtils.blinkAnimation());
        translationText.setText(R.string.stop_translation);
        translationButton.setImageResource(R.drawable.start_translation);
        translationButton.setBackgroundResource(R.drawable.btn_circle_large_primary_color);
    }


    private class LoadUserTask extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... data) {
            User user = userService.login(sessionManager.getUser().getUsername());
            return user;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(User user) {

            UserAnswerEvent event = new UserAnswerEvent();
            event.setUser(user);
            bus.post(event);
        }

    }

    @Subscribe
    public void answerAvailable(UserAnswerEvent event) {
        progressDialog.dismiss();
        User user = event.getUser();
        if (user == null || !user.isTranslator()) {
            UIUtils.showErrorMessage(getActivity(), R.string.no_translator);
            getActivity().finish();
        } else {
            language.setText(sessionManager.getUser().getTranslationLanguage());
            //initializePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopRecording();


    }


}
