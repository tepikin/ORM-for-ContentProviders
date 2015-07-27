/**************************************************************************
 * TODO copyright
 *
 * $Id: ClassCaster.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/ClassCaster.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.util.Date;

/**
 * Class for cast object to special class.
 */
/* package */final class ClassCaster {
    /**
     * Cast object to special class if it's possible.
     * 
     * @param <T>
     * @param object
     * @param asClass
     * @return
     */
    public static <T> Object getAsClass(Object object, Class<T> asClass) {
        // immediate conversations
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            Date value = (Date) object;
            return value.getTime();
        }
        if (asClass == null) {
            return object;
        }
        if (object.getClass().isAssignableFrom(asClass)) {
            return object;
        }
        if (asClass.isAssignableFrom(String.class)) {
            return object.toString();
        }

        // convert to Boolean class
        if (asClass.isAssignableFrom(Boolean.class)
                || asClass.isAssignableFrom(boolean.class)) {
            if (object instanceof String) {
                String stringObject = (String) object;
                if (stringObject.equalsIgnoreCase("true")
                        || stringObject.equalsIgnoreCase("1")) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }
            if (object instanceof Number) {
                Number value = (Number) object;
                if (value.intValue() == 0) {
                    return Boolean.FALSE;
                } else {
                    return Boolean.TRUE;
                }
            }
        }

        // convert to Date class
        if (asClass.isAssignableFrom(Date.class) && (object instanceof Long)) {
            Long longObject = (Long) object;
            return new Date(longObject);
        }

        // cast primitive types
        if ((asClass.isAssignableFrom(int.class) && object instanceof Integer)
                || (asClass.isAssignableFrom(byte.class) && object instanceof Byte)
                || (asClass.isAssignableFrom(short.class) && object instanceof Short)
                || (asClass.isAssignableFrom(long.class) && object instanceof Long)
                || (asClass.isAssignableFrom(double.class) && object instanceof Double)
                || (asClass.isAssignableFrom(float.class) && object instanceof Float)
                || (asClass.isAssignableFrom(float.class) && object instanceof Float)
                || (asClass.isAssignableFrom(boolean.class) && object instanceof Boolean)) {
            return object;
        }

        // if can't convert types.
        throw new ClassCastException(String.format(
                "Cant cast object=\"%s\" with class=\"%s\" to class=\"%s\"",
                object, object.getClass(), asClass));

    }

    private ClassCaster() {
        super();
    }

}
