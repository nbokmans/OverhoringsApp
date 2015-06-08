package ica.han.oose.project.overhoorapp.endpoints.summary;

import org.junit.Test;

import java.util.ArrayList;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;

/**
 * @author Niels
 * @version 1.0
 * @since 2-6-2015
 */
public class SummaryAPITest {

    @Test
    public void testGetSummaries() {
        SummaryAPI.getMySummaries().size();
    }

    @Test
    public void testCreateNewSummary() {
        SummaryAPI.createSummary("test", "Frans", new ArrayList<ContributorElement>(), new ArrayList<TagElement>(), 1241234L).getOwnerUID();
    }
}
