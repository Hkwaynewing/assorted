package flag;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A file based implementation of the flags interface. Flag values are stored in a configuration file.
 */
public class FileFlags implements Flags {

    private final String flagsFilePath;
    private Map<String, Boolean> flagStore;
    private Map<String, Integer> flagToPercentageStore;
    private Map<String, Set<String>> flagToUserIdsMap;

    public FileFlags(String flagsFilePath) {
        this.flagsFilePath = flagsFilePath;
    }

    /**
     * Retrieves the value of a flag by the {@param flagName}.
     */
    public boolean getFlagValue(String flagName, User user) {
        readFlagsFromFile();
        // flag 1 true value:10
        Integer perc = flagToPercentageStore.get(flagName);
        if (perc == 0) {
            return false;
        }

        int userGroup = user.id.hashCode() % 100;
        return userGroup <= perc;

    }

    /**
     * Helper method to load the flag values from the flag configuration file.
     */
    private void readFlagsFromFile() {
        flagToPercentageStore = new HashMap<>();
        List<String> lines;
        try {
            lines = Files.readLines(new File(flagsFilePath), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Unable to open file at " + flagsFilePath);
        }
        for (String line : lines) {
            // Each line is of the form:
            //  <flagName>:<flagValue>
            // e.g.
            //  MyFlag:true
            String[] split = line.split(":");

            flagToPercentageStore.put(split[0], Integer.valueOf(split[1]));
        }
    }


}
