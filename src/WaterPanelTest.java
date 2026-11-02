import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class WaterPanelTest {

    private WaterPanel panel;
    private List<JTextField> fields;
    private JTextArea output;

    @BeforeAll
    static void useUsLocale() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void setUp() {
        panel = new WaterPanel();
        fields = SwingTestHelper.findAll(panel, JTextField.class);
        output = SwingTestHelper.findAll(panel, JTextArea.class).get(0);
    }

    @Test
    void panelHasFourInputFields() {
        assertEquals(4, fields.size());
    }

    @Test
    void outputAreaIsNotEditable() {
        assertFalse(output.isEditable());
    }

    @Test
    void calculateUsage_validReadings_showsVolumeAndCharge() {
        fields.get(0).setText("C002");
        fields.get(1).setText("W-55");
        fields.get(2).setText("10");
        fields.get(3).setText("25");

        SwingTestHelper.findButton(panel, "Calculate Usage").doClick();

        String result = output.getText();
        assertTrue(result.contains("Customer ID: C002"));
        assertTrue(result.contains("Connection No: W-55"));
        assertTrue(result.contains("15.0"));      // 25 - 10
        assertTrue(result.contains("48,000.00")); // 15 x 3200
    }

    @Test
    void clearButton_emptiesAllFieldsAndOutput() {
        fields.forEach(f -> f.setText("9"));
        output.setText("some result");

        SwingTestHelper.findButton(panel, "Clear").doClick();

        fields.forEach(f -> assertEquals("", f.getText()));
        assertEquals("", output.getText());
    }
}