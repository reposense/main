package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object with field and order parameters provided
 */
public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        //eliminates spaces
        String[] argKeywords = trimmedArgs.split("\\s");

        //accounts for caps entries
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        return new SortCommand(argKeywords[0], argKeywords[1]);
    }
}
