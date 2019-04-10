package com.ikaeesoft.www.weatherforcast;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_weather, rootKey);
        SharedPreferences sharedPreference = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefrenceScreen = getPreferenceScreen();
        int count  = prefrenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            android.support.v7.preference.Preference p = prefrenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value = sharedPreference.getString(p.getKey(),"");
                setPreferenceSummary(p ,value);
            }
        }
    }

    private void setPreferenceSummary(Preference p, Object value) {
        String newValue  = value.toString();
        if (p instanceof ListPreference){
            ListPreference listpreference = (ListPreference) p;
            int indexValue = listpreference.findIndexOfValue(newValue);
            if(indexValue >= 0){
                listpreference.setSummary(listpreference.getEntries()[indexValue]);
            }

        }else{
            EditTextPreference editTextPreference = (EditTextPreference) p;
            editTextPreference.setSummary(newValue);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onStart();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("changed",key);
        Preference preference = findPreference(key);
        if(preference != null) {
            if (!(preference instanceof CheckBoxPreference))
            setPreferenceSummary(preference,sharedPreferences.getString(key,""));
        }
    }
}
