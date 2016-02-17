package commons.lazard.ru.commons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import ru.lazard.commons.dao.Dao;
import ru.lazard.commons.dao.DaoManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoManager manager = new DaoManager(this, "ru.lazard.commons.client");
        Dao<Entry> daoTable = manager.getDao(Entry.class);

        Entry entry = new Entry();

        daoTable.insert(entry);

        List<Entry> result = daoTable.query(null, null, null);


        for (Entry entry1 : result) {
            Log.e("tag",""+entry1+"  "+entry1.getText());
        }
    }
}
