/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceCursorRow.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/DataSourceCursorRow.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;

/**
 * Wrapper class for convert Cursor with single row to IDataSource.
 */
/* package */class DataSourceCursorRow implements IDataSource {
    private String[] columnNames = new String[] {};

    private int currentColumn = -1;

    private Cursor cursor;

    private Map<String, String> methodNames = new HashMap<String, String>();

    public <T> DataSourceCursorRow(Cursor cursor) {
        super();

        if (cursor == null) {
            throw new IllegalArgumentException("Cursor should not be null");
        }

        this.cursor = cursor;
        this.columnNames = cursor.getColumnNames();

        initMethodMap();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#getFieldName()
     */
    @Override
    public String getFieldName() {
        int columnCount = columnNames.length;
        if (currentColumn < 0 || currentColumn >= columnCount) {
            throw new IllegalArgumentException("Wrong column number "
                    + currentColumn + " of " + columnCount);
        }

        return columnNames[currentColumn];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.enterra.android.lib.commons.mapping.IDataSource#getValue(java.lang
     * .Class)
     */
    @Override
    public Object getValue(Class<?> asClass) {
        int columnCount = columnNames.length;
        if (currentColumn < 0 || currentColumn >= columnCount) {
            throw new IllegalArgumentException("Wrong column number "
                    + currentColumn + " of " + columnCount);
        }

        if (asClass == null) {
            return cursor.getString(currentColumn);
        }

        // invoke standard method for get data from cursor.
        try {
            String methodName = methodNames.get(asClass.getName());
            if (methodName != null) {
                Method method = cursor.getClass().getMethod(methodName,
                        int.class);
                if (method != null) {
                    return method.invoke(cursor, currentColumn);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // invoke custom get data methods - for not standard types.
        if (asClass.isAssignableFrom(Byte.class)
                || asClass.isAssignableFrom(byte.class)) {
            return (byte) (cursor.getShort(currentColumn));
        }
        if (asClass.isAssignableFrom(Date.class)) {
            return new Date(cursor.getLong(currentColumn));
        }
        if (asClass.isAssignableFrom(Boolean.class)
                || asClass.isAssignableFrom(boolean.class)) {
            String value = cursor.getString(currentColumn);
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1")) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }

        // if can't get data of this type.
        throw new IllegalArgumentException("Un suported class required: "
                + asClass);

    }

    public void moveToFirst() {
        currentColumn = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#next()
     */
    @Override
    public boolean next() {
        if (currentColumn < 0) {
            moveToFirst();
        } else {
            currentColumn++;
        }
        int columnCount = columnNames.length;
        if (currentColumn < 0 || currentColumn >= columnCount) {
            return false;
        }

        return true;
    }

    public boolean nextRow() {
        currentColumn = -1;
        return cursor.moveToNext();
    }

    /**
     * Put methods names for get data from cursor.
     */
    private void initMethodMap() {
        methodNames.put(byte[].class.getName(), "getBlob");
        methodNames.put(Byte[].class.getName(), "getBlob");
        methodNames.put(Double.class.getName(), "getDouble");
        methodNames.put(double.class.getName(), "getDouble");
        methodNames.put(Float.class.getName(), "getFloat");
        methodNames.put(float.class.getName(), "getFloat");
        methodNames.put(Long.class.getName(), "getLong");
        methodNames.put(long.class.getName(), "getLong");
        methodNames.put(Integer.class.getName(), "getInt");
        methodNames.put(int.class.getName(), "getInt");
        methodNames.put(Short.class.getName(), "getShort");
        methodNames.put(short.class.getName(), "getShort");
        methodNames.put(String.class.getName(), "getString");
    }

}
