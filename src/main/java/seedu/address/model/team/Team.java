package seedu.address.model.team;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Team in the application.
 * Guarantees: details are present and not null, field values are validated, immutable
 */
public class Team {

    private final TeamName teamName;
    private final UniquePersonList players;

    /**
     * Every field must be present and not null.
     */
    public Team(TeamName teamName) {
        this.teamName = teamName;
        this.players = new UniquePersonList();
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public ObservableList<Person> getTeamPlayers() {
        return players.asObservableList();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName())
                .append(", ")
                .append(players.asObservableList().size())
                .append(" players: ");
        getTeamPlayers().forEach(builder::append);
        return builder.toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Team // instanceof handles nulls
                && this.teamName.equals(((Team) other).teamName))
                && this.players.equals(((Team) other).players);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(teamName, players);
    }
}
