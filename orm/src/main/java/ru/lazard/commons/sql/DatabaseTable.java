/**************************************************************************
 * TODO copyright
 *
 * $Id: DatabaseTable.java 77 2012-06-20 03:33:28Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/sql/DatabaseTable.java $
 **************************************************************************/

package ru.lazard.commons.sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.lazard.commons.dao.BaseDao;
import ru.lazard.commons.dao.Dao;

/**
 * Annotation for mark class associated with database table.<br>
 * Equivalent of Entity.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseTable {

    /**
     * Dao class associated with table.
     * Default BaseDao.
     * 
     * @return
     */
    Class<? extends Dao> dao() default BaseDao.class;

    /**
     * Is create table only if it's not exist.
     * Default true.
     * 
     * @return
     */
    boolean isNotExist() default true;

    /**
     * Is temporary table.
     * Default false.
     * 
     * @return
     */
    boolean isTemporary() default false;

    /**
     * Name of table.
     * Default name of class.
     * 
     * @return
     */
    String name() default "";
}
