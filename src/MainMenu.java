import javax.swing.*;

public class MainMenu {
    private WelcomePanel panel;

    public MainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
        frame.setLocationRelativeTo(null);

        // create and add panel
        panel = new WelcomePanel(frame);
        frame.add(panel);

        // display the frame
        frame.setVisible(true);


    }
}
