package commons.lazard.ru.commons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.lazard.commons.providers.AttachableContentProvider;
import ru.lazard.commons.providers.AttachableContentProvidersRouter;
import ru.lazard.commons.providers.DbContentProvider;

/**
 * Created by Egor on 17.02.2016.
 */
public class SampleContentProvider extends AttachableContentProvidersRouter{

         private DatabaseHelper dbHelper;

         protected List<AttachableContentProvider> createProviders(Context context) {
                dbHelper = new DatabaseHelper(context);
                List<AttachableContentProvider> providers = new ArrayList<AttachableContentProvider>();

                providers.add(new DbContentProvider(dbHelper, context)); //Provider for all DB tables.


                return providers;
            }
}
