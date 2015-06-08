package ica.han.oose.project.overhoorapp.util.jsonbuilder;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TopicBuilderTest {

    private TopicBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new TopicBuilder();
    }

    @Test
    public void testSetObjectID() throws Exception {
        builder.setObjectID("a1b2c3d4e5f6g7h8i9j0a1b2c3d4e5f6");
        Assert.assertEquals("a1b2c3d4e5f6g7h8i9j0a1b2c3d4e5f6", builder.getTopic().get_id().get$oid());
    }

    @Test
    public void testSetVersion() throws Exception {
        builder.setVersion(1);
        Assert.assertEquals(1, builder.getTopic().getVersion());
    }

    @Test
    public void testSetOwner() throws Exception {
        builder.setOwner(1);
        Assert.assertEquals("1", builder.getTopic().getOwner().getUid());
    }

    @Test
    public void testSetTitle() throws Exception {
        builder.setTitle("Test title");
        Assert.assertEquals("Test title", builder.getTopic().getTitle());
    }

    @Test
    public void testSetContent() throws Exception {
        builder.setContent("<p>This is a paragraph.</p>");
        Assert.assertEquals("<p>This is a paragraph.</p>", builder.getTopic().getContent());
    }

    @Test
    public void testSetIsRoot() throws Exception {
        builder.setIsRoot(true);
        Assert.assertTrue(builder.getTopic().getIsRoot());
    }

    @Test
    public void testAddContributor() throws Exception {
        builder.addContributor(2);
        Assert.assertTrue(builder.getTopic().getContributors().length == 1);
    }

    @Test
    public void testAddCorrectContributor() throws Exception {
        builder.addContributor(2);
        Assert.assertEquals("2", builder.getTopic().getContributors()[0].getUid());
    }

    @Test
    public void testAddTag() throws Exception {
        builder.addTag("Test tag");
        Assert.assertTrue(builder.getTopic().getTags().length == 1);
    }

    @Test
    public void testAddCorrectTag() throws Exception {
        builder.addTag("Test tag");
        Assert.assertEquals("Test tag", builder.getTopic().getTags()[0].getText());
    }

    @Test
    public void testToJson() throws Exception {
        builder.setIsRoot(true);
        builder.setObjectID("12345678901234567890123456789012");
        builder.setOwner(1);
        builder.setContent("<p>Test paragraph</p>");
        builder.setTitle("Title");
        Assert.assertEquals("{\"_id\":{\"$oid\":\"12345678901234567890123456789012\"},\"content\":\"<p>Test paragraph</p>\",\"contributors\":[],\"creationDate\":1427704363828,\"isRoot\":true,\"owner\":{\"uid\":\"1\"},\"tags\":[],\"title\":\"Title\",\"updateDate\":1427744363828,\"version\":1}", builder.toJson());
    }

    @Test
    public void testGetTopic() throws Exception {
        Assert.assertTrue(builder.getTopic() != null);
    }
}