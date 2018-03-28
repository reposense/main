package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * JAXB-friendly adapted version of the Team.
 */
public class XmlAdaptedTeam {


    @XmlElement
    private String teamName;

    @XmlElement
    private List<XmlAdaptedPerson> persons;


    /**
     * Constructs an XmlAdaptedTeam.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTeam() {}

    /**
     * Constructs a {@code XmlAdaptedTeam} with the given {@code teamName}.

     */
    public XmlAdaptedTeam(String teamName) {
        this.teamName = teamName;
        persons = new ArrayList<>();
    }


    /**
     * Converts a given Team into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTeam(Team source) {
        teamName = source.getTeamName().toString();
        //persons.addAll(source.getTeamPlayers().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Team toModelType() throws IllegalValueException {
        if (!Team.isValidTeamName(teamName)) {
            throw new IllegalValueException(Team.MESSAGE_TEAM_CONSTRAINTS);
        }
        return new Team(new TeamName(teamName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTeam)) {
            return false;
        }

        return teamName.equals(((XmlAdaptedTeam) other).teamName);
    }
}
