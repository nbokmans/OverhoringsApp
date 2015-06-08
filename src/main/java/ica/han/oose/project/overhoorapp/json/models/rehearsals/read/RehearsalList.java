/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.read;

public class RehearsalList {
    private Rehearsal[] rehearsals;

    public RehearsalList(){

    }

    public Rehearsal[] getRehearsals() {
        if(rehearsals == null){
            Rehearsal[] r = {};
            return r;
        }
        return rehearsals;
    }

    public void setRehearsals(Rehearsal[] rehearsals) {
        this.rehearsals = rehearsals;
    }
}