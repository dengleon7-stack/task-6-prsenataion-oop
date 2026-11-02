import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.GraphicsEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class UtilityManagementFrameTest {

    private UtilityManagementFrame frame;
    private JTabbedPane tabs;

    @BeforeEach
    void setUp() {
        // A JFrame cannot be created on a machine with no display
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        frame = new UtilityManagementFrame();
        tabs = SwingTestHelper.findAll(frame, JTabbedPane.class).get(0);
    }

    @AfterEach
    void tearDown() {
        if (frame != null) {
            frame.dispose();
        }
    }

    private String statusText() {
        return SwingTestHelper.findAll(frame, JLabel.class).stream()
                .map(JLabel::getText)
                .filter(t -> t != null && t.startsWith(" Current section:"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Status label not found"));
    }

    @Test
    void frameHasCorrectTitle() {
        assertEquals("Public Utility Management System", frame.getTitle());
    }

    @Test
    void tabbedPaneHasSixTabsInOrder() {
        assertEquals(6, tabs.getTabCount());
        assertEquals("Electricity", tabs.getTitleAt(0));
        assertEquals("Water", tabs.getTitleAt(1));
        assertEquals("Waste Collection", tabs.getTitleAt(2));
        assertEquals("Complaints", tabs.getTitleAt(3));
        assertEquals("Billing", tabs.getTitleAt(4));
        assertEquals("Why Tabs?", tabs.getTitleAt(5));
    }

    @Test
    void eachTabHoldsTheCorrectPanelClass() {
        assertTrue(tabs.getComponentAt(0) instanceof ElectricityPanel);
        assertTrue(tabs.getComponentAt(1) instanceof WaterPanel);
        assertTrue(tabs.getComponentAt(2) instanceof WastePanel);
        assertTrue(tabs.getComponentAt(3) instanceof ComplaintsPanel);
        assertTrue(tabs.getComponentAt(4) instanceof BillingPanel);
        assertTrue(tabs.getComponentAt(5) instanceof JustificationPanel);
    }

    @Test
    void everyTabHasATooltip() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            assertNotNull(tabs.getToolTipTextAt(i));
        }
    }

    @Test
    void statusBarStartsOnElectricity() {
        assertEquals(" Current section: Electricity", statusText());
    }

    @Test
    void switchingTabUpdatesStatusBar() {
        tabs.setSelectedIndex(3);
        assertEquals(" Current section: Complaints", statusText());

        tabs.setSelectedIndex(4);
        assertEquals(" Current section: Billing", statusText());
    }
}