package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a change in the person details panel.
 *
 /** @@author Codee */
public class PersonDetailsChangedEvent extends BaseEvent {

    private final Person editedPerson;

    public PersonDetailsChangedEvent(Person editedPerson) {
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
