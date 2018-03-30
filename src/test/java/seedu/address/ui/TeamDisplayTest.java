package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertTeamDisplayEquals;

import org.junit.Test;

import guitests.guihandles.TeamDisplayHandle;


public class TeamDisplayTest extends GuiUnitTest {

    private TeamDisplayHandle teamDisplayHandle;

    @Test
    public void display() {
        TeamDisplay teamDisplay = new TeamDisplay().build();
        personCard = new PersonCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

    }
}
