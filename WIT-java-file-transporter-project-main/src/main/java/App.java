/**
 * @author Fokkittah
 */

import javax.swing.SwingUtilities;
import view.AppWindow;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppWindow window = new AppWindow();
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}