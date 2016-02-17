package ru.lazard.commons.providers;

import java.util.Date;

import ru.lazard.commons.mapping.MappedField;
import ru.lazard.commons.sql.DatabaseField;
import ru.lazard.commons.sql.DatabaseTable;

/**************************************************************************
 * TODO copyright
 *
 * $Id: TableForTest.java 66 2012-05-17 10:55:52Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/TableForTest.java $
 **************************************************************************/

/**
 * Class for store in database.
 */
@DatabaseTable(name = "myitem")
public class TableForTest {
    @MappedField(name = "date_1")
    @DatabaseField(name = "date_1", isNotNull = true)
    private Date date = new Date();

    @MappedField(isMappedOut = false)
    @DatabaseField(isPrimaryKey = true)
    private int id = 1;

    @MappedField
    @DatabaseField
    private String text = "text";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
