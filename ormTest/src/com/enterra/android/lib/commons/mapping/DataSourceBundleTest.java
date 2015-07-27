/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceBundleTest.java 31 2012-04-18 06:33:19Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/DataSourceBundleTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import android.os.Bundle;

public class DataSourceBundleTest extends android.test.AndroidTestCase {
    private String fieldBoolean = "fieldBoolean";

    private String fieldInt = "fieldInt";

    private String fieldString = "fieldString";

    private int intValue = 1;

    private String text = "text";

    public void testDataSourceBundle() {
        Bundle bundle = new Bundle();

        bundle.putBoolean(fieldBoolean, true);
        bundle.putString(fieldString, text);
        bundle.putInt(fieldInt, intValue);

        DataSourceBundle dataSourceBundle = new DataSourceBundle(bundle);

        assertTrue(dataSourceBundle.next());
        assertTrue(checkValues(dataSourceBundle,
                dataSourceBundle.getFieldName()));

        assertTrue(dataSourceBundle.next());
        assertTrue(checkValues(dataSourceBundle,
                dataSourceBundle.getFieldName()));

        assertTrue(dataSourceBundle.next());
        assertTrue(checkValues(dataSourceBundle,
                dataSourceBundle.getFieldName()));

        assertFalse(dataSourceBundle.next());

    }

    /**
     * @param fieldBoolean
     * @param fieldString
     * @param fieldInt
     * @param text
     * @param intValue
     * @param dataSourceBundle
     * @param fieldName
     */
    private boolean checkValues(DataSourceBundle dataSourceBundle,
            String fieldName) {
        if (fieldName.equals(fieldInt)) {
            assertEquals(dataSourceBundle.getValue(null), intValue);
            return true;
        }
        if (fieldName.equals(fieldString)) {
            assertEquals(dataSourceBundle.getValue(null), text);
            return true;
        }
        if (fieldName.equals(fieldBoolean)) {
            assertEquals(dataSourceBundle.getValue(null), true);
            return true;
        }
        return false;
    }
}
