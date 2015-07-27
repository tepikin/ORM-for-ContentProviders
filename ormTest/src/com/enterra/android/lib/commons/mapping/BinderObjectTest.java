/**************************************************************************
 * TODO copyright
 *
 * $Id: BinderObjectTest.java 71 2012-05-23 01:28:27Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/BinderObjectTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import android.os.Bundle;

public class BinderObjectTest extends android.test.AndroidTestCase {
    public static class TestObject {
        @MappedField
        public boolean fieldBoolean = true;

        @MappedField
        public int fieldInt = 1;

        @MappedField
        public String fieldString = "text";

        public TestObject() {
            fieldBoolean = true;

            fieldString = "text";

            fieldInt = 1;
        }
    }

    public void testDataSourceObject() {
        Bundle bundle = new Bundle();
        String fieldBoolean = "fieldBoolean";
        String fieldString = "fieldString";
        String fieldInt = "fieldInt";
        String text = "text";
        int intValue = 1;
        bundle.putBoolean(fieldBoolean, true);
        bundle.putString(fieldString, text);
        bundle.putInt(fieldInt, intValue);

        DataSourceBundle dataSourceBundle = new DataSourceBundle(bundle);

        ObjectMapper binderObject = new ObjectMapper();
        TestObject testObject = binderObject.createObject(dataSourceBundle,
                TestObject.class);

        assertEquals(testObject.fieldString, text);
        assertEquals(testObject.fieldInt, intValue);
        assertEquals(testObject.fieldBoolean, true);

    }

    public void testParcelBindObject() {
        Bundle bundle = new Bundle();
        String parcelKey = "parcel";
        String text = "text";
        ParcelObjectForTest parcelObject = new ParcelObjectForTest();
        parcelObject.put(parcelKey, parcelKey);
        bundle.putParcelable(parcelKey, parcelObject);
        bundle.putString(text, text);

        DataSourceBundle dataSourceBundle = new DataSourceBundle(bundle);

        ObjectMapper objectMapper = new ObjectMapper();
        ParcelContainsObjectForTest parcelContainsObject = objectMapper
                .createObject(dataSourceBundle,
                        ParcelContainsObjectForTest.class);

        assertTrue(parcelContainsObject != null);
        ParcelObjectForTest parcelObjectForTest = parcelContainsObject
                .getParcel();
        assertEquals(parcelObjectForTest.get(parcelKey), parcelKey);
        assertEquals(parcelContainsObject.getText(), text);

    }
}
