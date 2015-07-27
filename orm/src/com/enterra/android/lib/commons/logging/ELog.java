/**************************************************************************
 * TODO copyright
 *
 * $Id: ELog.java 5 2012-04-12 10:28:26Z tretyakov $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/logging/ELog.java $
 **************************************************************************/

package com.enterra.android.lib.commons.logging;

import android.util.Log;

/**
 * Class for logging data to standart android Log.
 */
public final class ELog {
    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }

    private ELog() {
    }
}
