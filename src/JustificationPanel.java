import javax.swing.*;
import java.awt.*;

public class JustificationPanel extends JPanel {

    public JustificationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));
        text.setText(
                "WHY JTabbedPane IS USED IN THIS APPLICATION\n\n"
                        + "1. Separation of functions: Electricity, Water, Waste Collection, Complaints and "
                        + "Billing each live in their own panel (class), so every service has a clear boundary.\n\n"
                        + "2. Single window: Officers switch between services without opening or closing "
                        + "extra windows, which reduces screen clutter and saves time.\n\n"
                        + "3. Consistent navigation: All sections are reached from the same tab bar. Tooltips "
                        + "and Alt+number shortcuts make navigation quick and predictable.\n\n"
                        + "4. State is preserved: Data typed in one tab remains when the officer moves to "
                        + "another tab and back, which suits handling a customer who has several issues.\n\n"
                        + "5. Scalability: A new service (e.g. Street Lighting) needs only a new JPanel class "
                        + "and one addTab() line. Existing tabs are not changed.\n\n"
                        + "6. Maintainability and reuse: Each panel is an independent class, so it can be "
                        + "tested, modified or reused without affecting the others.\n\n"
                        + "7. Lower memory and resource use than many JFrames, because all panels share one "
                        + "top-level window.\n\n"
                        + "8. Familiar interface: Tabs resemble browsers and modern software, so officers need "
                        + "little training."
        );

        add(new JScrollPane(text), BorderLayout.CENTER);
    }
}