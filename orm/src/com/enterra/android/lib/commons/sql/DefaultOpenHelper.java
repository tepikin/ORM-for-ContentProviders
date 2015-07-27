/**************************************************************************
 * TODO copyright
 *
 * $Id: DefaultOpenHelper.java 59 2012-04-26 09:59:06Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/sql/DefaultOpenHelper.java $
 **************************************************************************/

package com.enterra.android.lib.commons.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Default SqliteOpenHelper based on classes marked {@link DatabaseTable}
 * annotation.
 */
public class DefaultOpenHelper extends SQLiteOpenHelper {
    /**
     * Array of classes associated with tables.
     */
    private Class<?>[] classes;

    /**
     * Constructor
     * 
     * @param context
     *            - context of provider
     * @param dbName
     *            - name of database file
     * @param version
     *            - database version.
     * @param classes
     *            - list of classes associated with tables. Classes must be
     *            marked by {@link DatabaseTable} annotation.
     */
    public DefaultOpenHelper(Context context, String dbName, int version,
            Class<?>... classes) {
        super(context, dbName, null, version);
        this.classes = classes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < classes.length; i++) {
            TableUtils.createTable(db, classes[i]);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < classes.length; i++) {
            TableUtils.deleteTable(db, classes[i]);
        }
        onCreate(db);
    }

}
