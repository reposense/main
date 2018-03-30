package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import seedu.address.model.team.Team;

/**
 * A ui for displaying the team currently chosen
 */
public class TeamDisplay extends UiPart<Region> {

    private static final String FXML = "TeamDisplay.fxml";

    @FXML
    private FlowPane teams;

    public TeamDisplay(ObservableList<Team> teamList) {
        super(FXML);
        initTeams(teamList);
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTeams(ObservableList<Team> teamList) {
        for (Team t: teamList) {
            System.out.println("team is "+ t);
            Label teamLabel = new Label(t.getTeamName().toString());
            teamLabel.setStyle("-fx-text-fill: #9cb3d8");
            teams.getChildren().add(teamLabel);
            teams.setHgap(10);
        }
    }
}

