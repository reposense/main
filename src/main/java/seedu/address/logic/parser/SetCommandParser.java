package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * This class is to check whether Set Command was input correctly
 */
public class SetCommandParser implements Parser<SetCommand> {

    private static final String[] TAG_COLOR_STYLES =
        { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };

    private final UniqueTagList allTags = new UniqueTagList();
    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TAG_COLOUR);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG, PREFIX_TAG_COLOUR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            String colour = ParserUtil.parseTagColour(argMultimap.getValue(PREFIX_TAG_COLOUR).get());
            boolean isValidColour = false;
            for (String tcs : TAG_COLOR_STYLES) {
                if (colour.equals(tcs)) {
                    isValidColour = true;
                }
            }
            if (!isValidColour) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
            }
            return new SetCommand(tag, colour);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
