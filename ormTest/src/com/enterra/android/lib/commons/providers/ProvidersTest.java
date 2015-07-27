/**************************************************************************
 * TODO Copyright
 *
 * $Id: ProvidersTest.java 66 2012-05-17 10:55:52Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/ProvidersTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.providers;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.enterra.android.lib.commons.mapping.MapperHelper;
import com.enterra.android.lib.commons.sql.TableUtils;

/**
 * Class for test methods from providers package.
 */
public class ProvidersTest extends AndroidTestCase {

    private ContentResolver contentResolver;

    private int insertsCount = 2;

    private String providerAuthority = "com.enterra.android.lib.commons.client";

    private Uri uri = Uri.parse("content://" + providerAuthority + "/myitem");

    /**
     * Test method for create table.
     */
    public void testCreateTable() {
        String sql = TableUtils.createTableQuery(TableForTest2.class);

        assertTrue(sql.contains("CREATE TABLE IF NOT EXISTS myitem2 "));
        assertTrue(sql.contains("id INTEGER PRIMARY KEY"));
        assertTrue(sql.contains("date_1 INTEGER NOT NULL"));
        assertTrue(sql.contains("text TEXT UNIQUE"));
    }

    /**
     * Test method for bulk insert.
     */
    public void testProviderBulkInsert() {

        ContentValues[] values = new ContentValues[insertsCount];

        for (int i = 0; i < insertsCount; i++) {
            TableForTest object = new TableForTest();
            values[i] = MapperHelper.objectToContentValues(object);
        }

        contentResolver.bulkInsert(uri, values);
    }

    /**
     * Test method for delete.
     */
    public void testProviderDelete() {
        contentResolver.delete(uri, null, null);
    }

    /**
     * Test method for insert.
     */
    public void testProviderInsert() {
        ContentValues values;

        TableForTest object1 = new TableForTest();
        values = MapperHelper.objectToContentValues(object1);
        contentResolver.insert(uri, values);

    }

    /**
     * Test method for query.
     */
    public void testProviderQuery() {

        testProviderDelete();
        testProviderBulkInsert();

        Cursor cursor = contentResolver.query(uri, new String[] { "id" }, null,
                new String[] {}, null);

        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        assertEquals(insertsCount, count);
    }

    /**
     * Test method for query.
     */
    public void testProviderQueryConverter() {

        testProviderDelete();
        testProviderBulkInsert();

        Cursor cursor = contentResolver.query(uri, null, null, new String[] {},
                null);

        List<TableForTest> objects = MapperHelper.cursorToObjectList(cursor,
                TableForTest.class);

        assertEquals(insertsCount, objects.size());

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contentResolver = getContext().getContentResolver();
    }

}
