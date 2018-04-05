package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    @FXML
    private Circle avatar;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        remark.setText(person.getRemark().value);
        email.setText(person.getEmail().value);
        teamName.setText(person.getTeamName().fullName);
        rating.setText(person.getRating().value);
        position.setText(person.getPosition().getPositionName());
        jerseyNumber.setText(person.getJerseyNumber().value);
        initTags(person);
        setContactImage(person.getAvatar().getValue());
    }


    private void setContactImage(String path) {

        Image img = null;

        try {
            if (new File(path).isFile()) {
                img = new Image(new File(path).toURI().toURL().toString());
            } else {
                img = new Image(getClass().getResource("/images/placeholder.png").toString());
            }
        } catch (MalformedURLException e) {
            img = new Image(getClass().getResource("/images/placeholder.png").toString());
        }
        avatar.setVisible(true);
        avatar.setFill(new ImagePattern(img));
        avatar.setVisible(true);
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
