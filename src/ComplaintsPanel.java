import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ComplaintsPanel extends JPanel {

    private final JTextField customerIdField = new JTextField(15);
    private final JComboBox<String> categoryBox = new JComboBox<>(
            new String[]{"Electricity", "Water", "Waste Collection", "Billing", "Other"});
    private final JComboBox<String> priorityBox = new JComboBox<>(
            new String[]{"Low", "Medium", "High", "Urgent"});
    private final JTextArea descriptionArea = new JTextArea(3, 20);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Ref No", "Customer ID", "Category", "Priority", "Description", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable complaintTable = new JTable(tableModel);
    private int complaintCounter = 1;

    public ComplaintsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Log a Customer Complaint"));
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Category:"));
        form.add(categoryBox);
        form.add(new JLabel("Priority:"));
        form.add(priorityBox);
        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descriptionArea));

        JButton logButton = new JButton("Log Complaint");
        JButton resolveButton = new JButton("Mark as Resolved");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.add(logButton);
        buttons.add(resolveButton);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        JScrollPane tableScroll = new JScrollPane(complaintTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Complaint Register"));

        add(top, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);

        logButton.addActionListener(e -> logComplaint());
        resolveButton.addActionListener(e -> resolveComplaint());
    }

    private void logComplaint() {
        String id = customerIdField.getText().trim();
        String description = descriptionArea.getText().trim();
        if (id.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer ID and description are required.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ref = String.format("CMP-%03d", complaintCounter++);
        tableModel.addRow(new Object[]{ref, id, categoryBox.getSelectedItem(),
                priorityBox.getSelectedItem(), description, "Open"});
        customerIdField.setText("");
        descriptionArea.setText("");
    }

    private void resolveComplaint() {
        int row = complaintTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a complaint first.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableModel.setValueAt("Resolved", row, 5);
    }
}