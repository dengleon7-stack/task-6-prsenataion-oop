import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ElectricityPanelTest {

    private ElectricityPanel panel;
    private List<JTextField> fields;
    private JTextArea output;

    @BeforeAll
    static void useUsLocale() {
        Locale.setDefault(Locale.US); // so numbers format as 32,500.00
    }

    @BeforeEach
    void setUp() {
        panel = new ElectricityPanel();
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
    void calculateUsage_validReadings_showsUnitsAndCharge() {
        fields.get(0).setText("C001");
        fields.get(1).setText("M-100");
        fields.get(2).setText("100");
        fields.get(3).setText("150");

        SwingTestHelper.findButton(panel, "Calculate Usage").doClick();

        String result = output.getText();
        assertTrue(result.contains("Customer ID: C001"));
        assertTrue(result.contains("Meter No: M-100"));
        assertTrue(result.contains("Units consumed: 50.0 kWh"));
        assertTrue(result.contains("32,500.00")); // 50 x 650
    }

    @Test
    void calculateUsage_sameReadings_givesZeroCharge() {
        fields.get(2).setText("200");
        fields.get(3).setText("200");

        SwingTestHelper.findButton(panel, "Calculate Usage").doClick();

        assertTrue(output.getText().contains("Units consumed: 0.0 kWh"));
        assertTrue(output.getText().contains("0.00"));
    }

    @Test
    void clearButton_emptiesAllFieldsAndOutput() {
        fields.forEach(f -> f.setText("123"));
        output.setText("some result");

        SwingTestHelper.findButton(panel, "Clear").doClick();

        fields.forEach(f -> assertEquals("", f.getText()));
        assertEquals("", output.getText());
    }
}