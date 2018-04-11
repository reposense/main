package seedu.address.model.team.exceptions;

/**
 * Signals that the operation is unable to find the specified team.
 */
//@@author jordancjq
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException() {};

    public TeamNotFoundException(String message) {
        super(message);
    }
}
