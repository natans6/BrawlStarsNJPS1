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
            background = ImageIO.read(new File("src/MenuPic.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            chunLi = ImageIO.read(new File("src/ChunLi.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Ryu = ImageIO.read(new File("src/Ryu.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        enclosingFrame = frame;
        playerOneName = new JTextField(10);
        playerTwoName = new JTextField(10);
        submitButton = new JButton("Submit");
        add(playerOneName);
        add(playerTwoName);
        add(submitButton);
        submitButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("Please enter your name's :", 400, 100);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g.drawString("NAME ONE:", 615, 250);
        playerOneName.setLocation(600, 280);
        g.drawImage(chunLi, 400, 200, null);
        g.setColor(Color.BLACK);
        g.drawString("NAME TWO:", 615, 320);
        g.drawImage(Ryu, 800, 300, null);
        playerTwoName.setLocation(600, 350);
        submitButton.setLocation(615, 400);
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
