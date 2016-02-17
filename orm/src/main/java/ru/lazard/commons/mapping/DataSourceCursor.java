/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceCursor.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/DataSourceCursor.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import android.database.Cursor;

/**
 * Wrapper class for convert Cursor to IListDataSource.
 */
/* package */class DataSourceCursor extends DataSourceCursorRow implements
        IListDataSource {

    public DataSourceCursor(Cursor cursor) {
        super(cursor);
    }

}
