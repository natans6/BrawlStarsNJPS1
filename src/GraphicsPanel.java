import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private Player player;
    private PlayerTwo playerTwo;
    private boolean[] pressedKeys;
    private Timer timer;
    private int time;
    private boolean OneisJumping;
    private boolean TwoisJumping;

    public GraphicsPanel(String name, String nameTwo) {
        OneisJumping = false;
        TwoisJumping = false;
        int num = (int) (Math.random() * 5) + 1;
        if (num == 1)   {
            try {
                background = ImageIO.read(new File("src/Map1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (num == 2)   {
            try {
                background = ImageIO.read(new File("src/Map2.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (num == 3)   {
            try {
                background = ImageIO.read(new File("src/Map3.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (num == 4)   {
            try {
                background = ImageIO.read(new File("src/Map4.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (num == 5)   {
            try {
                background = ImageIO.read(new File("src/Map5.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        player = new Player("src/ChunLiIdle/ChunLi-Idle1.png", name);
        playerTwo = new PlayerTwo("src/RyuIdle/Ryu-Idle1.png", nameTwo);
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        player.play();
        playerTwo.play();
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);  // the order that things get "painted" matter; we put background down first
        //Here we use drawImage with additional parameters for width and height
        //Combined with the modified functions in the Player class, this does not modify the actual player image
        //Instead, it allows us to modify how the player image is drawn on the graphics object
        //However, this could potentially introduce desyncs between the graphics and the game logic
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), player.getWidth(), player.getHeight(), null);
        g.drawImage(playerTwo.getPlayerImage(), playerTwo.getxCoord(), playerTwo.getyCoord(), playerTwo.getWidth(), playerTwo.getHeight(), null);
        // this loop does two things:  it draws each Coin that gets placed with mouse clicks,
        // and it also checks if the player has "intersected" (collided with) the Coin, and if so,
        // the score goes up and the Coin is removed from the arraylist


        if (pressedKeys[69])    {
            player.punching();
        }

        if (pressedKeys[65]) {
            player.faceLeft();
            player.moveLeft();
            player.walking();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.faceRight();
            player.moveRight();
            player.walking();
        }
        if (pressedKeys[83]) {
            player.crouching();
        }

        if (pressedKeys[87] && !OneisJumping) {
            player.jumping();
            OneisJumping = true;
        }
        //PlayerTwo
        if (pressedKeys[85])    {
            playerTwo.punching();
        }

        if (pressedKeys[74]) {
            playerTwo.faceLeft();
            playerTwo.moveLeft();
            playerTwo.walking();
        }

        // player moves right (D)
        if (pressedKeys[76]) {
            playerTwo.faceRight();
            playerTwo.moveRight();
            playerTwo.walking();
        }
        if (pressedKeys[75]) {
            playerTwo.crouching();
        }

        if (pressedKeys[73] && !TwoisJumping) {
            playerTwo.jumping();
            TwoisJumping = true;
        }
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
        if (key == 65 || key == 68 || key == 83 || key == 69) {
            player.idle();
        }
        if (key == 85 || key == 74 || key == 76 || key == 75) {
            playerTwo.idle();
        }
        if (key == 87)  {
            player.stopJump();
            OneisJumping = false;
        }
        if (key == 73)  {
            playerTwo.stopJump();
            TwoisJumping = false;
        }
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time++;
        }
    }
}
