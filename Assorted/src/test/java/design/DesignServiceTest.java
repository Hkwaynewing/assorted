package design;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static assorted.canva.design.DesignServiceImpl.clearValues;
import static org.junit.jupiter.api.Assertions.*;

import assorted.canva.design.AuthContext;
import assorted.canva.design.DesignService;
import assorted.canva.design.DesignServiceImpl;

class DesignServiceTest {

    private static final DesignService designService = new DesignServiceImpl();
    private static final AuthContext authContext = new AuthContext();

    @BeforeEach
    void setup() {
        clearValues();
    }

    @Test
    void testCD() {
        String id = designService.createDesign(authContext, "content");
        assertEquals("0", id);
    }

    @Test
    void testGD() {
        String id = designService.createDesign(authContext, "content");
        String content = designService.getDesign(authContext, id);
        assertEquals("content", content);
    }

    @Test
    void testGD_invalidUser() {
        String id = designService.createDesign(authContext, "content");
        AuthContext invalidAuth = new AuthContext();
        invalidAuth.userId = "1";
        String content = designService.getDesign(invalidAuth, id);
        assertNull(content);
    }

    @Test
    void testFDs() {
        String id1 = designService.createDesign(authContext, "content1");
        String id2 = designService.createDesign(authContext, "content2");

        List<String> designs = designService.findDesigns(authContext);
        assertTrue(designs.contains(id1));
        assertTrue(designs.contains(id2));
    }
}