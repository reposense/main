# Codee
###### /java/seedu/address/ui/TeamDisplayTest.java
``` java
public class TeamDisplayTest extends GuiUnitTest {

    private static final String NEW_TEAM_NAME = "myTeam";
    private static final ShowNewTeamNameEvent SHOW_NEW_TEAM_NAME_EVENT = new ShowNewTeamNameEvent(NEW_TEAM_NAME);

    private TeamDisplay teamDisplay;
    private TeamDisplayHandle teamDisplayHandle;
    private ObservableList<Team> teamList;

    @Before
    public void setUp() {
        teamList = new TeamBuilder().build();
        teamDisplay = new TeamDisplay(teamList);
        uiPartRule.setUiPart(teamDisplay);
        teamDisplayHandle = new TeamDisplayHandle(teamDisplay.getRoot());
    }

    @Test
    public void display() {
        assertTeamDisplay(teamDisplay, teamList);
    }
    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertTeamDisplay(TeamDisplay teamDisplay, ObservableList<Team> teamlist) {
        guiRobot.pauseForHuman();

        // verify team names are displayed correctly
        assertTeamDisplayEquals(teamDisplay, teamDisplayHandle);
    }

    @Test
    public void handleShowNewTeamNameEvent() {
        postNow(SHOW_NEW_TEAM_NAME_EVENT);

        // verify team names are displayed correctly after event
        guiRobot.pauseForHuman();

        teamList.add(new Team(new TeamName(NEW_TEAM_NAME)));
        TeamDisplay expectedTeamDisplay = new TeamDisplay(teamList);
        teamDisplayHandle = new TeamDisplayHandle(teamDisplay.getRoot());
        // verify team names are displayed correctly
        assertTeamDisplayEquals(expectedTeamDisplay, teamDisplayHandle);
    }
}
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParserTest.java
``` java
public class ChangeThemeCommandParserTest {

    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();
    private String[] listThemes = { "Light", "Dark" };

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        for (int i = 0; i < 2; i++) {
            assertParseSuccess(parser, listThemes[i], new ChangeThemeCommand(listThemes[i]));
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty Argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/SetCommandParserTest.java
``` java
public class SetCommandParserTest {

    private SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_validArgs_returnsSetCommand() {
        String userInput = " " + PREFIX_TAG + FRIEND.getTagName() + " " + PREFIX_TAG_COLOUR + "green";
        assertParseSuccess(parser, userInput, new SetCommand(FRIEND, "green"));
    }

}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommandTest.java
``` java
public class ChangeThemeCommandTest {

    private String[] listThemes = { "Light", "Dark" };

    @Test
    public void execute_validTheme_success() {
        assertExecutionSuccess(listThemes[1]);
    }

    @Test
    public void execute_invalidTheme_failure() {
        assertExecutionFailure("FakeTheme", Messages.MESSAGE_INVALID_THEME);
    }

    @Test
    public void equals() {
        ChangeThemeCommand[] listThemeCommands = new ChangeThemeCommand[2];
        for (int i = 0; i < 2; i++) {
            listThemeCommands[i] = new ChangeThemeCommand(listThemes[i]);
        }

        // same object -> returns true
        for (int i = 0; i < 2; i++) {
            assertTrue(listThemeCommands[i].equals(new ChangeThemeCommand(listThemes[i])));
        }

        // different types -> returns false
        for (int i = 0; i < 2; i++) {
            assertFalse(listThemeCommands[i].equals(1));
        }

        // null -> returns false
        for (int i = 0; i < 2; i++) {
            assertFalse(listThemeCommands[i].equals(null));
        }

        // different theme -> returns false
        int j = 1;

        for (int i = 0; i < 2; i++) {
            if (i != j) {
                assertFalse(listThemeCommands[i].equals(listThemeCommands[j]));
            }
            j--;
        }
    }

    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}.
     */
    private void assertExecutionSuccess(String theme) {
        ChangeThemeCommand changethemeCommand = new ChangeThemeCommand(theme);
        try {
            CommandResult commandResult = changethemeCommand.execute();
            assertEquals(String.format(ChangeThemeCommand.MESSAGE_THEME_SUCCESS, theme),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}.
     */
    private void assertExecutionFailure(String theme, String expectedMessage) {
        ChangeThemeCommand changethemeCommand = new ChangeThemeCommand(theme);

        try {
            changethemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }
}
```
###### /java/seedu/address/logic/commands/SetCommandTest.java
``` java
public class SetCommandTest {

    private Model model;
    private Tag tagOne = new Tag("testTagOne");
    private Tag tagTwo = new Tag("testTagTwo");

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {

        SetCommand testCommand = new SetCommand(tagOne, "teal");
        SetCommand testCommandTwo = new SetCommand(tagOne, "teal");
        //Test to ensure command is strictly a SetCommand
        assertFalse(testCommand.equals(new AddCommand(CARL)));
        assertFalse(testCommand.equals(new ClearCommand()));
        assertFalse(testCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(testCommand.equals(new HistoryCommand()));
        assertFalse(testCommand.equals(new HelpCommand()));
        assertFalse(testCommand.equals(new RedoCommand()));
        assertFalse(testCommand.equals(new UndoCommand()));
        assertFalse(testCommand.equals(new ListCommand()));
        assertFalse(testCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY)));


        //Test to check that different tag string returns false
        assertFalse(testCommand.equals(new SetCommand(tagTwo, "teal")));
        assertFalse(testCommandTwo.equals(new SetCommand(tagTwo, "teal")));

        //Test to check that different color strings returns false
        assertFalse(testCommand.equals(new SetCommand(tagOne, "red")));
        assertFalse(testCommandTwo.equals(new SetCommand(tagTwo, "red")));
    }

    @Test
    public void checkCommandResult() throws CommandException {

        //Check if the result message is correct when there is no tags found
        SetCommand command = new SetCommand(new Tag("blablabla"), "teal");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("tag is invalid! Please input a valid tag name!"));

        //When tags is present
        command = new SetCommand(new Tag("friends"), "red");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertFalse(command.execute().feedbackToUser.equals("No such tag"));
        assertTrue(command.execute().feedbackToUser.equals("tag [friends] colour changed to red"));

        //Check if friends tags are set to color
        command = new SetCommand(new Tag("friends"), "teal");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("tag [friends] colour changed to teal"));
        for (Tag tag : model.getAddressBook().getTagList()) {
            if ("friends".equals(tag.tagName)) {
                assertTrue(tag.getTagColour().equals("teal"));
                assertFalse(tag.getTagColour().equals("pink"));
            }
        }
        resetAllTagsToDefault();
    }

    /**
     * This method allows all tags to be set to the default colour "teal"
     */
    public void resetAllTagsToDefault() {
        ObservableList<Tag> allTags = model.getAddressBook().getTagList();
        for (Tag t : allTags) {
            t.changeTagColour("teal");
        }
    }
}
```
###### /java/seedu/address/testutil/TypicalTags.java
``` java
public class TypicalTags {

