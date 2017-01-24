package coop.biantik.traductor.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import coop.biantik.traductor.model.User;

public class SessionManager {

    private static SessionManager _instance;

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Preferences";

    private SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new SessionManager(context);
        }
        return _instance;
    }


    public void setUser(User user) {

        Gson gson = new Gson();

        editor.putString("user", gson.toJson(user));


        editor.commit();
    }

    public boolean isLoggedIn() {

        return pref.getString("user", null) != null;
    }

    /**
     * Get stored session data
     */
    public User getUser() {
        Gson gson = new Gson();
        return gson.fromJson(pref.getString("user", null), User.class);
    }


    /**
     * Clear session details
     */
    public void logoutUser() {

		//new Logout().execute(getUser());

		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
        /*
		callFacebookLogout();
        */
        /*
		ChatService chatService = new ChatServiceImpl();
		chatService.deleteAll(_context);
		editor.clear();
		editor.commit();
        */

    }

	/*
	private void callFacebookLogout() {


		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				// clear your preferences if saved
			}
		} else {

			session = new Session(_context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved

		}


	}

	protected class Logout extends AsyncTask<User, Void, Void> {

		@Override
		protected Void doInBackground(User... users) {

			((AlleupApplication) _context).userService.logout(users[0]);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}
	}
*/
}