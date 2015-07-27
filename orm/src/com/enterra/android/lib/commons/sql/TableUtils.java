/**************************************************************************
 * TODO copyright
 *
 * $Id: TableUtils.java 79 2012-06-22 02:31:14Z tretyakov $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommons/src/com/enterra/android/lib/commons/sql/TableUtils.java $
 **************************************************************************/

package com.enterra.android.lib.commons.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;

/**
 * Utilities for DDL commands.
 */
public class TableUtils {
    /**
     * Create table by class. Class must be marked by {@link DatabaseTable}
     * annotation. Class fields must be marked by {@link DatabaseField}
     * annotation.
     * 
     * @param db
     * @param mappedClass
     */
    public static void createTable(SQLiteDatabase db, Class<?> mappedClass) {
        if (db == null) {
            throw new IllegalArgumentException("db should not be null");
        }
        if (mappedClass == null) {
            throw new IllegalArgumentException("mappedClass should not be null");
        }
        db.execSQL(createTableQuery(mappedClass));
    }

    /**
     * Get DDL command for create table by class. Class must be marked by
     * {@link DatabaseTable} annotation. Class fields must be marked by
     * {@link DatabaseField} annotation.
     * 
     * @param mappedClass
     */
    public static String createTableQuery(Class<?> mappedClass) {
        if (mappedClass == null) {
            throw new IllegalArgumentException("mappedClass should not be null");
        }
        DatabaseTable classAnnotation = mappedClass.getAnnotation(DatabaseTable.class);
        if (classAnnotation == null) {
            throw new IllegalArgumentException(
                    "Class must be marked by DatabaseTable annotation. Class is " + mappedClass);
        }

        String tableName = classAnnotation.name();
        if (tableName == null || tableName.length() <= 0) {
            tableName = mappedClass.getSimpleName();
        }

        ArrayList<Field> mappedFields = new ArrayList<Field>();
        ArrayList<DatabaseField> fieldsAnnotations = new ArrayList<DatabaseField>();
        Field[] fields = mappedClass.getDeclaredFields();
        for (Field field : fields) {
            DatabaseField fieldAnnotation = field.getAnnotation(DatabaseField.class);
            if (fieldAnnotation != null) {
                mappedFields.add(field);
                fieldsAnnotations.add(fieldAnnotation);
            }
        }
        int columnsCount = mappedFields.size();
        if (columnsCount <= 0) {
            throw new IllegalArgumentException(
                    "Class fields mut be marked by DatabaseField anatation");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE ");
        if (classAnnotation.isTemporary()) {
            sql.append("TEMPORARY ");
        }
        sql.append("TABLE ");
        if (classAnnotation.isNotExist()) {
            sql.append("IF NOT EXISTS ");
        }

        sql.append(tableName);
        sql.append(" ");

        sql.append("( ");

        for (int i = 0; i < columnsCount; i++) {
            Field field = mappedFields.get(i);
            DatabaseField fieldAnnotation = fieldsAnnotations.get(i);

            String columnName = fieldAnnotation.name();
            if (columnName == null || columnName.length() <= 0) {
                columnName = field.getName();
            }

            String columnType = getType(field.getType());

            sql.append(columnName);
            sql.append(" ");
            sql.append(columnType);
            sql.append(" ");

            if (fieldAnnotation.isPrimaryKey()) {
                sql.append("PRIMARY KEY ");
            } else {
                if (fieldAnnotation.isUnique()) {
                    sql.append("UNIQUE ");
                }
                if (fieldAnnotation.isNotNull()) {
                    sql.append("NOT NULL ");
                }
            }

            if (i != columnsCount - 1) {
                sql.append(", ");
            }
        }

        sql.append(") ");
        return sql.toString();
    }

    /**
     * Delete table from db by class associated with class.Class must be marked
     * by {@link DatabaseTable} annotation.
     * 
     * @param db
     * @param mappedClass
     */
    public static void deleteTable(SQLiteDatabase db, Class<?> mappedClass) {
        if (db == null) {
            throw new IllegalArgumentException("db should not be null");
        }

        String sql = deleteTableQuery(mappedClass);
        db.execSQL(sql);
    }

    /**
     * Delete table from db by class associated with class.Class must be marked
     * by {@link DatabaseTable} annotation.
     * 
     * @param db
     * @param mappedClass
     */
    public static String deleteTableQuery(Class<?> mappedClass) {
        if (mappedClass == null) {
            throw new IllegalArgumentException("mappedClass should not be null");
        }

        String tableName = getTableName(mappedClass);

        return "DROP TABLE IF EXISTS " + tableName;
    }

    /**
     * Get name of table by class marked {@link DatabaseTable} annotation.
     * 
     * @param mappedClass
     * @return - name of class.
     */
    public static String getTableName(Class<?> mappedClass) {
        if (mappedClass == null) {
            throw new NullPointerException("\"mappedClass\" should not be null");
        }
        DatabaseTable classAnnotation = mappedClass.getAnnotation(DatabaseTable.class);
        if (classAnnotation == null) {
            throw new IllegalArgumentException("Class mut be marked by DatabaseTable anatation");
        }

        String tableName = classAnnotation.name();
        if (tableName == null || tableName.length() <= 0) {
            tableName = mappedClass.getSimpleName();
        }
        return tableName;
    }

    /**
     * Get name of column type by field class.
     * 
     * @param mappedClass
     * @return - name of class.
     */
    private static String getType(Class<?> inClass) {
        if (inClass.isAssignableFrom(Integer.class) || inClass.isAssignableFrom(int.class)
                || inClass.isAssignableFrom(long.class) || inClass.isAssignableFrom(Long.class)
                || inClass.isAssignableFrom(Short.class) || inClass.isAssignableFrom(short.class)
                || inClass.isAssignableFrom(byte.class) || inClass.isAssignableFrom(Byte.class)
                || inClass.isAssignableFrom(Date.class) || inClass.isAssignableFrom(Boolean.class)
                || inClass.isAssignableFrom(boolean.class)) {
            return "INTEGER";
        }
        if (inClass.isAssignableFrom(Double.class) || inClass.isAssignableFrom(double.class)
                || inClass.isAssignableFrom(Float.class) || inClass.isAssignableFrom(float.class)) {
            return "REAL";
        }
        if (inClass.isAssignableFrom(String.class) || inClass.isAssignableFrom(CharSequence.class)) {
            return "TEXT";
        }
        if (inClass.isAssignableFrom(Byte[].class) || inClass.isAssignableFrom(byte[].class)) {
            return "BLOB";
        }
        return null;
    }
}
