/**************************************************************************
 * TODO copyright
 *
 * $Id: DataSourceBundle.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/DataSourceBundle.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.util.Iterator;
import java.util.Set;

import android.os.Bundle;

/**
 * Wrapper class for convert Bundle to IDataSource.
 */
/* package */class DataSourceBundle implements IDataSource {
    private Bundle bundle;

    private String currentKey = null;

    private Iterator<String> iterator = null;

    public DataSourceBundle(Bundle bundle) {
        super();
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle should not be null");
        }

        this.bundle = bundle;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#getFieldName()
     */
    @Override
    public String getFieldName() {
        return currentKey;
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
        Object object = bundle.get(currentKey);
        Object objectAsClass = ClassCaster.getAsClass(object, asClass);
        return objectAsClass;
    }

    /**
     * Move DataSource to first value.
     */
    public void moveToFirst() {
        Set<String> keys = bundle.keySet();
        iterator = keys.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enterra.android.lib.binding.IDataSource#next()
     */
    @Override
    public boolean next() {
        if (iterator == null) {
            moveToFirst();
        }
        if (iterator.hasNext()) {
            currentKey = iterator.next();
            return true;
        }
        return false;
    }

}
