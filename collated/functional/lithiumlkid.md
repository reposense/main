# lithiumlkid
###### \java\seedu\address\logic\commands\CommandTrie.java
``` java
/**
 * Trie of possible commands. Stores all possible commands for the addressbook.
 * Used for autocomplete functionality by returning a possible command via the attemptAutoComplete function.
 */
public class CommandTrie {

    private TrieNode root = null;
    private Set<String> commandSet;
    private Map<String, String> commandMap;

    public CommandTrie() {
        commandSet = Stream.of(
                EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
                AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,  UndoCommand.COMMAND_WORD,
                SortCommand.COMMAND_WORD, SetCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD,
                CreateCommand.COMMAND_WORD
        ).collect(Collectors.toSet());

        for (String command : commandSet) {
            this.insert(command);
        }

        commandMap = new HashMap<>();
        commandMap.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_PARAMETERS);
        commandMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_PARAMETERS);
        commandMap.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_PARAMETERS);
        commandMap.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_PARAMETERS);
        commandMap.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_PARAMETERS);
        commandMap.put(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_PARAMETERS);
        commandMap.put(SetCommand.COMMAND_WORD, SetCommand.MESSAGE_PARAMETERS);
        commandMap.put(RemarkCommand.COMMAND_WORD, RemarkCommand.MESSAGE_PARAMETERS);
        commandMap.put(CreateCommand.COMMAND_WORD, CreateCommand.MESSAGE_PARAMETERS);

    }

    /**
     * Inserts input into Trie
     *
     * @param input command string
     */
    private void insert(String input) {
        char[] inputArray = input.toCharArray();

        if (root == null) {
            root = new TrieNode(inputArray[0], null, null);
            TrieNode temp = root;
            for (int i = 1; i < inputArray.length; i++) {
                temp.setChild(new TrieNode(inputArray[i], null, null));
                temp = temp.getChild();
            }
        } else {
            TrieNode temp = root;
            int i = 0;
            while (temp.hasSibling()) {
                if (temp.getKey() == inputArray[i]) {
                    temp = temp.getChild();
                    i++;
                } else {
                    temp = temp.getSibling();
                }
            }

            while (i < inputArray.length - 1) {
                if (temp.getKey() == inputArray[i] && temp.hasChild()) {
                    i++;
                    temp = temp.getChild();
                } else if (temp.getKey() == inputArray[i] && !temp.hasChild()) {
                    i++;
                    temp.setChild(new TrieNode(inputArray[i], null, null));
                    temp = temp.getChild();
                } else {
                    temp.setSibling(new TrieNode(inputArray[i], null, null));
                    temp = temp.getSibling();
                }
            }
        }
    }

    /**
     * Indicates whether a node is a endOfWord
     */
    private boolean isEndOfWord(TrieNode current) {
        return !current.hasSibling() && !current.hasChild();
    }

    /**
     * @param input string to autocomplete by returning command with matching prefix
     * @return input if the command is not found, matching command word otherwise
     */
    public String attemptAutoComplete(String input) throws NullPointerException {
        StringBuilder output = new StringBuilder();

        if (commandSet.contains(input)) {
            if (commandMap.containsKey(input)) {
                output.append(" ");
                output.append(commandMap.get(input));
            }
        } else {
            char[] inputArray = input.toLowerCase().toCharArray();
            TrieNode temp = root;
            int i = 0;

            while (!isEndOfWord(temp)) {
                if (i < inputArray.length) {
                    if (temp.getKey() == inputArray[i]) {
                        output.append(inputArray[i]);
                        temp = temp.getChild();
                        i++;
                    } else {
                        temp = temp.getSibling();
                    }
                } else if (temp.hasSibling()) {
                    return input;
                } else {
                    output.append(temp.getKey());
                    temp = temp.getChild();
                }
            }
            output.append(temp.getKey());
        }
        return output.toString();
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }

    /**
     * @return a list of all possible commands for the given input
     */
    public List<String> getOptions(String input) {
        char[] inputArray = input.toLowerCase().toCharArray();
        TrieNode start = root;
        int i = 0;

        while (!isEndOfWord(start) && i < inputArray.length) {
            if (start.getKey() == inputArray[i]) {
                i++;
                start = start.getChild();
            } else {
                start = start.getSibling();
            }
        }

        return findOptions(start, input);
    }

    /**
     * Traverses the trie and gets possible options
     */
    private List<String> findOptions(TrieNode start, String stub) {
        List<String> options = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        output.append(stub);

        if (isEndOfWord(start)) {
            output.append(start.getKey());
            options.add(output.toString());
            return options;
        }
        if (start.hasSibling()) {
            options.addAll(findOptions(start.getSibling(), output.toString()));
        }
        if (start.hasChild()) {
            output.append(start.getKey());
            options.addAll(findOptions(start.getChild(), output.toString()));
        }
        return options;
    }
}
```
###### \java\seedu\address\logic\commands\TrieNode.java
``` java
/**
 * Represents a Trie Node object. Contains a character, a reference to sibling Trie Node and child Trie Node .
 */
public class TrieNode {
    private TrieNode sibling;
    private TrieNode child;

    private char key;

    TrieNode(char key, TrieNode sibling, TrieNode child) {
        this.key = key;
        this.sibling = sibling;
        this.child = child;
    }

    public char getKey() {
        return key;
    }

    public boolean hasSibling() {
        return sibling != null;
    }

    public TrieNode getSibling() {
        return sibling;
    }

    public void setSibling(TrieNode sibling) {
        this.sibling = sibling;
    }

    public boolean hasChild() {
        return child != null;
    }

    public TrieNode getChild() {
        return child;
    }

    public void setChild(TrieNode child) {
        this.child = child;
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case TAB:
            keyEvent.consume();
            handleAutoComplete();
            break;
        default:
            if (suggestions.isShowing()) {
                suggestions.hide();
            }
            // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Handles the Tab button pressed event. Attempts to autocomplete current input.
     */
    private void handleAutoComplete() {
        String input = commandTextField.getText();
        try {
            String command = commandTrie.attemptAutoComplete(input);
            if (input.equals(command)) {
                setStyleToIndicateCommandFailure();
                showSuggestions(commandTrie.getOptions(input));
            } else if (commandSet.contains(command)) {
                this.replaceText(command);
            } else if (commandSet.contains(input)) {
                this.replaceText(input + command);
            }
        } catch (NullPointerException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Populates ContextMenu and shows it to user
     * @param options the options with matching prefix found in Command Trie
     */
    private void showSuggestions(List<String> options) {
        suggestions.getItems().clear();
        for (String option : options) {
            MenuItem item = new MenuItem(option);
            item.setOnAction(event -> replaceText(item.getText()));
            suggestions.getItems().add(item);
        }
        suggestions.show(commandTextField, Side.BOTTOM, 0.0, 0.0);
    }
}
```
