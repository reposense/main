package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Player's avatar in the address book. Contains filepath to avatar image file.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvatar(String)}
 */
public class Avatar {

    public static final String MESSAGE_AVATAR_CONSTRAINTS =
            "Please specify the filepath for the avatar image eg. C:\\image.png\n" +
            "Image file should be 200x200 and in png format";

    public static final String AVATAR_VALIDATION_PATTERN = "([^\\s]+(\\.(?i)(png))$)";

    public static final String PLACEHOLDER_FILEPATH = "images\\placeholder.png";

    private String filePath;

    private String value;

    /**
     * Constructs an {@code Avatar}.
     *
     * @param avatar A valid avatar.
     */
    public Avatar(String avatar) {
        requireNonNull(avatar);
        checkArgument(isValidAvatar(avatar), MESSAGE_AVATAR_CONSTRAINTS);
        this.value = PLACEHOLDER_FILEPATH;
        this.filePath = avatar;
    }

    /**
     * Returns true if a given string is a valid player's avatar.
     */
    public static boolean isValidAvatar(String test) {
        Pattern pattern = Pattern.compile(AVATAR_VALIDATION_PATTERN);
        Matcher matcher = pattern.matcher(test);
        return matcher.matches() || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    public void setFilePath(String player) throws IOException {
        if (filePath.equals("<UNSPECIFIED>")) return;
        final File file = new File(filePath);

        Path dest = new File("images/" + player + ".png").toPath();
        Files.createDirectories(Paths.get("images")); // Creates missing directories if any
        Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        this.value = dest.toString();
        this.filePath = dest.toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
