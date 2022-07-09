package assorted.canva.design;

import java.util.List;

interface DesignService {

    /**
     * Creates a design and returns the design id.
     */
    String createDesign(AuthContext ctx, String designContent);

    /**
     * Returns the design content, if the user has access to the design.
     */
    String getDesign(AuthContext ctx, String designId);

    /**
     * Returns a list of design ids that the given context has access to.
     */
    List<String> findDesigns(AuthContext ctx);

}

