package assorted.atl;

import java.util.List;

public class Vote {

    private List<String> names;

    public Vote(List<String> names) {
        // names = [u1 -3, u2. u3]
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }
}
