/**************************************************************************
 * TODO copyright
 *
 * $Id: TestUtils.java 33 2012-04-18 07:58:07Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/TestUtils.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.util.Date;

import junit.framework.TestCase;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

/**
 * Class for test utils methods.
 */

public class TestUtils {

    /**
     * Class of object for test binding.
     */

    public static class ClassForTest {
        @MappedField(name = "booleanField_primitiveType")
        private boolean booleanField;

        @MappedField(name = "booleanField")
        private Boolean booleanField_CUSTOM_NAME;

        @MappedField
        private byte[] byteArrayField;

        @MappedField
        private byte byteField;

        @MappedField
        private Date dateField;

        @MappedField
        private double doubleField;

        @MappedField
        private Double doubleObjectField;

        @MappedField
        private float floatField;

        @MappedField
        private int intField;

        @MappedField
        private long longField;

        @MappedField
        private short shortField;

        @MappedField
        private String stringField;

    }

    /**
     * Check is Bundle contains model values.
     * 
     * @param bundle
     */
    public static void checkTestBundle(Bundle bundle) {

        TestCase.assertEquals(true,
                bundle.getBoolean("booleanField_primitiveType"));
        TestCase.assertEquals(true, bundle.getBoolean("booleanField"));
        TestCase.assertEquals(1, bundle.getLong("dateField"));

        byte[] bytes = bundle.getByteArray("byteArrayField");
        TestCase.assertEquals(1, bytes[0]);
        TestCase.assertEquals(2, bytes[1]);

        TestCase.assertEquals(7.0, bundle.getDouble("doubleObjectField"));

        TestCase.assertEquals(8, bundle.getByte("byteField"));
        TestCase.assertEquals(1.4, bundle.getDouble("doubleField"));
        TestCase.assertEquals(112345, bundle.getLong("longField"));
        TestCase.assertEquals(1, bundle.getShort("shortField"));

        TestCase.assertEquals(1.3f, bundle.getFloat("floatField"));
        TestCase.assertEquals(123, bundle.getInt("intField"));
        TestCase.assertEquals("testString", bundle.getString("stringField"));
    }

    /**
     * Check is ContentValues contains model values.
     * 
     * @param contentValues
     */
    public static void checkTestContentValues(ContentValues contentValues) {
        TestCase.assertEquals(true,
                contentValues.getAsBoolean("booleanField_primitiveType")
                        .booleanValue());
        TestCase.assertEquals(true, contentValues.getAsBoolean("booleanField")
                .booleanValue());

        TestCase.assertEquals(1, contentValues.getAsLong("dateField")
                .longValue());

        TestCase.assertEquals(1,
                contentValues.getAsByteArray("byteArrayField")[0]);
        TestCase.assertEquals(2,
                contentValues.getAsByteArray("byteArrayField")[1]);

        TestCase.assertEquals(7.0,
                contentValues.getAsDouble("doubleObjectField").doubleValue());

        TestCase.assertEquals(8, contentValues.getAsByte("byteField")
                .byteValue());
        TestCase.assertEquals(1.4, contentValues.getAsDouble("doubleField")
                .doubleValue());
        TestCase.assertEquals(112345, contentValues.getAsLong("longField")
                .longValue());
        TestCase.assertEquals(1, contentValues.getAsShort("shortField")
                .shortValue());

        TestCase.assertEquals(1.3f, contentValues.getAsFloat("floatField")
                .floatValue());
        TestCase.assertEquals(123, contentValues.getAsInteger("intField")
                .intValue());
        TestCase.assertEquals("testString",
                contentValues.getAsString("stringField"));
    }

    /**
     * Check is Cursor contains model values.<br>
     * Check current selected row,- if cursor not open need open and move it to
     * first value.<br>
     * Not auto close cursor.
     * 
     * @param object
     */
    public static void checkTestCursor(Cursor cursor) {
        TestCase.assertEquals(true, cursor.getInt(cursor
                .getColumnIndex("booleanField_primitiveType")));
        TestCase.assertEquals(1,
                cursor.getInt(cursor.getColumnIndex("booleanField")));
        TestCase.assertEquals(1,
                cursor.getLong(cursor.getColumnIndex("dateField")));
        TestCase.assertEquals(1,
                cursor.getBlob(cursor.getColumnIndex("byteArrayField"))[0]);
        TestCase.assertEquals(2,
                cursor.getBlob(cursor.getColumnIndex("byteArrayField"))[1]);

        TestCase.assertEquals(7,
                cursor.getDouble(cursor.getColumnIndex("doubleObjectField")));

        TestCase.assertEquals(8,
                cursor.getShort(cursor.getColumnIndex("byteField")));
        TestCase.assertEquals(1.4,
                cursor.getDouble(cursor.getColumnIndex("doubleField")));
        TestCase.assertEquals(112345,
                cursor.getLong(cursor.getColumnIndex("longField")));
        TestCase.assertEquals(1,
                cursor.getShort(cursor.getColumnIndex("shortField")));

        TestCase.assertEquals(1.3f,
                cursor.getFloat(cursor.getColumnIndex("floatField")));
        TestCase.assertEquals(123,
                cursor.getInt(cursor.getColumnIndex("intField")));
        TestCase.assertEquals("testString",
                cursor.getString(cursor.getColumnIndex("stringField")));
    }

