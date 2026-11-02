import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class BillingPanelTest {

    private BillingPanel panel;
    private List<JTextField> fields;
    private JTextArea billArea;

    @BeforeAll
    static void useUsLocale() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void setUp() {
        panel = new BillingPanel();
        fields = SwingTestHelper.findAll(panel, JTextField.class);
        billArea = SwingTestHelper.findAll(panel, JTextArea.class).get(0);
    }

    @Test
    void panelHasFiveInputFields() {
        assertEquals(5, fields.size());
    }

    @Test
    void chargeFieldsDefaultToZero() {
        assertEquals("0", fields.get(2).getText());
        assertEquals("0", fields.get(3).getText());
        assertEquals("0", fields.get(4).getText());
    }

    @Test
    void generateBill_calculatesSubtotalVatAndTotal() {
        fields.get(0).setText("C020");
        fields.get(1).setText("Aisha Nakato");
        fields.get(2).setText("100000");
        fields.get(3).setText("50000");
        fields.get(4).setText("20000");

        SwingTestHelper.findButton(panel, "Generate Bill").doClick();

        String bill = billArea.getText();
        assertTrue(bill.contains("C020"));
        assertTrue(bill.contains("Aisha Nakato"));
        assertTrue(bill.contains("170,000.00")); // subtotal
        assertTrue(bill.contains("30,600.00"));  // 18% VAT
        assertTrue(bill.contains("200,600.00")); // total
    }

    @Test
    void generateBill_allZeroCharges_totalIsZero() {
        SwingTestHelper.findButton(panel, "Generate Bill").doClick();

        assertTrue(billArea.getText().contains("TOTAL DUE"));
        assertTrue(billArea.getText().contains("0.00"));
    }

    @Test
    void clearButton_resetsFieldsAndBill() {
        fields.get(0).setText("C021");
        fields.get(2).setText("5000");
        SwingTestHelper.findButton(panel, "Generate Bill").doClick();

        SwingTestHelper.findButton(panel, "Clear").doClick();

        assertEquals("", fields.get(0).getText());
        assertEquals("", fields.get(1).getText());
        assertEquals("0", fields.get(2).getText());
        assertEquals("0", fields.get(3).getText());
        assertEquals("0", fields.get(4).getText());
        assertEquals("", billArea.getText());
    }
}