/**************************************************************************
 * TODO copyright
 *
 * $Id: ContentValuesMapper.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/ContentValuesMapper.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.ContentValues;

/**
 * Class for bind ContentValues by IDataSource.
 */
/* package */class ContentValuesMapper {

    /**
     * Name of method for put value in ContentValues. Used by reflection.
     */
    private static final String NAME_OF_METHOD_INVORKE = "put";

    /**
     * Generate new ContentValues and bind fields from DataSource to it.
     * 
     * @param dataSource
     */
    public ContentValues createContentValues(IDataSource dataSource) {
        // test inner parameters
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource should not be null");
        }
        // start bind values
        ContentValues bundle = new ContentValues();
        put(dataSource, bundle);
        return bundle;
    }

    /**
     * Put fields from DataSource to existed ContentValues.
     * 
     * @param dataSource
     * @param bundle
     */

    public void put(IDataSource dataSource, ContentValues contentValues) {
        // test inner parameters
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource should not be null");
        }
        if (contentValues == null) {
            throw new IllegalArgumentException(
                    "ContentValues should not be null");
        }
        // start bind values
        while (dataSource.next()) {
            String key = dataSource.getFieldName();
            Object value = dataSource.getValue(null);
            put(key, value, contentValues);
        }
    }

    /**
     * Put abstract object to contentValues.<br>
     * Only standard bundle objects can be put to bundle.
     * 
     * @param key
     * @param value
     *            Object for set - Supported types:
     *            <ul>
     *            <li><code>String</code></li>
     *            <li><code>Boolean</code></li>
     *            <li><code>Byte</code></li>
     *            <li><code>byte[]</code></li>
     *            <li><code>Double</code></li>
     *            <li><code>Float</code></li>
     *            <li><code>Integer</code></li>
     *            <li><code>Short</code></li>
     *            <li><code>String</code></li>
     *            <li><code>Long</code></li>
     *            </ul>
     * @param contentValues
     */
    public void put(String key, Object value, ContentValues contentValues) {
        // Empty fields must mapped as null.
        if (value == null) {
            contentValues.putNull(key);
            return;
        }

        try {
            /**
             * Put value to contentValues by reflection.
             */
            Method method = ContentValues.class.getDeclaredMethod(
                    NAME_OF_METHOD_INVORKE, String.class, value.getClass());
            if (method != null) {
                method.invoke(contentValues, key, value);
                return;
            }

        } catch (SecurityException e) {
            // should not happen ever
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // should not happen ever
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // should not happen ever
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // should not happen ever
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // should not happen ever
            e.printStackTrace();
        }

        // if field not mapped
        throw new IllegalArgumentException(
                String.format(
                        "Can't map to ContentValues types : key=\"%s\" object=\"%s\" class=\"%s\"",
                        key, value.toString(), value.getClass().toString()));

    }
}
