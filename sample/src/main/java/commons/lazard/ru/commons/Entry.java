package commons.lazard.ru.commons;


import java.util.Date;

import ru.lazard.commons.mapping.MappedField;
import ru.lazard.commons.sql.DatabaseField;
import ru.lazard.commons.sql.DatabaseTable;

@DatabaseTable(name = "myitem")
public class Entry {
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
