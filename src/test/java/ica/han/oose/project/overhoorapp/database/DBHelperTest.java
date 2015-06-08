package ica.han.oose.project.overhoorapp.database;

import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

@Config(manifest = "app/src/main/AndroidManifest.xml", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DBHelperTest extends Activity {
    private DBHelper db;

    @Before
    public void setUp() {
        db = mock(DBHelper.class);

        db.open();
    }

    @After
    public void after() {
        db.close();
    }

    @Test
    public void testDropDatabase() {
        db.dropDatabaseTables();
    }

    @Test
    public void testInsertRowAccount() {
        assertNotEquals(db.insertRowAccount("TEST", "PASSword29", "1"), (long) -1);
    }

    @Test
    public void testInsertRowTopic() {
        assertNotEquals(db.insertRowTopic("2e wereldoorlog", "1", null, "eindigde in 1945", 1), -1l);
    }

    @Test
    public void testInsertRowQuestion() {
        testInsertRowType();
        assertNotEquals(db.insertRowQuestion("Hoelaat is het?", "1", "Openvraag", "1"), -1l);
    }

    @Test
    public void testInsertRowIncorrectAnswer() {
        db.insertRowIncorrectAnswer("Het is 9 uur","1");
    }

    @Test
    public void testInsertRowAnswerValue() {
        db.insertRowAnswerValue("Het is 9 uur", "1");
    }

    @Test
    public void testInsertRowCorrectAnswer() {
        assertNotEquals(db.insertRowCorrectAnswer("Het is 8 uur", "1"), -1l);
    }

    @Test
    public void testInsertRowType() {
        assertNotEquals(db.insertRowType("Openvraag"), -1l);
    }

    @Test
    public void testInsertRowTag() {
        assertNotEquals(db.insertRowTag("Geschiedenis"), -1l);
    }

}