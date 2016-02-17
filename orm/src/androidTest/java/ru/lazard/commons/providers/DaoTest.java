/**************************************************************************
 * TODO copyright
 *
 * $Id: DaoTest.java 77 2012-06-20 03:33:28Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/providers/DaoTest.java $
 **************************************************************************/

package ru.lazard.commons.providers;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import ru.lazard.commons.dao.Dao;
import ru.lazard.commons.dao.DaoManager;

/**
 * 
 */

public class DaoTest extends AndroidTestCase {

    private Dao<TableForTest> daoTable;

    private DaoClassInstance daoTable2;

    private String providerAuthority = "ru.lazard.commons.client";

    /**
     * Test custom Dao create.
     */
    public void testCustomDao() {

        assertEquals(daoTable2.customMetod(), 0);
    }

    /**
     * Test method for {@link Dao#delete(String, String[])}
     */
    public void testDaoDeleteByClass() {
        // execute
        daoTable.delete(null, null);

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 0);
    }

    /**
     * Test method for {@link Dao#deleteList(List)}
     */
    public void testDaoDeleteList() {
        // set up
        daoTable.delete(null, null);
        daoTable.insert(new TableForTest());
        daoTable.insert(new TableForTest());
        daoTable.insert(new TableForTest());
        List<TableForTest> listDeleted = daoTable.query(null, new String[] {},
                null);

        // execute
        try {
            try {
                daoTable.deleteList(listDeleted);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 0);
    }

    /**
     * Test method for {@link Dao#delete(Object)}
     */
    public void testDaoDeleteObject() {
        // set up
        daoTable.delete(null, null);
        TableForTest object = new TableForTest();
        daoTable.insert(object);

        // execute
        try {
            daoTable.delete(object);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 0);
    }

    /**
     * Test method for {@link Dao#insertList(List)}
     */
    public void testDaoInsertList() {
        // setup
        daoTable.delete(null, null);
        int insertCount = 10;
        List<TableForTest> insertList = new ArrayList<TableForTest>();
        for (int i = 0; i < insertCount; i++) {
            insertList.add(new TableForTest());
        }

        // execute
        try {
            daoTable.insertList(insertList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), insertCount);
    }

    /**
     * Test method for {@link Dao#insert(Object)}
     */
    public void testDaoInsertObject() {
        // setup
        daoTable.delete(null, null);

        // execute
        daoTable.insert(new TableForTest());

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 1);
    }

    /**
     * Test method for
     * {@link ru.lazard.commons.dao.Dao#query(String, String[], String)}
     */
    public void testDaoQuery() {
        // set up
        daoTable.delete(null, null);
        daoTable.insert(new TableForTest());
        daoTable.insert(new TableForTest());

        // execute
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);

        // test
        assertEquals(list.size(), 2);
    }

    /**
     * Test method for
     * {@link ru.lazard.commons.dao.Dao#updateList(List)}
     */
    public void testDaoUpdateList() {
        // set up
        daoTable.delete(null, null);
        TableForTest object = new TableForTest();
        daoTable.insert(object);
        daoTable.insert(object);

        String newText = "text2";
        List<TableForTest> listUpdate = daoTable.query(null, new String[] {},
                null);
        for (TableForTest myTableClass : listUpdate) {
            myTableClass.setText(newText);
        }
        // execute
        try {
            daoTable.updateList(listUpdate);
        } catch (RemoteException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 2);
        for (TableForTest myTableClass : list) {
            assertEquals(myTableClass.getText(), newText);
        }

    }

    /**
     * Test method for
     * {@link ru.lazard.commons.dao.Dao#update(Object)}
     */
    public void testDaoUpdateObject() {
        // set up
        daoTable.delete(null, null);
        TableForTest object = new TableForTest();
        daoTable.insert(object);
        String newText = "text2";
        object.setText(newText);

        // execute
        try {
            daoTable.update(object);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        // test
        List<TableForTest> list = daoTable.query(null, new String[] {}, null);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getText(), newText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context context = getContext();
        DaoManager manager = new DaoManager(context, providerAuthority);
        daoTable = manager.getDao(TableForTest.class);
        daoTable2 = (DaoClassInstance) manager.getDao(TableForTest2.class);

    }
}
