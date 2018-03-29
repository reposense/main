package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

/**
 * Represents a Player's position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Position {

    public static final String MESSAGE_POSITION_CONSTRAINTS =
            "Player's position should be an integer from 1 - 4 where 1 - Striker, 2 - Midfield, 3 - Defender, "
                   + "and 4 - Goalkeeper.";

    public static final String RATING_VALIDATION_REGEX = "[1-4]";

    public final String value;

    /**
     * Constructs an {@code Position}.
     *
     * @param position A valid position.
     */
    public Position(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_POSITION_CONSTRAINTS);
        this.value = position;
    }

    /**
     * Returns true if a given string is a valid player's position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && this.value.equals(((Position) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
