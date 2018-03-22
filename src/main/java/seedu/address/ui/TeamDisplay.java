package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import seedu.address.model.tag.Tag;

/**
 * A ui for displaying the team currently chosen
 */
public class TeamDisplay extends UiPart<Region> {

    private static final String FXML = "TeamDisplay.fxml";

    private Tag tag1 = new Tag("teamA");
    private Tag tag2 = new Tag("teamB");

    @FXML
    private FlowPane teams;

    public TeamDisplay() {
        super(FXML);
        Label tagLabel = new Label(tag1.getTagName());
        tagLabel.setStyle("-fx-text-fill: red");
        teams.getChildren().add(tagLabel);

        Label tagLabel1 = new Label(tag2.getTagName());
        tagLabel1.setStyle("-fx-text-fill: grey");
        teams.getChildren().add(tagLabel1);
        //currently using tags as a placeholder
    }

}

