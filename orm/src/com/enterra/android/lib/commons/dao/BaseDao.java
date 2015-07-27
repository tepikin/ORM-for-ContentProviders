/**************************************************************************
 * TODO copyright
 *
 * $Id: BaseDao.java 78 2012-06-20 08:08:19Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/dao/BaseDao.java $
 **************************************************************************/

package com.enterra.android.lib.commons.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.enterra.android.lib.commons.mapping.MapperHelper;
import com.enterra.android.lib.commons.sql.DatabaseField;
import com.enterra.android.lib.commons.sql.TableUtils;

/**
 * Class for access to database objects. Class contains cash info - do't
 * recreate class if not needed.
 */
public class BaseDao<T> implements Dao<T> {
    /**
     * Describe of database class.
     */
    private class TableInfo {
        private Class<T> clazz;

        private Field idField;

        private String idFieldColumnName;

        private String tableName;
    }

    private final ContentResolver contentResolver;

    private final Context context;

    private final String providerAuthority;

    private TableInfo tableInfo;

    private Uri uri;

    /**
     * Class contains cash info. Do't recreate class if not needed.
     * 
     * @param providerAuthority
     * @param contentResolver
     * @param context
     */
    public BaseDao(String providerAuthority, Context context, Class<T> clazz) {
        if (TextUtils.isEmpty(providerAuthority)) {
            throw new IllegalArgumentException(
                    "\"providerAuthority\" should not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("\"context\" should not be null");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("\"clazz\" should not be null");
        }
        this.providerAuthority = providerAuthority;
        this.contentResolver = context.getContentResolver();
        this.context = context;
        readTableInfo(clazz);
    }

    /**
     * Delete from db.
     * 
     * @param clazz
     *            - associated with table.
     * @param where
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(String where, String[] selectionArgs) {
        if (selectionArgs == null) {
            // selectionArgs should not be null.
            selectionArgs = new String[0];
        }
        return contentResolver.delete(uri, where, selectionArgs);
    }

    /**
     * Delete from db by object id.
     * 
     * @param object
     *            - associated with table.
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public int delete(T object) throws IllegalAccessException {
        if (object == null) {
            throw new NullPointerException("\"object\" should not be null");
        }
        String idColumnName = tableInfo.idFieldColumnName;
        Object idValue = tableInfo.idField.get(object);
        return contentResolver.delete(uri, idColumnName + " = " + idValue,
                new String[] {});
    }

    /**
     * @see ContentResolver#delete(Uri, String, String[])
     */
    @Override
    public int delete(Uri uri, String where, String[] selectionArgs) {
        if (uri == null) {
            throw new NullPointerException("\"uri\" should not be null");
        }
        if (selectionArgs == null) {
            // selectionArgs should not be null.
            selectionArgs = new String[0];
        }
        return contentResolver.delete(uri, where, selectionArgs);
    }

    /**
     * Delete from db list of objects at transaction by objects id's.
     * 
     * @param objects
     *            - associated with table, with correct id's.
     * @return
     * @throws IllegalAccessException
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    @Override
    public int deleteList(List<T> objects) throws IllegalAccessException,
            RemoteException, OperationApplicationException {
        if (objects == null) {
            throw new NullPointerException("\"objects\" should not be null");
        }
        if (objects.size() == 0) {
            // no operations need.
            return 0;
        }
        StringBuilder selection = new StringBuilder();
        String orString = " or ";
        String equalString = " = ";
        String spaceString = " ";
        for (Object object : objects) {
            String idColumnName = tableInfo.idFieldColumnName;
            Object idValue = tableInfo.idField.get(object);
            selection.append(spaceString);
            selection.append(idColumnName);
            selection.append(equalString);
            selection.append(idValue);
            selection.append(spaceString);
            selection.append(orString);
        }
        String selectionString = selection.substring(0, selection.length()
                - orString.length());
        return contentResolver.delete(uri, selectionString, new String[0]);
    }

    /**
     * Get used instance of ContentResolver.
     * 
     * @return
     */
    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    /**
     * Get used Context.
     * 
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     * Get ContentResolver Authority.
     * 
     * @return
     */
    public String getProviderAuthority() {
        return providerAuthority;
    }

