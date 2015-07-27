/**************************************************************************
 * TODO copyright
 *
 * $Id: BundleMapper.java 71 2012-05-23 01:28:27Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/BundleMapper.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Class for build bundle by DataSource.
 */
/* package */class BundleMapper {

    /**
     * Map of classes to put methods types.
     */
    private Map<String, Class<?>> methodClass = new HashMap<String, Class<?>>();

    /**
     * Map of classes to put methods names.
     */
    private Map<String, String> methodNames = new HashMap<String, String>();

    /**
     * Constructor.
     */
    public BundleMapper() {
        super();

        initMethodsMap();
    }

    /**
     * Generate new bundle and bind data to it.
     * 
     * @param dataSource
     * @return
     */
    public Bundle createBundle(IDataSource dataSource) {
        // test inner parameters
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource should not be null");
        }

        // start bind values
        Bundle bundle = new Bundle();
        put(dataSource, bundle);
        return bundle;
    }

    /**
     * Put DataSource values to existed Bundle object.
     * 
     * @param dataSource
     * @param bundle
     */
    public void put(IDataSource dataSource, Bundle bundle) {
        // test inner parameters
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource should not be null");
        }
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle should not be null");
        }
        // start bind values
        while (dataSource.next()) {
            String key = dataSource.getFieldName();
            Object value = dataSource.getValue(null);
            put(key, value, bundle);
        }

    }

    /**
     * Put abstract object to bundle.<br>
     * Only standart bundle objects can be puted to bundle.
     * 
     * @param key
     * @param value
     *            Object for set - Supported types:
     *            <ul>
     *            <li><code>String</code></li>
     *            <li><code>Boolean</code></li>
     *            <li><code>boolean[]</code></li>
     *            <li><code>Byte</code></li>
     *            <li><code>byte[]</code></li>
     *            <li><code>Character</code></li>
     *            <li><code>char[]</code></li>
     *            <li><code>CharSequence</code></li>
     *            <li><code>Double</code></li>
     *            <li><code>double[]</code></li>
     *            <li><code>Float</code></li>
     *            <li><code>float[]</code></li>
     *            <li><code>Integer</code></li>
     *            <li><code>int[]</code></li>
     *            <li><code>Short</code></li>
     *            <li><code>short[]</code></li>
     *            <li><code>String</code></li>
     *            <li><code>String[]</code></li>
     *            <li><code>ArrayList&ltInteger></code></li>
     *            <li><code>ArrayList&ltString></code></li>
     *            </ul>
     * @param bundle
     */
    @SuppressWarnings("unchecked")
    public void put(String key, Object value, Bundle bundle) {
        // Empty fields must not be mapped.
        if (value == null) {
            return;
        }
        if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
            return;
        }
        if (value instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) value);
            return;
        }

        // invoke put method
        try {
            String className = value.getClass().getName();
            String methodName = methodNames.get(className);
            Method method = Bundle.class.getMethod(methodName, String.class,
                    methodClass.get(className));
            method.invoke(bundle, key, value);
            return;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // if field not mapped
        throw new IllegalArgumentException(
                String.format(
                        "Can't map to Bundle types : key=\"%s\" object=\"%s\" class=\"%s\"",
                        key, value.toString(), value.getClass().toString()));
    }

    /**
     * Initialize invocation methods and class cast types.
     */
    private void initMethodsMap() {
        // put invocation methods.
        methodNames.put(String.class.getName(), "putString");
        methodNames.put(Boolean.class.getName(), "putBoolean");
        methodNames.put(boolean.class.getName(), "putBoolean");
        methodNames.put(boolean[].class.getName(), "putBooleanArray");
        methodNames.put(Boolean[].class.getName(), "putBooleanArray");
        methodNames.put(Byte.class.getName(), "putByte");
        methodNames.put(byte.class.getName(), "putByte");
        methodNames.put(byte[].class.getName(), "putByteArray");
        methodNames.put(Byte[].class.getName(), "putByteArray");
        methodNames.put(Character.class.getName(), "putChar");
        methodNames.put(char.class.getName(), "putChar");
        methodNames.put(char[].class.getName(), "putCharArray");
        methodNames.put(Character[].class.getName(), "putCharArray");
        methodNames.put(CharSequence.class.getName(), "putCharSequence");
        methodNames.put(Double.class.getName(), "putDouble");
        methodNames.put(double.class.getName(), "putDouble");
        methodNames.put(double[].class.getName(), "putDoubleArray");
        methodNames.put(Double[].class.getName(), "putDoubleArray");
        methodNames.put(Float.class.getName(), "putFloat");
        methodNames.put(float.class.getName(), "putFloat");
        methodNames.put(float[].class.getName(), "putFloatArray");
        methodNames.put(Float[].class.getName(), "putFloatArray");
        methodNames.put(Integer.class.getName(), "putInt");
        methodNames.put(int.class.getName(), "putInt");
        methodNames.put(int[].class.getName(), "putIntArray");
        methodNames.put(Integer[].class.getName(), "putIntArray");
        methodNames.put(Short.class.getName(), "putShort");
        methodNames.put(short.class.getName(), "putShort");
        methodNames.put(short[].class.getName(), "putShortArray");
        methodNames.put(Short[].class.getName(), "putShortArray");
        methodNames.put(Long.class.getName(), "putLong");
        methodNames.put(long.class.getName(), "putLong");
        methodNames.put(long[].class.getName(), "putLongArray");
        methodNames.put(Long[].class.getName(), "putLongArray");
        methodNames.put(String.class.getName(), "putString");
        methodNames.put(String[].class.getName(), "putStringArrayList");

        // put classes cast.
        methodClass.put(String.class.getName(), String.class);
        methodClass.put(Boolean.class.getName(), boolean.class);
        methodClass.put(boolean.class.getName(), boolean.class);
        methodClass.put(boolean[].class.getName(), boolean[].class);
        methodClass.put(Boolean[].class.getName(), boolean[].class);
        methodClass.put(Byte.class.getName(), byte.class);
        methodClass.put(byte.class.getName(), byte.class);
        methodClass.put(byte[].class.getName(), byte[].class);
        methodClass.put(Byte[].class.getName(), byte[].class);
        methodClass.put(Character.class.getName(), char.class);
        methodClass.put(char.class.getName(), char.class);
        methodClass.put(char[].class.getName(), char[].class);
        methodClass.put(Character[].class.getName(), char[].class);
        methodClass.put(CharSequence.class.getName(), CharSequence.class);
        methodClass.put(Double.class.getName(), double.class);
        methodClass.put(double.class.getName(), double.class);
        methodClass.put(double[].class.getName(), double[].class);
        methodClass.put(Double[].class.getName(), double[].class);
        methodClass.put(Float.class.getName(), float.class);
        methodClass.put(float.class.getName(), float.class);
        methodClass.put(float[].class.getName(), float[].class);
        methodClass.put(Float[].class.getName(), float[].class);
        methodClass.put(Integer.class.getName(), int.class);
        methodClass.put(int.class.getName(), int.class);
        methodClass.put(int[].class.getName(), int[].class);
        methodClass.put(Integer[].class.getName(), int[].class);
        methodClass.put(Short.class.getName(), short.class);
        methodClass.put(short.class.getName(), short.class);
        methodClass.put(short[].class.getName(), short[].class);
        methodClass.put(Short[].class.getName(), short[].class);
        methodClass.put(Long.class.getName(), long.class);
        methodClass.put(long.class.getName(), long.class);
        methodClass.put(long[].class.getName(), long[].class);
        methodClass.put(Long[].class.getName(), long[].class);
        methodClass.put(String.class.getName(), String.class);
        methodClass.put(String[].class.getName(), String[].class);
    }
}
