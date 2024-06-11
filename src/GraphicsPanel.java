import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
    private boolean gameGoing;
    private boolean playerTwoIsCrouching;
    private boolean playerOneIsCrouching;
    private int countOne;
    private int countTwo;
    private Timer timer;
    private int time;
    private boolean OneisJumping;
    private boolean TwoisJumping;
    private BufferedImage koImage;
    private BufferedImage kot;
    private BufferedImage ryu;
    private BufferedImage chunLi;
    private String name;
    private String nameTwo;

    public GraphicsPanel(String name, String nameTwo) {
        
        this.name = name;
        this.nameTwo = nameTwo;
        countOne = 0;
        countTwo = 0;
        OneisJumping = false;
        TwoisJumping = false;
        gameGoing = true;
        playerOneIsCrouching = false;
        playerTwoIsCrouching = false;
        int num = (int) (Math.random() * 5) + 1;
        try {
            koImage = ImageIO.read(new File("src/KO.png"));
        } catch (IOException e) {
        System.out.println(e.getMessage());
        }
        try {
            kot = ImageIO.read(new File("src/Untitled.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            ryu = ImageIO.read(new File("src/Ryu2.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            chunLi = ImageIO.read(new File("src/ChunLi2.png"));
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
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    private void playFreedomDive() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/assets/XI - Freedom Dive ↓.wav").getAbsoluteFile());
            Clip freedomDive = AudioSystem.getClip();
            freedomDive.open(audioInputStream);
            freedomDive.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        g.setColor(Color.WHITE);
        g.fillRect(105,195, 1710, 60);
        g.setColor(Color.red);
        g.fillRect(110, 200, 1700, 50);
        g.setColor(Color.yellow);
        int part = (int) Math.round(((double) player.getHealthPlayerOne()) / 5000 * 800);
        g.fillRect(110 + 800 - part, 200, part, 50);
        int part2 = (int) Math.round(((double) playerTwo.getHealthPlayerTwo()) / 5000 * 800);
        g.fillRect(1010, 200, part2, 50);
        g.drawImage(koImage, 910, 200, koImage.getWidth(), koImage.getHeight(), null);
        g.drawImage(chunLi, 5,150, chunLi.getWidth(),chunLi.getHeight(),null);
        g.setFont(new Font("Courier BOLD", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString(name, 10, 120);
        g.drawImage(ryu, 1815,150, ryu.getWidth(),ryu.getHeight(),null);
        g.setFont(new Font("Courier BOLD", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString(nameTwo, 1800, 120);
        if (player.gethealthPlayerOne() <= 0)   {
            gameGoing = false;
            player.KOing();
            timer.start();
        }
        if (playerTwo.gethealthPlayerTwo() <= 0)    {
            gameGoing = false;
            playerTwo.KOing();
            timer.start();
        }
        if (time >= 1)  {
            g.drawImage(kot, 400, 200, 1200, 480, null);
            if (player.gethealthPlayerOne() <= 0)   {
                player.removePlayer();
            }
            if (playerTwo.gethealthPlayerTwo() <= 0)    {
                playerTwo.removePlayer();
            }
        }

        if (pressedKeys[69] && gameGoing && !pressedKeys[83])    {
            player.punching();
        }

        if (pressedKeys[65] && gameGoing && !OneisJumping) {
            player.faceLeft();
            player.moveLeft();
            player.walking();
        }

        // player moves right (D)
        if (pressedKeys[68] && gameGoing && !OneisJumping) {
            player.faceRight();
            player.moveRight();
            player.walking();
        }
        if (pressedKeys[83] && gameGoing) {
            player.crouching();
            playerOneIsCrouching = true;
        }

        if (pressedKeys[87] && !OneisJumping && gameGoing ) {
            player.jumping();
            OneisJumping = true;
        }
        //PlayerTwo
        if (pressedKeys[85] && gameGoing && !pressedKeys[75])    {
            playerTwo.punching();
        }

        if (pressedKeys[74] && gameGoing && !TwoisJumping) {
            playerTwo.faceLeft();
            playerTwo.moveLeft();
            playerTwo.walking();
        }

        // player moves right (D)
        if (pressedKeys[76] && gameGoing && !TwoisJumping) {
            playerTwo.faceRight();
            playerTwo.moveRight();
            playerTwo.walking();
        }
        if (pressedKeys[75] && gameGoing) {
            playerTwo.crouching();
            playerTwoIsCrouching = true;
        }

        if (pressedKeys[73] && !TwoisJumping && gameGoing ) {
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
            playerOneIsCrouching = false;
        }
        if (key == 85 || key == 74 || key == 76 || key == 75) {
            playerTwo.idle();
            playerTwoIsCrouching = false;
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
        if (key == 69 && player.playerRect().intersects(playerTwo.playerTwoRect()) && !playerTwoIsCrouching){
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
        if (key == 85 && playerTwo.playerTwoRect().intersects(player.playerRect()) && !playerOneIsCrouching){
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
