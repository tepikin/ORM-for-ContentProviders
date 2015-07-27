/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceObjectTest.java 33 2012-04-18 07:58:07Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/DataSourceObjectTest.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

public class DataSourceObjectTest extends android.test.AndroidTestCase {
    private class TestObject {
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

    private String fieldBoolean = "fieldBoolean";

    private String fieldInt = "fieldInt";

    private String fieldString = "fieldString";

    private int intValue = 1;

    private String text = "text";

    public void testDataSourceObject() {

        TestObject testObject = new TestObject();

        DataSourceObject dataSourceObject = new DataSourceObject(testObject);

        assertTrue(dataSourceObject.next());

        while (dataSourceObject.next()) {
            testFields(dataSourceObject);
        }

    }

    /**
     * @param fieldBoolean
     * @param fieldString
     * @param fieldInt
     * @param text
     * @param intValue
     * @param dataSourceObject
     */
    private void testFields(DataSourceObject dataSourceObject) {
        if (fieldString.equals(dataSourceObject.getFieldName())) {
            assertEquals(dataSourceObject.getValue(null), text);
        } else if (fieldInt.equals(dataSourceObject.getFieldName())) {
            assertEquals(dataSourceObject.getValue(null), intValue);
        } else if (fieldBoolean.equals(dataSourceObject.getFieldName())) {
            assertEquals(dataSourceObject.getValue(null), true);
        }
    }
}
