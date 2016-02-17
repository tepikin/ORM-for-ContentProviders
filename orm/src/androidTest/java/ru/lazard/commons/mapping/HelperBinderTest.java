/**************************************************************************
 * TODO Copyright
 *
 * $Id: HelperBinderTest.java 38 2012-04-24 06:09:38Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/HelperBinderTest.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import junit.framework.TestCase;

import java.util.List;

import ru.lazard.commons.mapping.TestUtils.ClassForTest;

/**
 * Class for test methods from HelperBinder class.
 */

public class HelperBinderTest extends TestCase {
    /**
     * Test method for
     * {@link ru.lazard.commons.mapping.MapperHelper#bundleToObject(Bundle, Class)
     * .
     */
    public void testBundleToObject() {

        Bundle bundle = TestUtils.getTestBundle();

        ClassForTest classForTest = MapperHelper.bundleToObject(bundle,
                ClassForTest.class);

        TestUtils.checkTestObject(classForTest);
    }

    /**
     * Test method for
     * {@link ru.lazard.commons.mapping.MapperHelper#cursorToObject(Cursor, Class)
     * .
     */
    public void testCursorToObject() {
        Cursor cursor = TestUtils.getTestCursor();

        cursor.moveToFirst();
        ClassForTest classForTest = MapperHelper.cursorToObject(cursor,
                ClassForTest.class);
        cursor.close();

        TestUtils.checkTestObject(classForTest);
    }

    /**
     * Test method for
     * {@link ru.lazard.commons.mapping.MapperHelper#cursorToObjectList(Cursor, Class)
     * .
     */
    public void testCursorToObjectList() {
        Cursor cursor = TestUtils.getTestCursor();

        List<ClassForTest> classForTest = MapperHelper.cursorToObjectList(
                cursor, ClassForTest.class);
        cursor.close();

        for (ClassForTest classForTest2 : classForTest) {
            TestUtils.checkTestObject(classForTest2);
        }

    }

    /**
     * Test method for
     * {@link ru.lazard.commons.mapping.MapperHelper#objectToBundle(Object)}
     * .
     */
    public void testObjectToBundle() {
        ClassForTest classForTest = TestUtils.getTestObject();

        Bundle bundle = MapperHelper.objectToBundle(classForTest);

        TestUtils.checkTestBundle(bundle);
    }

    /**
     * Test method for
     * {@link ru.lazard.commons.mapping.MapperHelper#objectToContentValues(Object)} }
     * .
     */
    public void testObjectToContentValues() {
        ClassForTest classForTest = TestUtils.getTestObject();

        ContentValues contentValues = MapperHelper
                .objectToContentValues(classForTest);

        TestUtils.checkTestContentValues(contentValues);
    }

}
