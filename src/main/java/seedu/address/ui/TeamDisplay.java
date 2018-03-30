package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import seedu.address.model.team.Team;

import java.util.List;

/**
 * A ui for displaying the team currently chosen
 */
public class TeamDisplay extends UiPart<Region> {

    private static final String FXML = "TeamDisplay.fxml";
    private ObservableList<Team> teamList;

    @FXML
    private FlowPane teams;

    public TeamDisplay(ObservableList<Team> teamList) {
        super(FXML);
        this.teamList = teamList;
        initTeams();
        getTeams();
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTeams() {
        for (Team t: this.teamList) {
            Label teamLabel = new Label(t.getTeamName().toString());
            teamLabel.setStyle("-fx-text-fill: #9cb3d8");
            teams.getChildren().add(teamLabel);
            teams.setHgap(10);
        }
    }

    public List<String> getTeams() {
        List<String> listOfTeams = FXCollections.observableArrayList();
        for (Team t: teamList) {
            listOfTeams.add(t.getTeamName().toString());
        }
        return listOfTeams;
    }


}

