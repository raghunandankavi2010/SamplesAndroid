package com.india.innovates.pucho.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.india.innovates.pucho.QuestionFeed;
import com.india.innovates.pucho.R;

/**
 * Created by yash on 20/2/16.
 */
public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences settings_preferences;
    public static final String preference_key =  "listkey";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);

        settings_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final ListPreference language_list = (ListPreference) findPreference(getString(R.string.language_list_preference));
        language_list.setTitle(getString(R.string.title_language_preference)+ ": " + settings_preferences.getString(getString(R.string.language_list_preference), "English"));
        language_list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                language_list.setTitle(getString(R.string.title_language_preference)+ ": " + newValue.toString());


                Intent intent = new Intent(getActivity(), QuestionFeed.class);
                intent.putExtra("language",newValue.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
                getActivity().finish();
                return true;
            }
        });
    }


}