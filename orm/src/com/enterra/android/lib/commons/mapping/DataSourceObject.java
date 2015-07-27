/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceObject.java 55 2012-04-26 04:46:58Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/DataSourceObject.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.enterra.android.lib.commons.logging.ELog;

/**
 * Wrapper class for convert Object to IDataSource.
 */
/* package */class DataSourceObject implements IDataSource {
    private class FieldItem {
        private Field field;

        private String name;

        /**
         * @param field
         * @param name
         */
        public FieldItem(Field field, String name) {
            super();
            this.field = field;
            this.name = name;
        }

    }

    private static final String TAG = "DataSourceObject";

    private int count;

    private int currentFieldNum = -1;

    private List<FieldItem> fields = null;

    private Object object;

    public DataSourceObject(Object object) {
        super();
        if (object == null) {
            throw new IllegalArgumentException("Object should not be null");
        }

        this.object = object;

        initializeFields(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#getFieldName()
     */
    @Override
    public String getFieldName() {
        return getField().name;
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
        Object value = null;
        try {
            FieldItem fieldItem = getField();
            fieldItem.field.setAccessible(true);
            Object fieldValue = fieldItem.field.get(object);
            value = ClassCaster.getAsClass(fieldValue, asClass);
        } catch (IllegalArgumentException e) {
            ELog.w(TAG, e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            ELog.w(TAG, e.toString());
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Move dataSource to first value.
     */
    public void moveToFirst() {
        count = fields.size();
        currentFieldNum = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#next()
     */
    @Override
    public boolean next() {
        if (currentFieldNum < 0) {
            moveToFirst();
        } else {
            currentFieldNum++;
        }
        if (currentFieldNum < 0 || currentFieldNum >= count) {
            return false;
        }

        return true;
    }

    private FieldItem getField() {
        return fields.get(currentFieldNum);
    }

    /**
     * Initialize fields and its names.
     * 
     * @param object
     */
    private void initializeFields(Object object) {
        // initialize fields list
        fields = new ArrayList<FieldItem>();
        Field[] allFields = object.getClass().getDeclaredFields();
        for (Field field : allFields) {
            MappedField annotation = field.getAnnotation(MappedField.class);
            if (annotation != null && annotation.isMappedOut()) {
                // get name
                String name = annotation.name();
                if (name == null || name.length() <= 0) {
                    name = field.getName();
                }

                // get value
                // not need because can changed.

                // save Field
                FieldItem item = new FieldItem(field, name);
                fields.add(item);
            }
        }
    }

}