    /**
     * Check is Object contains model values.
     * 
     * @param object
     */
    public static void checkTestObject(ClassForTest object) {
        TestCase.assertEquals(true, object.booleanField);
        TestCase.assertEquals(true,
                object.booleanField_CUSTOM_NAME.booleanValue());
        TestCase.assertEquals(1, object.dateField.getTime());
        TestCase.assertEquals((byte) 1, object.byteArrayField[0]);
        TestCase.assertEquals((byte) 2, object.byteArrayField[1]);

        TestCase.assertEquals(7.0, object.doubleObjectField.doubleValue());

        TestCase.assertEquals(8, object.byteField);
        TestCase.assertEquals(1.4, object.doubleField);
        TestCase.assertEquals(112345, object.longField);
        TestCase.assertEquals(1, object.shortField);

        TestCase.assertEquals(1.3f, object.floatField);
        TestCase.assertEquals(123, object.intField);
        TestCase.assertEquals("testString", object.stringField);
    }

    /**
     * Generate Bundle with binded model values.
     * 
     * @return Bundle
     */
    public static Bundle getTestBundle() {

        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanField_primitiveType", true);
        bundle.putBoolean("booleanField", true);
        bundle.putLong("dateField", 1);
        bundle.putByteArray("byteArrayField", new byte[] { 1, 2 });

        bundle.putDouble("doubleObjectField", 7);

        bundle.putByte("byteField", (byte) 8);
        bundle.putDouble("doubleField", 1.4);
        bundle.putLong("longField", 112345);
        bundle.putShort("shortField", (short) 1);

        bundle.putFloat("floatField", 1.3f);
        bundle.putInt("intField", 123);
        bundle.putString("stringField", "testString");
        return bundle;
    }

    /**
     * Generate ContentValues with binded model values.
     * 
     * @return ContentValues
     */
    public static ContentValues getTestContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("booleanField_primitiveType", true);
        contentValues.put("booleanField", true);
        contentValues.put("dateField", 1);
        contentValues.put("byteArrayField", new byte[] { 1, 2 });
        contentValues.put("doubleObjectField", 7);

        contentValues.put("byteField", 8);
        contentValues.put("doubleField", 1.4);
        contentValues.put("longField", 112345);
        contentValues.put("shortField", 1);

        contentValues.put("floatField", 1.3f);
        contentValues.put("intField", 123);
        contentValues.put("stringField", "testString");
        return contentValues;
    }

    /**
     * Generate Cursor with binded model values.
     * 
     * @return Cursor
     */
    public static Cursor getTestCursor() {

        MatrixCursor cursor = new MatrixCursor(new String[] { "floatField",
                "intField", "stringField", "byteField", "doubleField",
                "longField", "shortField", "doubleObjectField",
                "byteArrayField", "dateField", "booleanField",
                "booleanField_primitiveType" });
        cursor.addRow(new Object[] { 1.3f, 123, "testString", 8, 1.4, 112345,
                1, 7, new byte[] { 1, 2 }, 1, true, true });
        cursor.addRow(new Object[] { 1.3f, 123, "testString", 8, 1.4, 112345,
                1, 7, new byte[] { 1, 2 }, 1, true, true });
        return cursor;
    }

    /**
     * Generate Object with binded model values.
     * 
     * @return ClassForTest
     */
    public static ClassForTest getTestObject() {
        ClassForTest classForTest = new ClassForTest();
        classForTest.booleanField_CUSTOM_NAME = new Boolean(true);
        classForTest.booleanField = true;
        classForTest.dateField = new Date(1);
        classForTest.byteArrayField = new byte[] { 1, 2 };
        classForTest.doubleObjectField = new Double(7);

        classForTest.byteField = 8;

        classForTest.doubleField = 1.4;

        classForTest.floatField = 1.3f;

        classForTest.intField = 123;

        classForTest.longField = 112345;

        classForTest.shortField = 1;

        classForTest.stringField = "testString";
        return classForTest;
    }

    /**
     * Hidden constructor.
     */
    private TestUtils() {
    }

}
