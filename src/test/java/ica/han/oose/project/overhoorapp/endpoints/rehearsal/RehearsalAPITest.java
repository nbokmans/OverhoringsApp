package ica.han.oose.project.overhoorapp.endpoints.rehearsal;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Niels
 * @version 1.0
 * @since 2-6-2015
 */
public class RehearsalAPITest {

    @Test
    public void testCreateRehearsalFromSummary() {
        Assert.assertEquals(RehearsalAPI.createRehearsal("556ee06e1c00009201e4ea7e", "Testlabel", 1, 1533156045605L).getRehearsal().getLabel(), "Testlabel");
    }

    @Test
    public void testGetRehearsalStatus() {
       // RehearsalAPI.getStatus("556ecf371a0000b3030efb5b");
    }

    @Test
    public void testGetRehearsalQuestion() {
       // RehearsalAPI.getCurrentActiveQuestion("556ecf371a0000b3030efb5b");
    }

    @Test
    public void testResetRehearsal() {
        //RehearsalAPI.reset("556ecf371a0000b3030efb5b");
    }
}
