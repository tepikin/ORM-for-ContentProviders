/**************************************************************************
 * TODO copyright
 *
 * $Id: MappedField.java 55 2012-04-26 04:46:58Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/mapping/MappedField.java $
 **************************************************************************/

package com.enterra.android.lib.commons.mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for mark mapped fields.<br>
 * All mapped fields must be marked by this annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedField {
    /**
     * Is field must mapped in from other objects (as mapped object, on
     * injection to this object).
     * Default true.
     * 
     * @return
     */
    boolean isMappedIn() default true;

    /**
     * Is field must mapped out to other objects (as DataSource).
     * Default true.
     * 
     * @return
     */
    boolean isMappedOut() default true;

    /**
     * Name for mapped field.
     * Default field name.
     * 
     * @return
     */
    String name() default "";
}
