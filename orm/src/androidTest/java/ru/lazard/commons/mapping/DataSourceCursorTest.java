/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceCursorTest.java 31 2012-04-18 06:33:19Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/DataSourceCursorTest.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import android.database.MatrixCursor;

public class DataSourceCursorTest extends android.test.AndroidTestCase {
    private String fieldBoolean = "fieldBoolean";

    private String fieldInt = "fieldInt";

    private String fieldString = "fieldString";

    private int intValue = 1;

    private String text = "text";

    public void testDataSourceObject() {

        String[] columns = new String[] { fieldString, fieldInt, fieldBoolean };
        MatrixCursor matrixCursor = new MatrixCursor(columns);
        matrixCursor.addRow(new Object[] { text, intValue, true });
        matrixCursor.moveToFirst();
        DataSourceCursor dataSourceCursor = new DataSourceCursor(matrixCursor);

        assertTrue(dataSourceCursor.next());
        assertTrue(checkValues(dataSourceCursor,
                dataSourceCursor.getFieldName()));

        assertTrue(dataSourceCursor.next());
        assertTrue(checkValues(dataSourceCursor,
                dataSourceCursor.getFieldName()));

        assertTrue(dataSourceCursor.next());
        assertTrue(checkValues(dataSourceCursor,
                dataSourceCursor.getFieldName()));

        assertFalse(dataSourceCursor.next());

    }

    /**
     * @param dataSourceCursor
     * @param fieldName
     */
    private boolean checkValues(DataSourceCursor dataSourceCursor,
            String fieldName) {
        if (fieldName.equals(fieldInt)) {
            assertEquals(Integer.parseInt(dataSourceCursor.getValue(null)
                    .toString()), intValue);
            return true;
        }
        if (fieldName.equals(fieldString)) {
            assertEquals(dataSourceCursor.getValue(null), text);
            return true;
        }
        if (fieldName.equals(fieldBoolean)) {
            assertEquals(Boolean.parseBoolean(dataSourceCursor.getValue(null)
                    .toString()), true);
            return true;
        }
        return false;
    }
}
