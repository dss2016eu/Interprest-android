package coop.biantik.traductor.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;


import coop.biantik.traductor.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    public PreferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_preference);
        Preference versionPref = findPreference("pref_version");
        String version = "-";
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
        }
        versionPref.setTitle(String.format(getResources().getString(R.string.version), version));
    }
}
