package commons.lazard.ru.commons;

import android.content.Context;

import ru.lazard.commons.sql.DefaultOpenHelper;

/**
 * Created by Egor on 17.02.2016.
 */
public class DatabaseHelper extends DefaultOpenHelper {

    private static final String NAME = "sample";

    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, VERSION, Entry.class);
    }

}