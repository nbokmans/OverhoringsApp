/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */

package ica.han.oose.project.overhoorapp.json.models.summaries.read;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.json.wrappers.Summary;

public class Summaries {
    private SummaryElement[] summaries;

    public SummaryElement[] getSummaries() {
        return summaries;
    }

    public void setSummaries(SummaryElement[] summaries) {
        this.summaries = summaries;
    }

    public List<Summary> getSummaryList() {
        final List<Summary> list = new ArrayList<Summary>();
        for (final SummaryElement ele : summaries) {
            list.add(new Summary(ele));
        }
        return list;
    }
}