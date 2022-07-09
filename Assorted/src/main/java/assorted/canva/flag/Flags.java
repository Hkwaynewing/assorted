package assorted.canva.flag;

public interface Flags {

    /**
     * Retrieves the value of a flag by the {@param flagName}. {@param User} contains information about the user
     * requesting the flag value.
     */
    boolean getFlagValue(String flagName, User user);
}
