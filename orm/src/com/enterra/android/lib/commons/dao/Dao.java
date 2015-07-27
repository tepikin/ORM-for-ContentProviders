/**************************************************************************
 * TODO copyright
 *
 * $Id: Dao.java 67 2012-05-18 02:26:26Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/dao/Dao.java $
 **************************************************************************/

package com.enterra.android.lib.commons.dao;

import java.util.List;

import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

/**
 * Interface for Dao objects.
 * 
 * @param <T>
 *            - type of Dao associated class.
 */
public interface Dao<T> {

    /**
     * Delete from db.
     * 
     * @param clazz
     *            - associated with table.
     * @param where
     * @param selectionArgs
     * @return
     */
    public abstract int delete(String where, String[] selectionArgs);

    /**
     * Delete from db by object id.
     * 
     * @param object
     *            - associated with table.
     * @return
     * @throws IllegalAccessException
     */
    public abstract int delete(T object) throws IllegalAccessException;

    /**
     * @see ContentResolver#delete(Uri, String, String[])
     */
    public abstract int delete(Uri uri, String where, String[] selectionArgs);

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
    public abstract int deleteList(List<T> objects)
            throws IllegalAccessException, RemoteException,
            OperationApplicationException;

    /**
     * Insert row to table.
     * 
     * @param clazz
     *            - associated with table.
     * @param values
     *            - values for insert.
     */
    public abstract void insert(ContentValues values);

    /**
     * Insert row to table.
     * 
     * @param object
     *            - associated with table.
     */
    public abstract void insert(T object);

    /**
     * @see ContentResolver#insert(Uri, ContentValues)
     * @param uri
     * @param values
     */
    public abstract void insert(Uri uri, ContentValues values);

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
    public abstract int insertList(List<T> objects)
            throws IllegalAccessException, RemoteException,
            OperationApplicationException;

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
    public abstract List<T> query(String selection, String[] selectionArgs,
            String sortOrder);

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
    public abstract List<T> query(String[] projection, String selection,
            String[] selectionArgs, String sortOrder);

    /**
     * @see ContentResolver#query(Uri, String[], String, String[], String)
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public abstract Cursor query(Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder);

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
    public abstract int update(ContentValues values, String where,
            String[] selectionArgs);

    /**
     * Update table by id.
     * 
     * @param object
     *            - contains id and values for update.
     * @return
     * @throws IllegalAccessException
     */
    public abstract int update(T object) throws IllegalAccessException;

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
    public abstract int update(T object, String where, String[] selectionArgs);

    /**
     * @see ContentResolver#update(Uri, ContentValues, String, String[])
     * @param uri
     * @param values
     * @param where
     * @param selectionArgs
     * @return
     */
    public abstract int update(Uri uri, ContentValues values, String where,
            String[] selectionArgs);

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
    public abstract ContentProviderResult[] updateList(List<T> objects)
            throws IllegalAccessException, RemoteException,
            OperationApplicationException;

}