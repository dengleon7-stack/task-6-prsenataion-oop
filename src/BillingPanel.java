import javax.swing.*;
import java.awt.*;

public class BillingPanel extends JPanel {

    private final JTextField customerIdField = new JTextField(15);
    private final JTextField customerNameField = new JTextField(15);
    private final JTextField electricityField = new JTextField("0", 15);
    private final JTextField waterField = new JTextField("0", 15);
    private final JTextField wasteField = new JTextField("0", 15);
    private final JTextArea billArea = new JTextArea(10, 40);

    private static final double VAT_RATE = 0.18;

    public BillingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Generate Customer Bill"));
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Customer Name:"));
        form.add(customerNameField);
        form.add(new JLabel("Electricity Charge (UGX):"));
        form.add(electricityField);
        form.add(new JLabel("Water Charge (UGX):"));
        form.add(waterField);
        form.add(new JLabel("Waste Collection Charge (UGX):"));
        form.add(wasteField);

        JButton generateButton = new JButton("Generate Bill");
        JButton clearButton = new JButton("Clear");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.add(generateButton);
        buttons.add(clearButton);

        billArea.setEditable(false);
        billArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        billArea.setBorder(BorderFactory.createTitledBorder("Bill"));

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(billArea), BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateBill());
        clearButton.addActionListener(e -> clearFields());
    }

    private void generateBill() {
        try {
            double electricity = Double.parseDouble(electricityField.getText().trim());
            double water = Double.parseDouble(waterField.getText().trim());
            double waste = Double.parseDouble(wasteField.getText().trim());
            double subtotal = electricity + water + waste;
            double vat = subtotal * VAT_RATE;
            double total = subtotal + vat;

            billArea.setText(String.format(
                    "==========  UTILITY BILL  ==========%n"
                            + "Customer ID  : %s%n"
                            + "Customer Name: %s%n"
                            + "------------------------------------%n"
                            + "Electricity  : UGX %,15.2f%n"
                            + "Water        : UGX %,15.2f%n"
                            + "Waste        : UGX %,15.2f%n"
                            + "------------------------------------%n"
                            + "Subtotal     : UGX %,15.2f%n"
                            + "VAT (18%%)    : UGX %,15.2f%n"
                            + "TOTAL DUE    : UGX %,15.2f%n"
                            + "====================================",
                    customerIdField.getText().trim(), customerNameField.getText().trim(),
                    electricity, water, waste, subtotal, vat, total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric charges.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        customerIdField.setText("");
        customerNameField.setText("");
        electricityField.setText("0");
        waterField.setText("0");
        wasteField.setText("0");
        billArea.setText("");
    }
}