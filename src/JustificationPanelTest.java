import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;

import static org.junit.jupiter.api.Assertions.*;

class JustificationPanelTest {

    private JTextArea text;

    @BeforeEach
    void setUp() {
        JustificationPanel panel = new JustificationPanel();
        text = SwingTestHelper.findAll(panel, JTextArea.class).get(0);
    }

    @Test
    void textIsReadOnlyAndWrapped() {
        assertFalse(text.isEditable());
        assertTrue(text.getLineWrap());
        assertTrue(text.getWrapStyleWord());
    }

    @Test
    void textExplainsWhyTabsAreUsed() {
        String content = text.getText();
        assertTrue(content.contains("JTabbedPane"));
        assertTrue(content.contains("Scalability"));
        assertTrue(content.contains("Single window"));
    }
}