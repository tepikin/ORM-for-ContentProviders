/**************************************************************************
 * TODO copyright
 *
 * $Id: DaoManager.java 77 2012-06-20 03:33:28Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/dao/DaoManager.java $
 **************************************************************************/

package com.enterra.android.lib.commons.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.text.TextUtils;

import com.enterra.android.lib.commons.sql.DatabaseTable;

/**
 * Class for create Dao's.
 */
public class DaoManager {
    private Context context;

    private String providerAuthority;

    /**
     * @param context
     * @param providerAuthority
     */
    public DaoManager(Context context, String providerAuthority) {
        super();
        if (TextUtils.isEmpty(providerAuthority)) {
            throw new IllegalArgumentException(
                    "\"providerAuthority\" should not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("\"context\" should not be null");
        }
        this.context = context;
        this.providerAuthority = providerAuthority;
    }

    public <T> Dao<T> getDao(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("\"clazz\" should not be null");
        }
        if (!clazz.isAnnotationPresent(DatabaseTable.class)) {
            throw new IllegalArgumentException(
                    "\"clazz\" should be marked by annotation \"DatabaseTable\"");
        }

        Dao<T> dao = null;
        DatabaseTable annotation = clazz.getAnnotation(DatabaseTable.class);
        Class<? extends Dao> daoClass = annotation.dao();

        String name = daoClass.getName();
        String name2 = BaseDao.class.getName();
        if (name.equals(name2)) {
            return new BaseDao<T>(providerAuthority, context, clazz);
        }

        Constructor<? extends Dao> constructior;
        try {
            constructior = daoClass.getConstructor(String.class, Context.class);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(
                    "\"clazz\" should haw public constructor Constructor(String providerAuthority,Context context)",
                    e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "\"clazz\" should haw public constructor Constructor(String providerAuthority,Context context)",
                    e);
        }

        if (constructior != null) {
            try {
                dao = constructior.newInstance(providerAuthority, context);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (InstantiationException e) {
                throw new IllegalArgumentException(e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return dao;
    }
}