    /**
     * Get TableInfo of Dao instance.
     * 
     * @return
     */
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    /**
     * @return
     */
    public Uri getUri() {
        return uri;
    }

    public Uri getUri(String key) {
        return Uri.parse(getUri().toString() + "/" + key);
    }

    /**
     * Insert row to table.
     * 
     * @param clazz
     *            - associated with table.
     * @param values
     *            - values for insert.
     */
    @Override
    public void insert(ContentValues values) {
        if (values == null) {
            throw new NullPointerException("\"values\" should not be null");
        }
        contentResolver.insert(uri, values);
    }

    /**
     * Insert row to table.
     * 
     * @param object
     *            - associated with table.
     */
    @Override
    public void insert(T object) {
        if (object == null) {
            throw new NullPointerException("\"object\" should not be null");
        }
        ContentValues values = objectToContentValues(object);
        contentResolver.insert(uri, values);
    }

    /**
     * @see ContentResolver#insert(Uri, ContentValues)
     * @param uri
     * @param values
     */
    @Override
    public void insert(Uri uri, ContentValues values) {
        if (uri == null) {
            throw new NullPointerException("\"uri\" should not be null");
        }
        if (values == null) {
            throw new NullPointerException("\"values\" should not be null");
        }
        contentResolver.insert(uri, values);
    }

