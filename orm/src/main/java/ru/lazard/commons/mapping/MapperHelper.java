/**************************************************************************
 * TODO copyright
 *
 * $Id: MapperHelper.java 39 2012-04-24 06:13:12Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/MapperHelper.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import java.util.List;

/**
 * Helper for Object Mapping.<br>
 * Let you convert data between Object, Bundle, Cursor, ContentValues. <br>
 * Supported types:
 * <ul>
 * <li>Double
 * <li>Float
 * <li>Boolean
 * <li>Long
 * <li>Integer
 * <li>Short
 * <li>Byte
 * <li>Byte[]
 * <li>String
 * <li>Date (mapped as long everywhere except Object )
 * </ul>
 * <br>
 * <P>
 * Simple Usage:
 * 
 * <pre>
 * // example class of mapped object.
 * class MyObject {
 *     &#064;MappedField(name = &quot;custom_field_name&quot;)
 *     private int messageCount = 10;
 * 
 *     &#064;MappedField
 *     private String messageText = &quot;test&quot;;
 * }
 * 
 *  
 * Bundle bundle = new Bundle();
 * bundle.putString("messageText","test");
 * bundle.putString("custom_field_name",10);
 * 
 *                                                          // XXX usage here - one row for convert. 
 * MyObject object = cursorToObject(cursor, MyObject.class);// now Object contains values from bundle.
 *                                                          // object.messageCount == 10.
 *                                                          // object.messageText == &quot;test&quot;.
 * </pre>
 * 
 * 
 */
public final class MapperHelper {

    /**
     * Generate object by bundle values. <br>
     * <P>
     * Simple Usage:
     * 
     * <pre>
     * // example class of mapped object.
     * class MyObject {
     *     &#064;MappedField(name = &quot;custom_field_name&quot;)
     *     private int messageCount = 10;
     * 
     *     &#064;MappedField
     *     private String messageText = &quot;test&quot;;
     * }
     * 
     * 
     * 
     * Bundle bundle = new Bundle();
     * bundle.putString("messageText","test");
     * bundle.putString("custom_field_name",10);
     * 
     *                                                          // XXX usage here - one row for convert. 
     * MyObject object = cursorToObject(cursor, MyObject.class);// now Object contains values from bundle.
     *                                                          // object.messageCount == 10.
     *                                                          // object.messageText == &quot;test&quot;.
     * </pre>
     * 
     * @param <T>
     *            - class equal to inClass type.
     * @param bundle
     *            - bundle for put values to object.
     * @param clazz
     *            - class of object for map values.
     * @return new object mapped by bundle values
     * 
     * 
     */
    public static <T> T bundleToObject(Bundle bundle, Class<T> clazz) {
        ObjectMapper binder = new ObjectMapper();
        IDataSource dataSource = new DataSourceBundle(bundle);
        T genObject = binder.createObject(dataSource, clazz);
        return genObject;
    }

    /**
     * Generate object by cursor values.<br>
     * Generate by current selected row.<br>
     * MoveToFirst() or close() methods not called.
     * <P>
     * Simple Usage:
     * 
     * <pre>
     * // example class of mapped object.
     * class MyObject {
     *     &#064;MappedField(name = &quot;custom_field_name&quot;)
     *     private int messageCount = 10;
     * 
     *     &#064;MappedField
     *     private String messageText = &quot;test&quot;;
     * }
     * 
     * Cursor cursor = getCursor(); // cursor contains column &quot;messageText&quot; with value &quot;test&quot;
     *                              // and column &quot;custom_field_name&quot; with value 10.
     * 
     *                                                          // XXX usage here - one row for convert.
     * MyObject object = cursorToObject(cursor, MyObject.class);// now Object contains values from cursor.
     *                                                          // object.messageCount == 10.
     *                                                          // object.messageText == &quot;test&quot;.
     * </pre>
     * 
     * @param <T>
     *            - class equal to inClass type.
     * @param cursor
     *            - cursor for put values to object.
     * @param clazz
     *            - class of object for map values.
     * @return - new object mapped by cursor values.
     */
    public static <T> T cursorToObject(Cursor cursor, Class<T> clazz) {
        ObjectMapper binder = new ObjectMapper();
        IDataSource dataSource = new DataSourceCursor(cursor);
        T genObject = binder.createObject(dataSource, clazz);
        return genObject;
    }

