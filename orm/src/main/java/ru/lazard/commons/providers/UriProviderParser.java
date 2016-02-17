/**************************************************************************
 * TODO copyright
 *
 * $Id: UriProviderParser.java 49 2012-04-25 05:34:14Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/providers/UriProviderParser.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.net.Uri;

/**
 * Helper class for parse providers Uri.
 */
public final class UriProviderParser {
    /**
     * Get last Uri segment as id.<br>
     * If not number return -1;
     * 
     * @param uri
     * @return
     */
    public static int getId(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        int id = -1;
        String lastSegment = uri.getLastPathSegment();
        if (lastSegment != null && lastSegment.matches("\\d+")) {
            id = Integer.parseInt(lastSegment);
        }
        return id;
    }

    /**
     * Get path without id.
     * 
     * @param uri
     * @return
     */
    public static String getPath(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        String path = uri.getPath();
        path = path.substring(1, path.length());
        int id = getId(uri);
        if (id >= 0) {
            path = path.substring(0, path.length() - ("\\" + id).length());
        }
        return path;
    }

    /**
     * Get part of uri before path.
     * 
     * @param uri
     * @return
     */
    public static String getPrefix(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        String prefix = uri.getScheme() + "://" + uri.getHost();
        return prefix;
    }

    private UriProviderParser() {
    }
}
