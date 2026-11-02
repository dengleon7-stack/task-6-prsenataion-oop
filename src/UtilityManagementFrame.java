import javax.swing.*;
import java.awt.*;

public class UtilityManagementFrame extends JFrame {

    private final JTabbedPane tabbedPane;
    private final JLabel statusLabel;

    public UtilityManagementFrame() {
        setTitle("Public Utility Management System");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Public Utility Management System", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(30, 60, 114));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        add(header, BorderLayout.NORTH);

        // Tabbed pane: one tab per operational area, all in ONE window
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addTab("Electricity", new ElectricityPanel());
        tabbedPane.addTab("Waste Services", new WastePanel());
        tabbedPane.addTab("Complaints & Feedback", new ComplaintsPanel());
        tabbedPane.addTab("Complaints", new ComplaintsPanel());
        tabbedPane.addTab("Billing", new BillingPanel());
        tabbedPane.addTab("Why Tabs?", new JustificationPanel());

        // Tooltips for consistent, logical navigation
        tabbedPane.setToolTipTextAt(0, "Manage electricity meters and readings");
        tabbedPane.setToolTipTextAt(1, "Manage water connections and usage");
        tabbedPane.setToolTipTextAt(2, "Schedule waste collection");
        tabbedPane.setToolTipTextAt(3, "Log and track customer complaints");
        tabbedPane.setToolTipTextAt(4, "Generate and view customer bills");
        tabbedPane.setToolTipTextAt(5, "Justification for using JTabbedPane");

        // Keyboard shortcuts Alt+1 ... Alt+6
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setMnemonicAt(i, java.awt.event.KeyEvent.VK_1 + i);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Status bar shows the active section
        statusLabel = new JLabel(" Current section: " + tabbedPane.getTitleAt(0));
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        add(statusLabel, BorderLayout.SOUTH);

        tabbedPane.addChangeListener(e ->
                statusLabel.setText(" Current section: "
                        + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
    }
}