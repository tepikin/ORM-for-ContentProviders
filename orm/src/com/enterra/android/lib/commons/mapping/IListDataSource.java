/**************************************************************************
 * TODO copyright
 *
 * $Id: IListDataSource.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/IListDataSource.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

/**
 * Interface for lists provide data.
 */
/* package */interface IListDataSource extends IDataSource {
    /**
     * Move data source to next list of values (for example next row of cursor).
     * 
     * @return true if move to next was success, false if no more next rows
     *         (cursor at last row).
     */
    boolean nextRow();
}
