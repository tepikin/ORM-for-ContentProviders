/**************************************************************************
 * TODO copyright
 *
 * $Id: BinderBundleTest.java 71 2012-05-23 01:28:27Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/BinderBundleTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import android.os.Bundle;

/**
 * 
 */
public class BinderBundleTest extends android.test.AndroidTestCase {

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

        BundleMapper binderObject = new BundleMapper();
        Bundle testBundle = binderObject.createBundle(dataSourceBundle);

        assertTrue(testBundle.containsKey(fieldBoolean));
        assertEquals(testBundle.get(fieldBoolean), true);

        assertTrue(testBundle.containsKey(fieldInt));
        assertEquals(testBundle.get(fieldInt), intValue);

        assertTrue(testBundle.containsKey(fieldString));
        assertEquals(testBundle.get(fieldString), text);

    }

    public void testParcelBindBundle() {
        Bundle bundle = new Bundle();
        String parcelKey = "parcel";
        ParcelObjectForTest parcelObject = new ParcelObjectForTest();
        parcelObject.put(parcelKey, parcelKey);
        bundle.putParcelable(parcelKey, parcelObject);

        DataSourceBundle dataSourceBundle = new DataSourceBundle(bundle);

        BundleMapper bundleMapper = new BundleMapper();
        Bundle testBundle = bundleMapper.createBundle(dataSourceBundle);

        assertTrue(testBundle.containsKey(parcelKey));
        ParcelObjectForTest parcelObjectForTest = (ParcelObjectForTest) testBundle
                .get(parcelKey);
        assertEquals(parcelObjectForTest.get(parcelKey), parcelKey);

    }
}
