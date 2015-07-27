/**************************************************************************
 * TODO copyright
 *
 * $Id: DatabaseField.java 59 2012-04-26 09:59:06Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/sql/DatabaseField.java $
 **************************************************************************/

package com.enterra.android.lib.commons.sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for mark class associated with database table.<br>
 * Equivalent of Entity.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseField {
    /**
     * Is need "NOT NULL" constraint for this table column.<br>
     * Default false.
     * 
     * @return - true if constraint need, else false.
     */
    boolean isNotNull() default false;

    /**
     * Is column are "PRIMARY KEY".<br>
     * Default false.
     * 
     * @return - true if "PRIMARY KEY", else false.
     */
    boolean isPrimaryKey() default false;

    /**
     * Is need "UNIQUE" constraint for this table column.<br>
     * Default false.
     * 
     * @return - true if constraint need, else false.
     */
    boolean isUnique() default false;

    /**
     * Name of column.
     * Default - name of field.
     * 
     * @return
     */
    String name() default "";
}
