# ORM-for-ContentProviders

Можно использовать как :
 - ORM для Sqlite.
 - обертка над ContentProvider
 - Object Mapping
 - итд
 
 
 Детального описания не будет, все можно посмотреть в UnitTests.
 
## Пример использования:
Описываем Entity 
```java
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
```


 Описываем DatabaseHelper 
```java
public class DatabaseHelper extends DefaultOpenHelper {

    private static final String NAME = null; // for store database in-memory.

    private static final int VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, NAME, VERSION, TableForTest.class, TableForTest2.class);
    }

}
```

 Содаем объект DAO:

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
        