    /**
     * Insert rows to table in transaction.
     * 
     * @param objects
     *            - associated with table.
     * @return
     * @throws IllegalAccessException
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    @Override
    public int insertList(List<T> objects) throws IllegalAccessException,
            RemoteException, OperationApplicationException {
        if (objects == null) {
            throw new NullPointerException("\"objects\" should not be null");
        }
        if (objects.size() == 0) {
            // no operations need.
            return 0;
        }
        ArrayList<ContentValues> list = new ArrayList<ContentValues>();
        for (T object : objects) {
            ContentValues values = objectToContentValues(object);
            list.add(values);
        }
        ContentValues[] values = list.toArray(new ContentValues[list.size()]);
        return contentResolver.bulkInsert(uri, values);
    }

    /**
     * Get query result mapped to objects.
     * 
     * @param <T>
     * @param clazz
     *            - associated with table - for mapping.
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public List<T> query(String selection, String[] selectionArgs,
            String sortOrder) {
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        Cursor cursor = contentResolver.query(uri, null, selection,
                selectionArgs, sortOrder);
        List<T> list = cursorToObjects(cursor);
        cursor.close();
        return list;
    }

    /**
     * Get query result mapped to objects.
     * 
     * @param <T>
     * @param clazz
     *            - associated with table - for mapping.
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public List<T> query(String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        Cursor cursor = contentResolver.query(uri, projection, selection,
                selectionArgs, sortOrder);
        List<T> list = cursorToObjects(cursor);
        cursor.close();
        return list;
    }

    /**
     * @see ContentResolver#query(Uri, String[], String, String[], String)
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        if (uri == null) {
            throw new NullPointerException("\"uri\" should not be null");
        }
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        return contentResolver.query(uri, projection, selection, selectionArgs,
                sortOrder);
    }

    /**
     * Update table.
     * 
     * @param clazz
     *            - associated with table.
     * @param values
     *            - values for update
     * @param where
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(ContentValues values, String where, String[] selectionArgs) {
        if (values == null) {
            throw new NullPointerException("\"values\" should not be null");
        }
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        return contentResolver.update(uri, values, where, selectionArgs);
    }

    /**
     * Update table by id.
     * 
     * @param object
     *            - contains id and values for update.
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public int update(T object) throws IllegalAccessException {
        if (object == null) {
            throw new NullPointerException("\"object\" should not be null");
        }
        String idColumnName = tableInfo.idFieldColumnName;
        Object idValue = tableInfo.idField.get(object);
        ContentValues values = objectToContentValues(object);
        return contentResolver.update(uri, values, idColumnName + " = "
                + idValue, new String[] {});
    }

    /**
     * Update table.
     * 
     * @param clazz
     *            - associated with table.
     * @param object
     *            - contains values for update.
     * @param where
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(T object, String where, String[] selectionArgs) {
        if (object == null) {
            throw new NullPointerException("\"object\" should not be null");
        }
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        ContentValues values = objectToContentValues(object);
        return contentResolver.update(uri, values, where, selectionArgs);
    }

    /**
     * @see ContentResolver#update(Uri, ContentValues, String, String[])
     * @param uri
     *            - not null uri
     * @param values
     *            - not null values
     * @param where
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] selectionArgs) {
        if (uri == null) {
            throw new NullPointerException("\"uri\" should not be null");
        }
        if (values == null) {
            throw new NullPointerException("\"values\" should not be null");
        }
        if (selectionArgs == null) {
            // selections args should not be null.
            selectionArgs = new String[0];
        }
        return contentResolver.update(uri, values, where, selectionArgs);
    }

    /**
     * Update list of Objects by theirs id's.
     * 
     * @param objects
     *            - list of objects associated with table contains correct id's
     *            an values for update.
     * @return
     * @throws IllegalAccessException
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    @Override
    public ContentProviderResult[] updateList(List<T> objects)
            throws IllegalAccessException, RemoteException,
            OperationApplicationException {
        if (objects == null) {
            throw new NullPointerException("\"objects\" should not be null");
        }
        if (objects.size() == 0) {
            // no operations need.
            return new ContentProviderResult[0];
        }
        String idColumnName = tableInfo.idFieldColumnName;
        ArrayList<ContentProviderOperation> list = new ArrayList<ContentProviderOperation>();
        for (T object : objects) {
            Object idValue = tableInfo.idField.get(object);
            ContentValues values = objectToContentValues(object);
            Builder builder = ContentProviderOperation.newUpdate(uri);
            builder.withValues(values);
            builder.withSelection(idColumnName + " = " + idValue,
                    new String[] {});
            list.add(builder.build());
        }
        return contentResolver.applyBatch(providerAuthority, list);
    }

    /**
     * @param cursor
     * @return
     */
    protected List<T> cursorToObjects(Cursor cursor) {
        return MapperHelper.cursorToObjectList(cursor, tableInfo.clazz);
    }

    /**
     * @param object
     * @return
     */
    protected ContentValues objectToContentValues(T object) {
        return MapperHelper.objectToContentValues(object);
    }

    /**
     * Get db data from class object.
     * 
     * @param clazz
     * @return
     */
    private void readTableInfo(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("\"clazz\" should not be null");
        }
        tableInfo = new TableInfo();
        tableInfo.clazz = clazz;
        tableInfo.tableName = TableUtils.getTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            DatabaseField fieldAnnotation = field
                    .getAnnotation(DatabaseField.class);
            if (fieldAnnotation != null && fieldAnnotation.isPrimaryKey()) {
                tableInfo.idField = field;
                tableInfo.idField.setAccessible(true);
                String idFieldColumnName = fieldAnnotation.name();
                if (TextUtils.isEmpty(idFieldColumnName)) {
                    idFieldColumnName = field.getName();
                }
                tableInfo.idFieldColumnName = idFieldColumnName;
                break;
            }
        }
        if (TextUtils.isEmpty(tableInfo.idFieldColumnName)
                || tableInfo.idField == null) {
            throw new IllegalArgumentException(
                    "No id field in the class. One field must be marked by anotation \"DatabaseField\" as \"isPrimaryKey=true\".");
        }
        uri = Uri.parse("content://" + providerAuthority + "/"
                + tableInfo.tableName);
    }
}
