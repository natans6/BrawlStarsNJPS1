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
    private int countOne;
    private int countTwo;
    private Timer timer;
    private int time;
    private boolean OneisJumping;
    private boolean TwoisJumping;
    private BufferedImage koImage;
    public GraphicsPanel(String name, String nameTwo) {
        countOne = 0;
        countTwo = 0;
        OneisJumping = false;
        TwoisJumping = false;
        int num = (int) (Math.random() * 5) + 1;
        try {
            koImage = ImageIO.read(new File("src/KO.png"));
        } catch (IOException e) {
        System.out.println(e.getMessage());
        }

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


        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player One Health: " + player.getHealthPlayerOne(), 50, 40);
        g.drawString("Player Two Health: " + playerTwo.getHealthPlayerTwo(), 50, 80);
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), player.getWidth(), player.getHeight(), null);
        g.drawImage(playerTwo.getPlayerImage(), playerTwo.getxCoord(), playerTwo.getyCoord(), playerTwo.getWidth(), playerTwo.getHeight(), null);

        g.setColor(Color.WHITE);
        g.fillRect(105,195, 1710, 60);
        g.setColor(Color.red);
        g.fillRect(110, 200, 1700, 50);
        g.setColor(Color.yellow);
        int part = (int) Math.round(((double) player.getHealthPlayerOne()) / 5000 * 800);
        g.fillRect(110 + 800 - part, 200, part, 50);

        int part2 = (int) Math.round(((double) playerTwo.getHealthPlayerTwo()) / 5000 * 800);
        g.fillRect(1010, 200, part2, 50);
        g.drawImage(koImage,910,200,koImage.getWidth(),koImage.getHeight(),null);



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
        // If Statements for damage
        if (key == 69 && player.playerRect().intersects(playerTwo.playerTwoRect())){
            countOne++;
            if (countOne == 1) {
                int damage = (int) (Math.random() * 11) + 60;
                playerTwo.setHealthPlayerTwo(damage);
            }
            if (countOne == 2)  {
                playerTwo.setHealthPlayerTwo(100);
            }
            if (countOne == 3)  {
                playerTwo.setHealthPlayerTwo(150);
            }
            if (countOne == 4)  {
                playerTwo.setHealthPlayerTwo(200);
            }
            if (countOne == 5)  {
                int damage = (int) (Math.random() * 11) + 60;
                playerTwo.setHealthPlayerTwo(damage);
                countOne = 0;
            }
        }
        if (key == 85 && playerTwo.playerTwoRect().intersects(player.playerRect())){
            countTwo++;
            if (countTwo == 1) {
                int damage = (int) (Math.random() * 11) + 60;
                player.setHealthPlayerOne(damage);
            }
            if (countTwo == 2)  {
                player.setHealthPlayerOne(100);
            }
            if (countTwo == 3)  {
                player.setHealthPlayerOne(150);
            }
            if (countTwo == 4)  {
                player.setHealthPlayerOne(200);
            }
            if (countTwo == 5)  {
                int damage = (int) (Math.random() * 11) + 60;
                player.setHealthPlayerOne(damage);
                countTwo = 0;
            }
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
