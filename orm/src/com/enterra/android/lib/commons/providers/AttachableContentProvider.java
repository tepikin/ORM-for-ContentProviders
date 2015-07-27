/**************************************************************************
 * TODO copyright
 *
 * $Id: AttachableContentProvider.java 66 2012-05-17 10:55:52Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/providers/AttachableContentProvider.java $
 **************************************************************************/

package com.enterra.android.lib.commons.providers;

import android.content.ContentProvider;
import android.net.Uri;

/**
 * Class for ContentProvider with Uri check.
 */
public abstract class AttachableContentProvider extends ContentProvider {

    /**
     * Test is uri for this provider. (is uri matches by this provider)
     * 
     * @param uri
     *            - request uri.
     * @return - true if uri maches by current provider, else false.
     */
    public abstract boolean isMatched(Uri uri);

}
