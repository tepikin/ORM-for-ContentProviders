package ru.lazard.commons.providers;

import java.util.Date;

import ru.lazard.commons.mapping.MappedField;
import ru.lazard.commons.sql.DatabaseField;
import ru.lazard.commons.sql.DatabaseTable;

/**************************************************************************
 * TODO copyright
 *
 * $Id: TableForTest2.java 77 2012-06-20 03:33:28Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/TableForTest2.java $
 **************************************************************************/

/**
 * Class for store in database.
 */
@DatabaseTable(name = "myitem2", dao = DaoClassInstance.class)
public class TableForTest2 {
    @MappedField(name = "date_1")
    @DatabaseField(name = "date_1", isNotNull = true)
    private Date date = new Date();

    @MappedField(isMappedOut = false)
    @DatabaseField(isPrimaryKey = true)
    private int id = 1;

    @MappedField
    @DatabaseField(isUnique = true)
    private String text = "text";

}