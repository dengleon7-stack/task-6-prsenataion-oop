import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class SwingTestHelper {


    public static <T extends Component> List<T> findAll(Container root, Class<T> type) {
        List<T> result = new ArrayList<>();
        collect(root, type, result);
        return result;
    }

    private static <T extends Component> void collect(Container container, Class<T> type, List<T> result) {
        for (Component child : container.getComponents()) {
            if (type.isInstance(child)) {
                result.add(type.cast(child));
            }
            if (child instanceof Container) {
                collect((Container) child, type, result);
            }
        }
    }


    public static JButton findButton(Container root, String text) {
        for (JButton button : findAll(root, JButton.class)) {
            if (text.equals(button.getText())) {
                return button;
            }
        }
        throw new AssertionError("Button not found: " + text);
    }
}