    public static final Tag GOOD_ATTITUDE = new Tag("goodAttitude", "teal");
    public static final Tag FRIEND = new Tag("friends", "teal");

    private TypicalTags() {} //prevents instantiation


    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Tag tag : getTypicalTags()) {
            try {
                ab.addTag(tag);
            } catch (UniqueTagList.DuplicateTagException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(GOOD_ATTITUDE, FRIEND));
    }

}
```
###### /java/seedu/address/testutil/TeamBuilder.java
``` java
public class TeamBuilder {

    public static final String DEFAULT_TEAMNAME_ONE = "Arsenal";
    public static final String DEFAULT_TEAMNAME_TWO = "Chelsea";

    private ObservableList<Team> teams;

    private Team teamOne;
    private Team teamTwo;
    private TeamName teamNameOne;
    private TeamName teamNameTwo;


    public TeamBuilder() {
        teams = FXCollections.observableArrayList();
        teamNameOne = new TeamName(DEFAULT_TEAMNAME_ONE);
        teamNameTwo = new TeamName(DEFAULT_TEAMNAME_TWO);
        teamOne = new Team(teamNameOne);
        teamTwo = new Team(teamNameTwo);
        teams.add(teamOne);
        teams.add(teamTwo);
    }

    public ObservableList<Team> build() {
        return teams;
    }




}
```
###### /java/guitests/guihandles/TeamDisplayHandle.java
``` java
public class TeamDisplayHandle extends NodeHandle<Node> {
    public static final String TEAM_DISPLAY_ID = "#teams";

    private final List<Label> teamLabels;

    public TeamDisplayHandle(Node teamDisplayNode) {
        super(teamDisplayNode);

        Region teamContainer = getChildNode(TEAM_DISPLAY_ID);
        this.teamLabels = teamContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public List<String> getTeams() {
        return teamLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

}
```
