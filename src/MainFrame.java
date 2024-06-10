import javax.swing.*;
import java.awt.*;

public class MainFrame implements Runnable {

    private GraphicsPanel panel;
    JPanel healthBarPanelOne;

    public MainFrame(String name, String nameTwo) {
        JFrame frame = new JFrame("Skibidi Fighters - The Battle of Aura ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080); // 540 height of image + 40 for window menu bar
        frame.setLocationRelativeTo(null); // auto-centers frame in screen
        // create and add panel
        panel = new GraphicsPanel(name, nameTwo);
        frame.add(panel);
        // display the frame
        frame.setVisible(true);

        // start thread, required for animation
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            panel.repaint();  // we don't ever call "paintComponent" directly, but call this to refresh the panel
        }
    }
}
