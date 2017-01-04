package com.indiainnovates.pucho.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.indiainnovates.pucho.R;

/**
 * Created by yash on 20/2/16.
 */
public class SettingsFragment extends PreferenceFragment {

    SharedPreferences settings_preferences;

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
                return true;
            }
        });
    }
}