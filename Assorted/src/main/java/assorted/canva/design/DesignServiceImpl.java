package assorted.canva.design;

import java.util.*;

public class DesignServiceImpl implements DesignService {

    // key would be userID; value would be data
    private static final Map<String, Map<String, String>> userIdToDataMap = new HashMap<>();
    private static int id = 0;

    @Override
    public String createDesign(AuthContext ctx, String designContent) {
        String designId = String.valueOf(id++);
//        Map<String, String> userDataMap = useridToDataMap.getOrDefault(userId, new HashMap<>());
//        userDataMap.put(designId, designContent);
//        useridToDataMap.put(userId, userDataMap);
        userIdToDataMap.computeIfAbsent(ctx.userId, (unused) -> new HashMap<>()).put(designId, designContent);
        return designId;
    }

    @Override
    public String getDesign(AuthContext ctx, String designId) {
        Map<String, String> userDataMap = userIdToDataMap.getOrDefault(ctx.userId, new HashMap<>());
        return userDataMap.get(designId);
    }

    @Override
    public List<String> findDesigns(AuthContext ctx) {
        Map<String, String> userData = userIdToDataMap.get(ctx.userId);
        if (userData != null) {
            Set<String> result = userData.keySet();
            return new ArrayList<>(result);
        }
        return Collections.emptyList();
    }

    public static void clearValues() {
        userIdToDataMap.clear();
        id = 0;
    }

}
