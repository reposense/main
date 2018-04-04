package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label teamName;
    @FXML
    private FlowPane tags;
    @FXML
    private Label rating;
    @FXML
    private Label position;
    @FXML
    private Label jerseyNumber;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        if (person.getPhone().isPrivate()) {
            phone.setText(person.getPhone().toString());
        } else {
            phone.setText(person.getPhone().value);
        }

        if (person.getAddress().isPrivate()) {
            address.setText(person.getAddress().toString());
        } else {
            address.setText(person.getAddress().value);
        }

        if (person.getEmail().isPrivate()) {
            email.setText(person.getEmail().toString());
        } else {
            email.setText(person.getEmail().value);
        }

        if (person.getRemark().isPrivate()) {
            remark.setText(person.getRemark().toString());
        } else {
            remark.setText(person.getRemark().value);
        }

        if (person.getRating().isPrivate()) {
            rating.setText(person.getRating().toString());
        } else {
            rating.setText(person.getRating().value);
        }

        teamName.setText(person.getTeamName().fullName);
        position.setText(person.getPosition().getPositionName());
        jerseyNumber.setText(person.getJerseyNumber().value);
        initTags(person);
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.getTagName());
            tagLabel.getStyleClass().add(tag.getTagColour());
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
