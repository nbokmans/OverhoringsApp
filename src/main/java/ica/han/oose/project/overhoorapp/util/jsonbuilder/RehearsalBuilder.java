package ica.han.oose.project.overhoorapp.util.jsonbuilder;

import ica.han.oose.project.overhoorapp.json.models.rehearsals.create.Rehearsal;
import ica.han.oose.project.overhoorapp.util.Config;

/**
 * Provides an easy way to create a new Rehearsal object for serialization.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 2-6-2015
 */
public class RehearsalBuilder {

    private Rehearsal rehearsal;

    public RehearsalBuilder() {
        rehearsal = new Rehearsal();
    }

    public RehearsalBuilder setLabel(final String label) {
        rehearsal.setLabel(label);
        return this;
    }

    public RehearsalBuilder setMinCorrect(final int minCorrect) {
        rehearsal.setMinCorrect(minCorrect);
        return this;
    }

    public RehearsalBuilder setEndDate(final long endDate) {
        rehearsal.setEndDate(endDate);
        return this;
    }

    public RehearsalBuilder setTopicID(final String topicID) {
        rehearsal.setTopicIds(new String[]{topicID});
        return this;
    }

    public String toJson() {
        return Config.GENSON.serialize(rehearsal);
    }
}
