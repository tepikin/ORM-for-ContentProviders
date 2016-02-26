# ORM-for-ContentProviders

Можно использовать как :
 - ORM для Sqlite.
 - обертка над ContentProvider
 - Object Mapping
 - итд
 
 
 Детального описания не будет, все можно посмотреть в UnitTests ( да и комментариев в библиотеке хватает ).
 
## Пример использования:
 Описываем Entity 
```java
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
```


 Описываем DatabaseHelper 
```java
public class DatabaseHelper extends DefaultOpenHelper {

    private static final String NAME = "db_name";

    private static final int VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, NAME, VERSION, Entry.class, Entry2.class);
    }
}
```


 Добавляем провайдер ContentProvider:
```java
public class SampleContentProvider extends AttachableContentProvidersRouter {
    
    private DatabaseHelper dbHelper;
    
    protected List<AttachableContentProvider> createProviders(Context context) {
        dbHelper = new DatabaseHelper(context);
        List<AttachableContentProvider> providers = new ArrayList<AttachableContentProvider>();
        
        providers.add(new DbContentProvider(dbHelper, context)); //Provider for all DB tables.
        
        return providers;
    }
}
```


И описываем его в manifest:
``` xml
<provider
    android:name=".SampleContentProvider"
    android:authorities="ru.lazard.commons.client"
    android:enabled="true"
    android:exported="true">
</provider>
```




---

Теперь там где нужно использовать базу или провайдер создаем DAO и используем.

 Создаем объект DAO:
```java
DaoManager manager = new DaoManager(context, providerAuthority);
Dao<TableForTest> daoTable = manager.getDao(TableForTest.class);
```

 Все готово к работе :
```java
daoTable.delete(null, null);
daoTable.insert(new TableForTest());
daoTable.query(null, new String[] {}, null);
```
        
## Как добавить в свой проект (Gradle)
To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file <br \>
Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
**Step 2.** Add the dependency
```gradle
dependencies {
    compile 'com.github.tepikin:ORM-for-ContentProviders:v0.1'
}
```
