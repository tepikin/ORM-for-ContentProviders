/**************************************************************************
 * TODO Copyright
 *
 * $Id: SettingsTest.java 46 2012-04-24 09:44:54Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/settings/SettingsTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

/**
 * Class for test methods from {@link Settings} class.
 */
public class SettingsTest extends AndroidTestCase {

    private Settings settings;

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#Settings(SharedPreferences) 
     */
    public void testConstructors() {
        Settings settings;
        // {@link
        // com.enterra.android.lib.commons.settings.Settings#Settings(android.content.Context)
        settings = new Settings(getContext());
        settings.setBoolean("KEY_BOOLEAN", true);

        // {@link
        // com.enterra.android.lib.commons.settings.Settings#Settings(Context,
        // String)
        settings = new Settings(getContext(), "settings");
        settings.setBoolean("KEY_BOOLEAN", true);

        // {@link
        // com.enterra.android.lib.commons.settings.Settings#Settings(Context,
        // String, int)
        settings = new Settings(getContext(), "settings", Context.MODE_PRIVATE);
        settings.setBoolean("KEY_BOOLEAN", true);

        // {@link
        // com.enterra.android.lib.commons.settings.Settings#Settings(SharedPreferences)
        SharedPreferences preferences = getContext().getSharedPreferences(
                "settings", Context.MODE_PRIVATE);
        settings = new Settings(preferences);
        settings.setBoolean("KEY_BOOLEAN", true);
    }

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#setBoolean(String, boolean) 
     */
    public void testGetSetBoolean() {
        settings.setBoolean("KEY_BOOLEAN", true);
        assertEquals(true, settings.getBoolean("KEY_BOOLEAN", false));
    }

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#setFloat(String, float) 
     */
    public void testGetSetFloat() {
        settings.setFloat("KEY_FLOAT", 1.2f);
        assertEquals(1.2f, settings.getFloat("KEY_FLOAT", 0));
    }

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#setInt(String, int) 
     */
    public void testGetSetInt() {
        settings.setInt("KEY_INT", 1);
        assertEquals(1, settings.getInt("KEY_INT", 0));
    }

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#setLong(String, long) 
     */
    public void testGetSetLong() {
        settings.setLong("KEY_LONG", 1l);
        assertEquals(1l, settings.getLong("KEY_LONG", 0));
    }

    /**
     * Test method for
     * {@link com.enterra.android.lib.commons.settings.Settings#setString(String, String) 
     */
    public void testGetSetString() {
        settings.setString("KEY_STRING", "test");
        assertEquals("test", settings.getString("KEY_STRING", null));
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        settings = new Settings(getContext());
        super.setUp();
    }

}
