package at;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FindWinner {

    Map<String, Integer> nameToScoreMap = new HashMap<>();

    // u1 -> [v1, v2, v3]
    // u2 -> [v1, v2, v3]
    Map<String, List<Integer>> userToVotesMap = new HashMap<>();

    /**
     * For a list of votes, return an ordered list of candidate in descending order of their votes. 1st - 3 2nd - 2 3rd
     * -1
     */
    List<String> findWinner(List<Vote> votes) {
        // loop votes
        for (Vote vote : votes) {
            List<String> names = vote.getNames();
            int loopSize = Math.min(names.size(), 3);
            for (int i = 0; i < loopSize; i++) {
                String name = names.get(i);
                Integer score = nameToScoreMap.getOrDefault(name, 0);

                updateVoteCount(i, name);

                int increment = 3 - i;
                score = score + increment;
                nameToScoreMap.put(name, score);
            }
        }

        List<String> nameList = nameToScoreMap.entrySet().stream().sorted((v1, v2) -> v2.getValue() - v1.getValue())
                .map(Entry::getKey).collect(
                        Collectors.toList());

        return nameList;
    }

    private void updateVoteCount(int i, String name) {
        List<Integer> integers = userToVotesMap.computeIfAbsent(name, (v) -> new ArrayList<>());
        Integer voteCount = integers.get(i);
        voteCount = voteCount + 1;
        integers.set(i, voteCount);
    }

}
