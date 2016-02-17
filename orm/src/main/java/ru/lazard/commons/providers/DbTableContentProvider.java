/**************************************************************************
 * TODO copyright
 *
 * $Id: DbTableContentProvider.java 66 2012-05-17 10:55:52Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/providers/DbTableContentProvider.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Provider for concrete query from DB.
 * Matches by fixed uri path.
 * Simple realization.
 */
public class DbTableContentProvider extends DbContentProvider {

    private String tableName;

    /**
     * Constructor.
     * 
     * @param dbHelper
     * @param tableName
     *            - name of requested table or special query identifier.
     * @param context
     */
    public DbTableContentProvider(SQLiteOpenHelper dbHelper, String tableName,
            Context context) {
        super(dbHelper, context);

        if (tableName == null) {
            throw new IllegalArgumentException("TableName should not be null");
        }

        this.tableName = tableName;

    }

    /**
     * Get table name of this provider.
     * 
     * @return
     */
    @Override
    public String getTableName(Uri uri) {
        return tableName;
    };

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.enterra.android.lib.providers.ISimpleProvider#isMaches(android.net
     * .Uri)
     */
    @Override
    public boolean isMatched(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        String tableName = getTableName(uri);
        String firstSegment = uri.getPathSegments().get(0);
        if (firstSegment.equals(tableName)) {
            return true;
        }
        return false;
    }

}