    /**
     * Generate List of objects by cursor rows.<br>
     * Cursor call moveToNext() for move by cursor.<br>
     * <b>Cursor not closed automatically.</b>
     * 
     * <P>
     * Simple Usage:
     * 
     * <pre>
     * // example class of mapped object.
     * class MyObject {
     *     &#064;MappedField(name = &quot;custom_field_name&quot;)
     *     private int messageCount = 10;
     * 
     *     &#064;MappedField
     *     private String messageText = &quot;test&quot;;
     * }
     * 
     * Cursor cursor = getCursor(); // cursor contains same rows for columns:
     *                              // column &quot;messageText&quot; with value &quot;test&quot;
     *                              // column &quot;custom_field_name&quot; with value 10.
     * 
     *                                                                      // XXX usage here - one row for convert.
     * List&lt;MyObject> objects = cursorToObjectList(cursor, MyObject.class); // new list of Objects contains objects mapped by cursor.
     *                                                                      // If cursor contains 2 rows then :
     *                                                                      // object.get(0).messageCount == 10.
     *                                                                      // object.get(0)messageText == &quot;test&quot;.                                                                     
     *                                                                      // object.get(1).messageCount == 10.
     *                                                                      // object.get(1)messageText == &quot;test&quot;.
     * </pre>
     * 
     * 
     * 
     * @param <T>
     *            - class equal to inClass type.
     * @param cursor
     *            - cursor for put values to object.
     * @param clazz
     *            - class of object for map values.
     * @return - new list of object mapped by cursor values (one object for one
     *         row of cursor).
     * 
     * 
     */
    public static <T> List<T> cursorToObjectList(Cursor cursor, Class<T> clazz) {
        ObjectListMapper binder = new ObjectListMapper();
        IListDataSource dataSource = new DataSourceCursor(cursor);
        List<T> genList = binder.createObjectList(dataSource, clazz);
        return genList;
    }

    /**
     * Generate Bundle by object fields.<br>
     * Mapped only fields marked by {@link MappedField}
     * <P>
     * Simple Usage:
     * 
     * <pre>
     * // example class of mapped object.
     * class MyObject {
     *     &#064;MappedField(name = &quot;custom_field_name&quot;)
     *     private int messageCount = 10;
     * 
     *     &#064;MappedField
     *     private String messageText = &quot;test&quot;;
     * }
     * 
     * MyObject object = MyObject();// Object with set values.
     *                              // object.messageText=&quot;test&quot;.
     *                              // object.messageCount=10.
     * 
     *                                        // XXX usage here - one row for convert.
     * Bundle bundle = objectToBundle(object);// now "bundle" contains objects mapped from object fields.
     *                                        // bundle.getString("custom_field_name")=10;
     *                                        // bundle.getInt("messageCount")=10;
     * </pre>
     * 
     * @param object
     *            - for put in bundle.
     * @return - bundle with binded values from object.
     */
    public static Bundle objectToBundle(Object object) {
        BundleMapper binder = new BundleMapper();
        DataSourceObject dataSource = new DataSourceObject(object);
        Bundle genBundle = binder.createBundle(dataSource);
        return genBundle;
    }

    /**
     * Generate ContentValues by object fields.<br>
     * Mapped only fields marked by {@link MappedField}
     * <P>
     * Simple Usage:
     * 
     * <pre>
     * // example class of mapped object.
     * class MyObject {
     *     &#064;MappedField(name = &quot;custom_field_name&quot;)
     *     private int messageCount = 10;
     * 
     *     &#064;MappedField
     *     private String messageText = &quot;test&quot;;
     * }
     * 
     * MyObject object = MyObject();// Object with set values.
     *                              // object.messageText=&quot;test&quot;.
     *                              // object.messageCount=10.
     * 
     *                                                             // XXX usage here - one row for convert.
     * ContentValues contentValues = objectToContentValues(object);// now "contentValues" contains objects mapped from object fields.
     *                                                             // contentValues.getAsString("custom_field_name")=10;
     *                                                             // contentValues.getAsInt("messageCount")=10;
     * </pre>
     * 
     * @param object
     *            - for put in ContentValues.
     * @return - ContentValues with mapped values from object.
     * 
     */
    public static ContentValues objectToContentValues(Object object) {
        DataSourceObject dataSourceObject = new DataSourceObject(object);
        ContentValues values = new ContentValuesMapper()
                .createContentValues(dataSourceObject);
        return values;
    }

    private MapperHelper() {
    }

}
