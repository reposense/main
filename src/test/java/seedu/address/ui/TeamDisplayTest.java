package seedu.address.ui;

import static seedu.address.ui.testutil.GuiTestAssert.assertTeamDisplayEquals;

import org.junit.Test;

import guitests.guihandles.TeamDisplayHandle;
import javafx.collections.ObservableList;
import seedu.address.model.team.Team;
import seedu.address.testutil.TeamBuilder;


public class TeamDisplayTest extends GuiUnitTest {

    private TeamDisplay teamDisplay;

    @Test
    public void display() {
        ObservableList<Team> teamList = new TeamBuilder().build();
        teamDisplay = new TeamDisplay(teamList);
        uiPartRule.setUiPart(teamDisplay);
        assertCardDisplay(teamDisplay, teamList);

    }
    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TeamDisplay teamDisplay, ObservableList<Team> teamlist) {
        guiRobot.pauseForHuman();

        TeamDisplayHandle teamDisplayHandle = new TeamDisplayHandle(teamDisplay.getRoot());

        // verify person details are displayed correctly
        assertTeamDisplayEquals(teamDisplay, teamDisplayHandle);
    }
}
