/**************************************************************************
 * TODO copyright
 *
 * $Id: DbContentProvider.java 68 2012-05-18 02:55:36Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/providers/DbContentProvider.java $
 **************************************************************************/

package com.enterra.android.lib.commons.providers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * ContentProvider for release queries to all existed tables.
 */
public class DbContentProvider extends AttachableContentProvider {
    /**
     * Name of column with table names.
     */
    private static final String COLUMN_NAME = "name";

    private static final String CONTENT = "content://";

    /**
     * Sql query for get all table names.
     */
    private static final String QUERY_GET_ALL_TABLESS = "SELECT name FROM sqlite_master WHERE type='table'";

    private static final String TYPE_VND_ITEM = "vnd.android.cursor.item/vnd.";

    private static final String TYPE_VND_LIST = "vnd.android.cursor.dir/vnd.";

    private SQLiteOpenHelper dbHelper;

    private String packageName;

    private String tableName;

    private List<String> tableNames;

    public DbContentProvider(SQLiteOpenHelper dbHelper, Context context) {
        super();
        if (dbHelper == null) {
            throw new IllegalArgumentException(
                    "SQLiteOpenHelper should not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("Context should not be null");
        }

        this.dbHelper = dbHelper;
        this.packageName = context.getPackageName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#applyBatch(java.util.ArrayList)
     */
    @Override
    public ContentProviderResult[] applyBatch(
            ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        ContentProviderResult[] results;
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            results = super.applyBatch(operations);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#bulkInsert(android.net.Uri,
     * android.content.ContentValues[])
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        int count = 0;
        try {
            for (ContentValues contentValues : values) {
                insert(uri, contentValues);
                count++;
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
        return count;
    }

    /**
     * Delete row by Uri with id at last segment.<br>
     * Example:
     * <code>content://com.example.transportationprovider/trains/123</code><br>
     * Where:<br>
     * "123" - id of row<br>
     * "trains" - name of table.
     * 
     * @param uri
     * @return
     */
    public int delete(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        if (UriProviderParser.getId(uri) < 0) {
            throw new IllegalArgumentException(
                    "Uri not contains id at last segment");
        }
        return delete(uri, null, null);
    }

    /**
     * Delete row by Uri with id at last segment or by query at arguments.<br>
     * If id at last segment set, then selection was ignored.<br>
     * Example:
     * <code>content://com.example.transportationprovider/trains/123</code><br>
     * Where:<br>
     * "123" - id of row<br>
     * "trains" - name of table.
     * 
     * @param uri
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri is null");
        }

        String table = getTableName(uri);
        int id = UriProviderParser.getId(uri);

        int count = 0;
        if (id < 0) {
            count = dbHelper.getWritableDatabase().delete(table, selection,
                    selectionArgs);
        } else {
            count = dbHelper.getWritableDatabase().delete(table, " id = ?",
                    new String[] { String.valueOf(id) });
        }
        return count;
    }

    /**
     * Getter for used SQLiteOpenHelper.
     * 
     * @return
     */
    public SQLiteOpenHelper getDbHelper() {
        return dbHelper;
    }

    /**
     * Get table name of this provider.
     * 
     * @return
     */
    public String getTableName(Uri uri) {
        String path = UriProviderParser.getPath(uri);
        if (!path.contains("/")) {
            String tableName = path;
            return tableName;
        }
        throw new IllegalArgumentException("Can't get table name from uri: "
                + uri);
    }

    /**
     * Get type of provided data. <br>
     * <code>getTypeList</code> for list data provider. <br>
     * <code>getTypeItem</code> for single row data provider.
     * 
     * @return
     */
    public String getType() {
        return getTypeList();
    };

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri is null");
        }
        String type = getType();
        return type;
    };

    /**
     * Get type for single item provider.
     * 
     * @return
     */
    public String getTypeItem() {
        return getTypeItem(tableName);
    }

    /**
     * Get type for single item provider.
     * 
     * @param contentType
     *            - string type of provided data.
     * @return
     */
    public String getTypeItem(String contentType) {
        return TYPE_VND_ITEM + packageName + "." + contentType;
    }

    /**
     * Get type for items list provider.
     * 
     * @return
     */
    public String getTypeList() {
        return getTypeList(tableName);
    }

    /**
     * Get type for items list provider.
     * 
     * @param contentType
     *            - string type of provided data.
     * @return
     */
    public String getTypeList(String contentType) {
        return TYPE_VND_LIST + packageName + "." + contentType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#insert(android.net.Uri,
     * android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri is null");
        }

        String table = getTableName(uri);
        long id = 0;
        id = dbHelper.getWritableDatabase().insert(table, null, values);

        return Uri.parse(CONTENT + packageName + "/" + table + "/" + id);
    }

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
            throw new IllegalArgumentException("Uri is null");
        }
        if (tableNames == null) {
            // get all table names - lazy initialization.
            tableNames = getTableNames();
        }
        String path = UriProviderParser.getPath(uri);
        if (!path.contains("/") && tableNames.contains(path)) {
            tableName = path;
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#query(android.net.Uri,
     * java.lang.String[], java.lang.String, java.lang.String[],
     * java.lang.String)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        String table = getTableName(uri);
        Cursor cursor = dbHelper.getReadableDatabase().query(table, projection,
                selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    /**
     * Setter for used SQLiteOpenHelper.
     * 
     * @param dbHelper
     */

    public void setDbHelper(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#update(android.net.Uri,
     * android.content.ContentValues, java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        String table = getTableName(uri);
        int count = dbHelper.getWritableDatabase().update(table, values,
                selection, selectionArgs);
        return count;
    }

    /**
     * Get list of all table names.
     * 
     * @return
     */
    private List<String> getTableNames() {
        final List<String> tableNames = new ArrayList<String>();
        SQLiteDatabase sqliteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery(QUERY_GET_ALL_TABLESS, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                tableNames.add(c.getString(c.getColumnIndex(COLUMN_NAME)));
                c.moveToNext();
            }
        }
        c.close();
        return tableNames;
    }
}
