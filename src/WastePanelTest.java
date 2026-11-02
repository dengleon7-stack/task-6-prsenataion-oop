import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WastePanelTest {

    private WastePanel panel;
    private List<JTextField> fields;
    private JList<?> scheduleList;

    @BeforeEach
    void setUp() {
        panel = new WastePanel();
        fields = SwingTestHelper.findAll(panel, JTextField.class);
        scheduleList = SwingTestHelper.findAll(panel, JList.class).get(0);
    }

    @Test
    void scheduleListStartsEmpty() {
        assertEquals(0, scheduleList.getModel().getSize());
    }

    @Test
    void schedulePickup_addsEntryWithDefaultSelections() {
        fields.get(0).setText("C003");
        fields.get(1).setText("Plot 5 Kampala Road");

        SwingTestHelper.findButton(panel, "Schedule Pickup").doClick();

        assertEquals(1, scheduleList.getModel().getSize());
        assertEquals("C003 | Plot 5 Kampala Road | Household | Monday",
                scheduleList.getModel().getElementAt(0));
    }

    @Test
    void schedulePickup_usesSelectedWasteTypeAndDay() {
        JComboBox<?> wasteType = SwingTestHelper.findAll(panel, JComboBox.class).get(0);
        JComboBox<?> day = SwingTestHelper.findAll(panel, JComboBox.class).get(1);
        wasteType.setSelectedItem("Commercial");
        day.setSelectedItem("Friday");
        fields.get(0).setText("C004");
        fields.get(1).setText("Industrial Area");

        SwingTestHelper.findButton(panel, "Schedule Pickup").doClick();

        assertEquals("C004 | Industrial Area | Commercial | Friday",
                scheduleList.getModel().getElementAt(0));
    }

    @Test
    void schedulePickup_clearsInputFieldsAfterAdding() {
        fields.get(0).setText("C005");
        fields.get(1).setText("Ntinda");

        SwingTestHelper.findButton(panel, "Schedule Pickup").doClick();

        assertEquals("", fields.get(0).getText());
        assertEquals("", fields.get(1).getText());
    }

    @Test
    void removeSelected_deletesTheSelectedEntry() {
        fields.get(0).setText("C006");
        fields.get(1).setText("Bugolobi");
        SwingTestHelper.findButton(panel, "Schedule Pickup").doClick();
        scheduleList.setSelectedIndex(0);

        SwingTestHelper.findButton(panel, "Remove Selected").doClick();

        assertEquals(0, scheduleList.getModel().getSize());
    }
}