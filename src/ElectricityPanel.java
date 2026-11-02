import javax.swing.*;
import java.awt.*;

// Electricity feature branch
public class ElectricityPanel extends JPanel {

    private final JTextField customerIdField = new JTextField(15);
    private final JTextField meterNumberField = new JTextField(15);
    private final JTextField previousReadingField = new JTextField(15);
    private final JTextField currentReadingField = new JTextField(15);
    private final JTextArea outputArea = new JTextArea(8, 40);

    private static final double RATE_PER_UNIT = 650.0; // UGX per kWh (sample rate)

    public ElectricityPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Electricity Meter Reading"));
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Meter Number:"));
        form.add(meterNumberField);
        form.add(new JLabel("Previous Reading (kWh):"));
        form.add(previousReadingField);
        form.add(new JLabel("Current Reading (kWh):"));
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
            double units = current - previous;
            double cost = units * RATE_PER_UNIT;
            outputArea.setText("Customer ID: " + customerIdField.getText().trim()
                    + "\nMeter No: " + meterNumberField.getText().trim()
                    + "\nUnits consumed: " + units + " kWh"
                    + "\nRate per unit: UGX " + RATE_PER_UNIT
                    + "\nElectricity charge: UGX " + String.format("%,.2f", cost));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric readings.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        customerIdField.setText("");
        meterNumberField.setText("");
        previousReadingField.setText("");
        currentReadingField.setText("");
        outputArea.setText("");
    }
}