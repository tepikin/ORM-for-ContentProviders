/**************************************************************************
 * TODO copyright
 *
 * $Id: AttachableContentProvidersRouter.java 70 2012-05-18 03:09:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/providers/AttachableContentProvidersRouter.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider - for contain all single providers of project.
 * <P>
 * Simple usage:
 * 
 * <pre>
 * public class NSProvider extends ProviderJoiner {
 *
 *     private DatabaseHelper dbHelper;
 *
 *     &#064;Override
 *     protected List<AttachableContentProvider> createProviders(Context context) {
 *        dbHelper = new DatabaseHelper(context);
 *        List<AttachableContentProvider> providers = new ArrayList<AttachableContentProvider>();
 *        
 *        providers.add(new DefaultSqLProvider(dbHelper, context)); //Provider for all DB tables.
 *        providers.add(new SingleSqlProvider(dbHelper,"my_table_name" ,context)); //Provider for single table.
 *        
 *        providers.add(new MatchesProvider()); //Example for add custom provider.
 *        
 *        return providers;
 *    }
 * }
 * </pre>
 */
public abstract class AttachableContentProvidersRouter extends ContentProvider {

    /**
     * List of providers.
     */
    private List<AttachableContentProvider> providers = new ArrayList<AttachableContentProvider>();

    /**
     * Add provider at end of list.
     * 
     * @param provider
     */
    public void addProvider(AttachableContentProvider provider) {
        providers.add(provider);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#bulkInsert(android.net.Uri,
     * android.content.ContentValues[])
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return getAppropriateProvider(uri).bulkInsert(uri, values);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#delete(android.net.Uri,
     * java.lang.String, java.lang.String[])
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = getAppropriateProvider(uri).delete(uri, selection,
                selectionArgs);
        return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        String type = getAppropriateProvider(uri).getType(uri);
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#insert(android.net.Uri,
     * android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri retUri = getAppropriateProvider(uri).insert(uri, values);
        return retUri;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        List<AttachableContentProvider> list = createProviders(getContext());
        if (list != null) {
            providers.addAll(list);
        }
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
        Cursor cursor = getAppropriateProvider(uri).query(uri, projection,
                selection, selectionArgs, sortOrder);
        return cursor;
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
        int count = getAppropriateProvider(uri).update(uri, values, selection,
                selectionArgs);
        return count;
    }

    /**
     * Method for create providers.
     * <P>
     * Simple realization:
     * 
     * <pre>
 * protected boolean createProviders(Context context) {
 *     dbHelper = new DatabaseHelper(context);
 *     
 *      addProvider(new DefaultSqLProvider(dbHelper, context)); //Provider for all DB tables.              
 *      addProvider(new SingleSqlProvider(dbHelper,"my_table_name" ,context)); //Provider for single table.
 *                                                                                                         
 *      addProvider(new MatchesProvider()); //Example for add custom provider.                             
 *     
 *     return true;
 * }
 * </pre>
     * 
     * @param context
     * @return true if all provides created successfully.
     */
    protected abstract List<AttachableContentProvider> createProviders(
            Context context);

    /**
     * Get first matches provider by Uri.
     * 
     * @param uri
     * @return
     */
    private AttachableContentProvider getAppropriateProvider(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri should not be null");
        }
        for (AttachableContentProvider provider : providers) {
            if (provider.isMatched(uri)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
    }

}
