package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Event handler for undoing clearing of all teams.
 */
// @@author Codee
public class UndoClearTeamsEvent extends BaseEvent {

    public UndoClearTeamsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
