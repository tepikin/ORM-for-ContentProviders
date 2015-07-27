/**************************************************************************
 * TODO copyright
 *
 * $Id: ObjectMapper.java 55 2012-04-26 04:46:58Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/ObjectMapper.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.enterra.android.lib.commons.logging.ELog;

/**
 * Class for bind object from DataSource.
 */
/* package */class ObjectMapper {

    private static final String TAG = "Binder";

    private Map<String, Field> fieldsMap;

    /**
     * Bind object fields by IDataSource.
     * 
     * @param dataSource
     * @param bundle
     */
    public void bind(Object object, IDataSource dataSource) {
        // test inner parameters
        if (object == null) {
            throw new IllegalArgumentException("Binding object is null");
        }
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource is null");
        }

        createFieldsHashMap(object);

        // start bind values
        while (dataSource.next()) {
            bind(dataSource.getFieldName(), dataSource, object);
        }
    }

    /**
     * Bind concrete object field by fieldName.
     * 
     * @param dataSource
     * @param bundle
     */
    public boolean bind(String fieldName, IDataSource dataSource, Object object) {
        // test inner parameters
        if (object == null) {
            throw new IllegalArgumentException("Binding object is null");
        }
        if (fieldName == null || fieldName.length() <= 0) {
            throw new IllegalArgumentException("FieldName is empty");
        }

        // start bind values
        Field field = fieldsMap.get(fieldName);
        if (field == null) {
            throw new IllegalArgumentException(String.format(
                    "No field whith name \"%s\"", fieldName));
        }

        // Convert object to field class.
        Class<?> fieldClass = field.getType();
        Object valueToSet = dataSource.getValue(fieldClass);

        // Set field value.
        setField(valueToSet, object, field);
        return true;
    }

    /**
     * Generate new object and bind it fields by IDataSource.
     * 
     * @param dataSource
     * @param bundle
     */
    public <T> T createObject(IDataSource dataSource, Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (IllegalAccessException e) {
            ELog.w(TAG, e.toString());
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Class not contains default constructor");
        } catch (InstantiationException e) {
            ELog.w(TAG, e.toString());
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Class not contains default constructor");
        }
        bind(object, dataSource);
        return object;
    }

    /**
     * Put fields and fields names to hash map.
     */
    private void createFieldsHashMap(Object object) {
        fieldsMap = new HashMap<String, Field>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            MappedField annotation = field.getAnnotation(MappedField.class);
            if (annotation == null || !annotation.isMappedIn()) {
                continue;
            }
            String fieldName = annotation.name();
            if (fieldName.length() <= 0) {
                fieldName = field.getName();
            }
            fieldsMap.put(fieldName, field);
        }
    }

    /**
     * Set object field by name.
     * 
     * @param value
     * @param object
     * @param field
     * @return
     */
    private boolean setField(Object value, Object object, Field field) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            ELog.w(TAG, e.toString());
            ELog.w(TAG, "object=" + object);
            ELog.w(TAG, "field=" + field);
            ELog.w(TAG, "value=" + value);
            ELog.w(TAG, "value Class=" + value.getClass());
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            ELog.w(TAG, e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
