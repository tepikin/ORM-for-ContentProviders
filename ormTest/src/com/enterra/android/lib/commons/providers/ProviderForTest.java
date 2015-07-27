/**************************************************************************
 * TODO copyright
 *
 * $Id: ProviderForTest.java 70 2012-05-18 03:09:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/ProviderForTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.providers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * 
 */

public class ProviderForTest extends AttachableContentProvidersRouter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.enterra.android.lib.commons.providers.sql.MainSqlContentProvider#
     * createProviders(android.content.Context,
     * android.database.sqlite.SQLiteOpenHelper)
     */
    @Override
    protected List<AttachableContentProvider> createProviders(Context context) {
        List<AttachableContentProvider> providers = new ArrayList<AttachableContentProvider>();
        providers.add(new DbContentProvider(new DatabaseHelper(context),
                context));
        return providers;
    }

}
