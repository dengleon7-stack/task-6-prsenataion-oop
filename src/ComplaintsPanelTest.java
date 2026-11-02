import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

class ComplaintsPanelTest {

    private ComplaintsPanel panel;
    private JTextField customerId;
    private JTextArea description;
    private JTable table;

    @BeforeEach
    void setUp() {
        panel = new ComplaintsPanel();
        customerId = SwingTestHelper.findAll(panel, JTextField.class).get(0);
        description = SwingTestHelper.findAll(panel, JTextArea.class).get(0);
        table = SwingTestHelper.findAll(panel, JTable.class).get(0);
    }

    private void logComplaint(String id, String text) {
        customerId.setText(id);
        description.setText(text);
        SwingTestHelper.findButton(panel, "Log Complaint").doClick();
    }

    @Test
    void tableHasSixColumnsAndStartsEmpty() {
        assertEquals(6, table.getColumnCount());
        assertEquals(0, table.getRowCount());
    }

    @Test
    void logComplaint_addsRowWithReferenceAndOpenStatus() {
        logComplaint("C010", "No power since morning");

        assertEquals(1, table.getRowCount());
        assertEquals("CMP-001", table.getValueAt(0, 0));
        assertEquals("C010", table.getValueAt(0, 1));
        assertEquals("Electricity", table.getValueAt(0, 2)); // default category
        assertEquals("Low", table.getValueAt(0, 3));         // default priority
        assertEquals("No power since morning", table.getValueAt(0, 4));
        assertEquals("Open", table.getValueAt(0, 5));
    }

    @Test
    void logComplaint_incrementsReferenceNumber() {
        logComplaint("C011", "First issue");
        logComplaint("C012", "Second issue");

        assertEquals("CMP-001", table.getValueAt(0, 0));
        assertEquals("CMP-002", table.getValueAt(1, 0));
    }

    @Test
    void logComplaint_usesSelectedCategoryAndPriority() {
        JComboBox<?> category = SwingTestHelper.findAll(panel, JComboBox.class).get(0);
        JComboBox<?> priority = SwingTestHelper.findAll(panel, JComboBox.class).get(1);
        category.setSelectedItem("Water");
        priority.setSelectedItem("Urgent");

        logComplaint("C013", "Burst pipe");

        assertEquals("Water", table.getValueAt(0, 2));
        assertEquals("Urgent", table.getValueAt(0, 3));
    }

    @Test
    void logComplaint_clearsFormAfterLogging() {
        logComplaint("C014", "Late bill");

        assertEquals("", customerId.getText());
        assertEquals("", description.getText());
    }

    @Test
    void markAsResolved_changesStatusOfSelectedRow() {
        logComplaint("C015", "Missed collection");
        table.setRowSelectionInterval(0, 0);

        SwingTestHelper.findButton(panel, "Mark as Resolved").doClick();

        assertEquals("Resolved", table.getValueAt(0, 5));
    }

    @Test
    void tableCellsAreNotEditable() {
        logComplaint("C016", "Test");
        assertFalse(table.getModel().isCellEditable(0, 0));
    }
}