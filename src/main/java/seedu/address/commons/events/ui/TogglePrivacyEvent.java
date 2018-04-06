package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a toggle privacy change.
 *
/** @@author Codee */
public class TogglePrivacyEvent extends BaseEvent {

    private final Person editedPerson;

    public TogglePrivacyEvent(Person editedPerson) {
        this.editedPerson = editedPerson;
    }

    public Person getPerson() {
        return this.editedPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
