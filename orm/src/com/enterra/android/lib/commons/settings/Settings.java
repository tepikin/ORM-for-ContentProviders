/**************************************************************************
 * TODO copyright
 *
 * $Id: Settings.java 45 2012-04-24 09:36:19Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/settings/Settings.java $
 **************************************************************************/

package com.enterra.android.lib.commons.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

/**
 * Class for keep settings at application. <br>
 * Wrapper for {@link SharedPreferences}.
 */
public class Settings {
    private static final String SETTINGS_FILE_NAME = "settings";

    private SharedPreferences preferences;

    private SharedPreferences.Editor preferencesEditor;

    /**
     * Constructor.
     * 
     * @param context
     *            - context of application.
     */
    public Settings(Context context) {
        this(context, SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Constructor.
     * 
     * @param context
     *            - context of application.
     * @param fileName
     *            - name of ".xml" file with shared preferences.
     */
    public Settings(Context context, String fileName) {
        this(context, fileName, Context.MODE_PRIVATE);
    }

    /**
     * Constructor.
     * 
     * @param context
     *            - context of application.
     * @param fileName
     *            - name of ".xml" file with shared preferences.
     * @param accessMode
     *            - access mode - one of (Context.MODE_APPEND,
     *            Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE,
     *            Context.MODE_WORLD_WRITEABLE)
     */
    public Settings(Context context, String fileName, int accessMode) {
        super();
        if (context == null) {
            throw new IllegalArgumentException("Context should not be null");
        }
        if (fileName == null || fileName.length() <= 0) {
            throw new IllegalArgumentException("fileName should not be null");
        }
        if (accessMode != Context.MODE_APPEND
                && accessMode != Context.MODE_PRIVATE
                && accessMode != Context.MODE_WORLD_READABLE
                && accessMode != Context.MODE_WORLD_WRITEABLE) {
            throw new IllegalArgumentException(
                    "accessMode should be is Context.MODE_APPEND, Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE, Context.MODE_WORLD_WRITEABLE.");
        }
        this.preferences = context.getSharedPreferences(fileName, accessMode);
        preferencesEditor = preferences.edit();
    }

    /**
     * Constructor.
     * 
     * @param preferences
     *            - {@link SharedPreferences} instance.
     */
    public Settings(SharedPreferences preferences) {
        super();
        if (preferences == null) {
            throw new IllegalArgumentException(
                    "SharedPreferences should not be null");
        }
        this.preferences = preferences;
        preferencesEditor = preferences.edit();
    }

    /**
     * Add listener for settings changes.
     * 
     * @param listener
     */
    public void addListener(OnSharedPreferenceChangeListener listener) {
        getPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Get boolean value from settings by key.
     * 
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * Get float value from settings by key.
     * 
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * Get int value from settings by key.
     * 
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    /**
     * Get long value from settings by key.
     * 
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * Get SharedPreferences object.
     * 
     * @return
     */
    public SharedPreferences getPreferences() {
        return preferences;
    }

    /**
     * Get SharedPreferences.Editor object.
     * 
     * @return
     */
    public SharedPreferences.Editor getPreferencesEditor() {
        return preferencesEditor;
    }

    /**
     * Get String value from settings by key.
     * 
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * Remove listener by settings changes.
     * 
     * @param listener
     */
    public void removeListener(OnSharedPreferenceChangeListener listener) {
        getPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Set boolean value from settings by key.
     * 
     * @param key
     * @param value
     */
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Set float value from settings by key.
     * 
     * @param key
     * @param value
     */
    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * Set int value from settings by key.
     * 
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Set long value from settings by key.
     * 
     * @param key
     * @param value
     */
    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Set String value from settings by key.
     * 
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putString(key, value);
        editor.commit();
    }
}
