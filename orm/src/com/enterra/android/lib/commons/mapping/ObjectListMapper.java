/**************************************************************************
 * TODO copyright
 *
 * $Id: ObjectListMapper.java 54 2012-04-26 02:54:31Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/ObjectListMapper.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for bind List of objects from IListDataSource.
 */
/* package */class ObjectListMapper extends ObjectMapper {

    /**
     * Generate List of objects with binded it fields by IListDataSource.
     * 
     * @param dataSource
     * @param bundle
     */
    public <T> List<T> createObjectList(IListDataSource listDataSource,
            Class<T> clazz) {
        if (listDataSource == null) {
            throw new IllegalArgumentException(
                    "ListDataSource should not be null");
        }
        List<T> list = new ArrayList<T>();
        while (listDataSource.nextRow()) {
            list.add(createObject(listDataSource, clazz));
        }
        return list;
    }

}
