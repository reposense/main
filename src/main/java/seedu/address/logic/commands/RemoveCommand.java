package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.exceptions.TeamNotFoundException;

/**
 * Removes a team identified using the team name
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the team specified by the team name.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " Arsenal";

    public static final String MESSAGE_PARAMETERS = "TEAM_NAME";

    public static final String MESSAGE_REMOVE_TEAM_SUCCESS = "Removed Team: %1$s";

    private TeamName targetTeamName;

    public RemoveCommand(TeamName targetTeamName) {
        this.targetTeamName = targetTeamName;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(targetTeamName);
        try {
            model.removeTeam(targetTeamName);
        } catch (TeamNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TEAM_SUCCESS, targetTeamName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetTeamName.equals(((RemoveCommand) other).targetTeamName)); // state check
    }
}
