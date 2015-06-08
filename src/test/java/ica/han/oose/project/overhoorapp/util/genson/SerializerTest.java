package ica.han.oose.project.overhoorapp.util.genson;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SerializerTest {

    private Person person;
    private static final String EXPECTED_JSON = "{\"age\":22,\"firstName\":\"Test\",\"lastName\":\"Persoon\"}";

    @Before
    public void setUp() {
        person = new Person();
        person.setAge(22);
        person.setFirstName("Test");
        person.setLastName("Persoon");
    }

    @Test
    public void testSerialize() throws Exception {
        Assert.assertEquals(EXPECTED_JSON, Serializer.serialize(person));
    }


}