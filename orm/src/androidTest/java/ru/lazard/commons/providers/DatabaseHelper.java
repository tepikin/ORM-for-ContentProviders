/**************************************************************************
 * TODO copyright
 *
 * $Id: DatabaseHelper.java 66 2012-05-17 10:55:52Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/DatabaseHelper.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.content.Context;

import ru.lazard.commons.sql.DefaultOpenHelper;

public class DatabaseHelper extends DefaultOpenHelper {

    private static final String NAME = null; // for store database in-memory.

    private static final int VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, NAME, VERSION, TableForTest.class, TableForTest2.class);
    }

}
