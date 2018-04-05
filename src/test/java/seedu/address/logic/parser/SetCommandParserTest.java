package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.SetCommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTags.FRIEND;

/** @@author Codee */
public class SetCommandParserTest {

    private SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_validArgs_returnsSetCommand() {
        String userInput = " " + PREFIX_TAG + FRIEND.getTagName() + " " + PREFIX_TAG_COLOUR + "green";
        assertParseSuccess(parser, userInput, new SetCommand(FRIEND, "green"));
    }

}
