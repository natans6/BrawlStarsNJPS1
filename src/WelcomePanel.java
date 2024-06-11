import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements ActionListener {
    private BufferedImage background;
    private BufferedImage chunLi;
    private BufferedImage Ryu;
    private JTextField playerOneName;
    private JTextField playerTwoName;
    private JButton submitButton;
    private JButton clearButton;
    private JFrame enclosingFrame;
    private BufferedImage goomba;

    public WelcomePanel(JFrame frame) {
        try {
            background = ImageIO.read(new File("src/MainMenuPic.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            chunLi = ImageIO.read(new File("src/Ryu.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Ryu = ImageIO.read(new File("src/ChunLi.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        enclosingFrame = frame;
        playerOneName = new JTextField(10);
        playerTwoName = new JTextField(10);
        submitButton = new JButton("START");
        add(playerOneName);
        add(playerTwoName);
        add(submitButton);
        submitButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        g.setFont(new Font("Courier BOLD", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        g.drawString("PLAYER ONE:", 665, 250);
        playerOneName.setLocation(660, 280);
        g.drawImage(chunLi, 450, 200, null);
        g.setColor(Color.WHITE);
        g.drawString("PLAYER TWO:", 665, 320);
        g.drawImage(Ryu, 850, 300, null);
        playerTwoName.setLocation(660, 350);
        submitButton.setLocation(685, 400);
    }

    // ACTIONLISTENER INTERFACE METHODS
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if ((button == submitButton)) {
                String playerName = playerOneName.getText();
                String player2Name = playerTwoName.getText();
                MainFrame f = new MainFrame(playerName, player2Name);
                enclosingFrame.setVisible(false);
            } else {
                playerOneName.setText("");
                playerTwoName.setText("");
            }
        }
    }
}
