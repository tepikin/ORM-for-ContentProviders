/**************************************************************************
 * TODO copyright
 *
 * $Id: IDataSource.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/IDataSource.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

/**
 * Interface for object provide data.
 */
/* package */interface IDataSource {
    /**
     * Get name (string key) for current value.
     * 
     * @return
     */
    String getFieldName();

    /**
     * Get current value as specified class.
     * 
     * @return
     */
    Object getValue(Class<?> asClass);

    /**
     * Move to next value.
     * 
     * @return true if more values exist, else false.
     */
    boolean next();
}
