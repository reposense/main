package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.stream.Collectors;

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
