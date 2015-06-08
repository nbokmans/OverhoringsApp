package ica.han.oose.project.overhoorapp.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

@Config(manifest = "app/src/main/AndroidManifest.xml", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class PersistenceAdapterTest {
    PersistenceAdapter pa;

    @Before
    public void setUp() {
        pa = mock(PersistenceAdapter.class);
    }

    @Test
    public void testCheckServerConnection() {
        assertFalse(pa.checkServerConnection());
    }
}