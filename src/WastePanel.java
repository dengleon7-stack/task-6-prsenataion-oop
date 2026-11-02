import javax.swing.*;
import java.awt.*;

public class WastePanel extends JPanel {

    private final JTextField customerIdField = new JTextField(15);
    private final JTextField addressField = new JTextField(15);
    private final JComboBox<String> wasteTypeBox = new JComboBox<>(
            new String[]{"Household", "Commercial", "Recyclable", "Hazardous"});
    private final JComboBox<String> dayBox = new JComboBox<>(
            new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    private final DefaultListModel<String> scheduleModel = new DefaultListModel<>();

    public WastePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Schedule Waste Collection"));
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Pickup Address:"));
        form.add(addressField);
        form.add(new JLabel("Waste Type:"));
        form.add(wasteTypeBox);
        form.add(new JLabel("Collection Day:"));
        form.add(dayBox);

        JButton scheduleButton = new JButton("Schedule Pickup");
        JButton removeButton = new JButton("Remove Selected");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.add(scheduleButton);
        buttons.add(removeButton);

        JList<String> scheduleList = new JList<>(scheduleModel);
        JScrollPane listScroll = new JScrollPane(scheduleList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Scheduled Collections"));

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);

        scheduleButton.addActionListener(e -> {
            String id = customerIdField.getText().trim();
            String address = addressField.getText().trim();
            if (id.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer ID and address are required.",
                        "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            scheduleModel.addElement(id + " | " + address + " | "
                    + wasteTypeBox.getSelectedItem() + " | " + dayBox.getSelectedItem());
            customerIdField.setText("");
            addressField.setText("");
        });

        removeButton.addActionListener(e -> {
            int index = scheduleList.getSelectedIndex();
            if (index >= 0) {
                scheduleModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(this, "Select a collection to remove.",
                        "No Selection", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}