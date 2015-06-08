package ica.han.oose.project.overhoorapp.util.genson;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DeserializerTest {

    private String json;
    private Person person;

    @Before
    public void setUp() throws Exception {
        json = "{\"age\":22,\"firstName\":\"Test\",\"lastName\":\"Persoon\"}";
        person = new Person();
        person.setAge(22);
        person.setFirstName("Test");
        person.setLastName("Persoon");
    }

    @Test
    public void testDeserialize() throws Exception {
        Assert.assertEquals(person, Deserializer.deserialize(json, Person.class));
    }
}