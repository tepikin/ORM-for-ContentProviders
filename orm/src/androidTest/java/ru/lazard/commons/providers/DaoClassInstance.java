/**************************************************************************
 * TODO copyright
 *
 * $Id: DaoClassInstance.java 77 2012-06-20 03:33:28Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/DaoClassInstance.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.content.Context;

import ru.lazard.commons.dao.BaseDao;

/**
 * Dao class for test DaoManager.
 */
public class DaoClassInstance extends BaseDao<TableForTest2> {

    /**
     * @param providerAuthority
     * @param context
     */
    public DaoClassInstance(String providerAuthority, Context context) {
        super(providerAuthority, context, TableForTest2.class);
    }

    public int customMetod() {
        return 0;
    }

}
