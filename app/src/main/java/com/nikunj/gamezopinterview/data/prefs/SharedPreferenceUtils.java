package com.nikunj.gamezopinterview.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by nikunj on 3/27/18.
 */

public class SharedPreferenceUtils {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedPreferenceUtils utilInstance;

    private SharedPreferenceUtils(Context context) {
        Context mContext = context;
        sharedPreferences = context.getSharedPreferences(SharedPreferenceKeys.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferenceUtils getInstance(Context context) {
        if (utilInstance == null) {
            utilInstance = new SharedPreferenceUtils(context);
        }
        return utilInstance;
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void setValue(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void setValue(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public void setValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setValue(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public void setValue(String key, Set<String> values) {
        editor.putStringSet(key, values);
        editor.apply();
    }

    public String getStringValue(String key, String defaultVal) {
        return sharedPreferences.getString(key, defaultVal);
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, SharedPreferenceKeys.DEFAULT_STRING_VALUE);
    }

    public int getIntValue(String key, int defaultVal) {
        return sharedPreferences.getInt(key, defaultVal);
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, SharedPreferenceKeys.DEFAULT_INT_VALUE);
    }

    public float getFloatValue(String key, float defaultVal) {
        return sharedPreferences.getFloat(key, defaultVal);
    }

    public float getFloatValue(String key) {
        return sharedPreferences.getFloat(key, SharedPreferenceKeys.DEFAULT_FLOAT_VALUE);
    }

    public boolean getBooleanValue(String key, boolean defaultVal) {
        return sharedPreferences.getBoolean(key, defaultVal);
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, SharedPreferenceKeys.DEFAULT_BOOLEAN_VALUE);
    }

    public long getLongValue(String key, long defaultVal) {
        return sharedPreferences.getLong(key, defaultVal);
    }

    public long getlongValue(String key) {
        return sharedPreferences.getLong(key, SharedPreferenceKeys.DEFAULT_LONG_VALUE);
    }

    public Set<String> getSetValue(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    public Map<String, ?> getAllValues() {
        return sharedPreferences.getAll();
    }

    public void clearPrefs() {
        editor.clear().apply();
    }

    public void removeKey(String key) {
        if (editor != null) {
            editor.remove(key).apply();
        }
    }

    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener changeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(changeListener);
    }

    public void unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener changeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

}
