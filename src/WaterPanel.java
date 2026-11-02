import javax.swing.*;
import java.awt.*;

public class WaterPanel extends JPanel {

    private final JTextField customerIdField = new JTextField(15);
    private final JTextField connectionNumberField = new JTextField(15);
    private final JTextField previousReadingField = new JTextField(15);
    private final JTextField currentReadingField = new JTextField(15);
    private final JTextArea outputArea = new JTextArea(8, 40);

    private static final double RATE_PER_CUBIC_METRE = 3200.0; // UGX per m3 (sample rate)

    public WaterPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Water Meter Reading"));
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Connection Number:"));
        form.add(connectionNumberField);
        form.add(new JLabel("Previous Reading (m³):"));
        form.add(previousReadingField);
        form.add(new JLabel("Current Reading (m³):"));
        form.add(currentReadingField);

        JButton calculateButton = new JButton("Calculate Usage");
        JButton clearButton = new JButton("Clear");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.add(calculateButton);
        buttons.add(clearButton);

        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Result"));

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        calculateButton.addActionListener(e -> calculateUsage());
        clearButton.addActionListener(e -> clearFields());
    }

    private void calculateUsage() {
        try {
            double previous = Double.parseDouble(previousReadingField.getText().trim());
            double current = Double.parseDouble(currentReadingField.getText().trim());
            if (current < previous) {
                JOptionPane.showMessageDialog(this,
                        "Current reading cannot be less than previous reading.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double volume = current - previous;
            double cost = volume * RATE_PER_CUBIC_METRE;
            outputArea.setText("Customer ID: " + customerIdField.getText().trim()
                    + "\nConnection No: " + connectionNumberField.getText().trim()
                    + "\nWater consumed: " + volume + " m³"
                    + "\nRate per m³: UGX " + RATE_PER_CUBIC_METRE
                    + "\nWater charge: UGX " + String.format("%,.2f", cost));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric readings.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        customerIdField.setText("");
        connectionNumberField.setText("");
        previousReadingField.setText("");
        currentReadingField.setText("");
        outputArea.setText("");
    }
}