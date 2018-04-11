# jordancjq
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {

    private static final String REMARK_EMPTY = "";
    private static final String REMARK_NONEMPTY = "Some remark to test.";

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // with remark
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_NONEMPTY));
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + REMARK_NONEMPTY;
        System.out.println(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);

        // without remark
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_EMPTY));
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_EMPTY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // missing parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // missing index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + REMARK_NONEMPTY, expectedMessage);
    }

}
```
###### /java/seedu/address/logic/commands/RemoveCommandTest.java
``` java
public class RemoveCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveCommand(null);
    }

    @Test
    public void execute_removeTeam_success() throws Exception {
        Team teamToRemove = TypicalTeams.LIVERPOOL;
        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_TEAM_SUCCESS,
                teamToRemove.getTeamName().toString());

        RemoveCommand removeCommand = prepareCommand(teamToRemove);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.removeTeam(teamToRemove.getTeamName());

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeNonExistingTeam_throwsTeamNotFoundException() throws Exception {
        Team teamToRemove = TypicalTeams.ARSENAL;
        RemoveCommand removeCommand = prepareCommand(teamToRemove);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void equals() {
        TeamName chelsea = TypicalTeams.CHELSEA.getTeamName();
        TeamName liverpool = TypicalTeams.LIVERPOOL.getTeamName();
        RemoveCommand removeChelseaCommand = new RemoveCommand(chelsea);
        RemoveCommand removeLiverpoolCommand = new RemoveCommand(liverpool);

        // same object -> returns true
        assertTrue(removeChelseaCommand.equals(removeChelseaCommand));

        // same values -> returns true
        RemoveCommand removeChelseaCommandCopy = new RemoveCommand(chelsea);
        assertTrue(removeChelseaCommand.equals(removeChelseaCommandCopy));

        // different types -> returns false
        assertFalse(removeChelseaCommand.equals(1));

        // null -> returns false
        assertFalse(removeChelseaCommand.equals(null));

        // different person -> returns false
        assertFalse(removeChelseaCommand.equals(removeLiverpoolCommand));
    }

    /**
     * Returns an {@code RemoveCommand} with parameters {@code team}.
     */
    private RemoveCommand prepareCommand(Team team) {
        RemoveCommand removeCommand = new RemoveCommand(team.getTeamName());
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ViewCommandTest.java
``` java
public class ViewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ViewCommand(null);
    }

    @Test
    public void execute_viewTeam_success() throws Exception {
        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_TEAM_SUCCESS, VALID_TEAM_CHELSEA);

        ViewCommand viewCommand = prepareCommand(VALID_TEAM_CHELSEA);

        assertEquals(expectedMessage, viewCommand.execute().feedbackToUser);

        HighlightSelectedTeamEvent lastEvent =
                (HighlightSelectedTeamEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(VALID_TEAM_CHELSEA, lastEvent.teamName);
    }

    @Test
    public void execute_viewTeamNonExist_fail() throws Exception {
        ViewCommand viewCommand = prepareCommand(VALID_TEAM_ARSENAL);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_TEAM_NOT_FOUND);

        viewCommand.execute();
    }

    @Test
    public void equals() {
        final ViewCommand standardCommand = prepareCommand(VALID_TEAM_CHELSEA);

        // same values -> returns true
        ViewCommand commandWithSameValues = prepareCommand(VALID_TEAM_CHELSEA);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new ViewCommand(new TeamName(VALID_TEAM_ARSENAL))));
    }

    /**
     * Returns an {@code ViewCommand} with parameters {@code team}.
     */
    private ViewCommand prepareCommand(String team) {
        ViewCommand viewCommand = new ViewCommand(new TeamName(team));
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCommand;
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person remarkedPerson = personInList.withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = prepareCommand(indexLastPerson, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person remarkedPerson = personInList.withRemark(VALID_REMARK_EMPTY).build();

        RemarkCommand remarkCommand = prepareCommand(indexLastPerson, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personInFilteredList).withRemark(VALID_REMARK_BOB).build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredList, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personToRemark).withRemark(VALID_REMARK_AMY).build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // remark -> first person remarked
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person remarked again
        expectedModel.updatePerson(personToRemark, remarkedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_EMPTY);

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Updates {@code Person#remark} from a filtered list.
     * 2. Undo the update.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously updated person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the update. This ensures {@code RedoCommand} updates the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonRemarked() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_BOB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personToRemark).withRemark(VALID_REMARK_BOB).build();
        // remark -> remarks second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToRemark, remarkedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToRemark);
        // redo -> remarks same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);

        // same values -> returns true
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/AssignCommandTest.java
``` java
public class AssignCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validTeamAndIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        assertExecutionSuccess(firstTeam, Collections.singletonList(lastPersonIndex));
    }

    @Test
    public void execute_validTeamAndIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        assertExecutionSuccess(firstTeam, Collections.singletonList(INDEX_FIRST_PERSON));
    }

    @Test
    public void execute_validTeamAndInvalidIndexUnfilteredList_fail() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        assertExecutionFailure(firstTeam, Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_INVALID_ALL_INDEX);
    }

    @Test
    public void execute_invalidTeamAndValidIndexUnfilteredList_fail() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Team invalidTeam = ARSENAL;

        assertExecutionFailure(invalidTeam, Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void execute_validTeamAndValidIndexUnfilteredListPersonExistInTeam_fail() throws Exception {
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        AssignCommand assignCommand = prepareCommand(firstTeam.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));

        assignCommand.execute();
        eventsCollectorRule.eventsCollector.reset();

        assertExecutionFailure(firstTeam, Collections.singletonList(INDEX_FIRST_PERSON),
               AssignCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = prepareCommand(CHELSEA.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));
        final AssignCommand secondCommand = prepareCommand(LIVERPOOL.getTeamName(),
                Collections.singletonList(INDEX_SECOND_PERSON));

        // same values -> returns true
        AssignCommand commandWithSameValues = prepareCommand(CHELSEA.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name and index -> returns false
        assertFalse(standardCommand.equals(secondCommand));
    }

    /**
     * Returns an {@code AssignCommand} with parameters {@code team}.
     */
    private AssignCommand prepareCommand(TeamName team, List<Index> indexes) {
        AssignCommand assignCommand = new AssignCommand(team, indexes);
        assignCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return assignCommand;
    }

    /**
     * Executes a {@code AssignCommand} with the given {@code team} and {@code indexes},
     * and checks that {@code HighlightSelectedTeamEvent} is raised with the correct team name.
     */
    private void assertExecutionSuccess(Team team, List<Index> indexes) {
        AssignCommand assignCommand = prepareCommand(team.getTeamName(), indexes);
        String expectedAssignResultMessage = new String();

        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            if (person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                expectedAssignResultMessage = String.format(AssignCommand.MESSAGE_UNSPECIFIED_TEAM_SUCCESS,
                        person.getName().toString(), team.getTeamName().toString());
            } else {
                expectedAssignResultMessage = String.format(AssignCommand.MESSAGE_TEAM_TO_TEAM_SUCCESS,
                        person.getName().toString(), person.getTeamName().toString(), team.getTeamName().toString());
            }
        }

        try {
            CommandResult commandResult = assignCommand.execute();
            assertEquals(String.format(AssignCommand.MESSAGE_SUCCESS + expectedAssignResultMessage),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        HighlightSelectedTeamEvent lastEvent =
                (HighlightSelectedTeamEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(team.getTeamName().toString(), lastEvent.teamName);

        try {
            for (Person person : model.getFilteredPersonList()) {
                team.remove(person);
            }
        } catch (PersonNotFoundException e) {
            throw new AssertionError("not possible");
        }
    }

    /**
     * Executes a {@code AssignCommand} with the given {@code team} and {@code indexes},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Team team, List<Index> indexes, String expectedMessage) {
        AssignCommand assignCommand = prepareCommand(team.getTeamName(), indexes);

        try {
            assignCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
###### /java/seedu/address/logic/commands/RenameCommandTest.java
``` java
public class RenameCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCommand(null);
    }

    @Test
    public void execute_renameTeam_sucess() throws Exception {
        Index indexLastTeam = Index.fromOneBased(model.getAddressBook().getTeamList().size());
        Team targetTeam = model.getAddressBook().getTeamList().get(indexLastTeam.getZeroBased());

        TeamName renameInto = new TeamName(VALID_TEAM_ARSENAL);

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), renameInto);

        String expectedMessage = String.format(RenameCommand.MESSAGE_RENAME_SUCCESS,
                targetTeam.getTeamName().toString(), renameInto.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.renameTeam(targetTeam, renameInto);

        assertCommandSuccess(renameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_renameTeamWithExistingName_failure() throws Exception {
        Team existingTeam = TypicalTeams.LIVERPOOL;
        Team targetTeam = TypicalTeams.CHELSEA;

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), existingTeam.getTeamName());

        assertCommandFailure(renameCommand, model, RenameCommand.MESSAGE_NO_CHANGE);
    }

    @Test
    public void equals() {
        final RenameCommand standardCommand = prepareCommand(new TeamName(VALID_TEAM_ARSENAL),
                new TeamName(VALID_TEAM_BARCELONA));

        // same values -> returns true
        RenameCommand commandWithSameValues = prepareCommand(new TeamName(VALID_TEAM_ARSENAL),
                new TeamName(VALID_TEAM_BARCELONA));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(new TeamName(VALID_TEAM_BARCELONA),
                new TeamName(VALID_TEAM_CHELSEA))));
    }

    /**
     * Returns an {@code RenameCommand} with parameters {@code targetTeam} and {@code updatedTeamName}.
     */
    private RenameCommand prepareCommand(TeamName targetTeam, TeamName updatedTeamName) {
        RenameCommand renameCommand = new RenameCommand(targetTeam, updatedTeamName);
        renameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return renameCommand;
    }

}
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Test Remark");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark sameRemark = new Remark(remark.value);
        assertTrue(remark.equals(sameRemark));

        // different types -> return false
        assertFalse(remark.equals(new Phone("999")));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Another Remark");
        assertFalse(remark.equals(differentRemark));

    }
}
```
###### /java/seedu/address/model/UniqueTeamListTest.java
``` java
public class UniqueTeamListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTeamList uniqueTagList = new UniqueTeamList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/model/team/TeamNameTest.java
``` java
public class TeamNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TeamName(null));
    }

    @Test
    public void constructor_invalidTeamName_throwsIllegalArgumentException() {
        String invalidTeamName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TeamName(invalidTeamName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TeamName.isValidName(null));

        // invalid name
        assertFalse(TeamName.isValidName("")); // empty string
        assertFalse(TeamName.isValidName(" ")); // spaces only
        assertFalse(TeamName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TeamName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TeamName.isValidName("new jersey")); // alphabets only
        assertTrue(TeamName.isValidName("5566")); // numbers only
        assertTrue(TeamName.isValidName("5th cycle")); // alphanumeric characters
        assertTrue(TeamName.isValidName("Georgia Clint")); // with capital letters
        assertTrue(TeamName.isValidName("The Longest Football Team Name You Know")); // long names
    }
}
```
###### /java/seedu/address/testutil/TypicalTeams.java
``` java
/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team CHELSEA = new Team(new TeamName("Chelsea"));
    public static final Team LIVERPOOL = new Team(new TeamName("Liverpool"));

    // Manually added - Team's details found in {@code CommandTestUtil}
    public static final Team ARSENAL = new Team(new TeamName(VALID_TEAM_ARSENAL));
    public static final Team BARCELONA = new Team(new TeamName(VALID_TEAM_BARCELONA));

    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBookWithTeams() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical teams and persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }

        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(CHELSEA, LIVERPOOL));
    }
}
```
