import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private int healthPlayerOne;
    private double MOVE_AMT;
    private BufferedImage right;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int score;
    private int jumpCount;
    private String name;
    private Boolean walking;
    private Boolean crouch;
    private Boolean jump;
    private Boolean punch;
    private Boolean KOed;
    private Animation run;
    private Animation idle;
    private Animation crouchs;
    private Animation jumps;
    private Animation punchs;
    private Animation KO;
    private Animation currentAnimation;
    private Animation KOStop;

    // Constructor used to set animations
    public Player(String rightImg, String name) {
        healthPlayerOne = 5000;
        MOVE_AMT = 0.5;
        crouch = false;
        jump = false;
        punch = false;
        KOed = false;
        jumpCount = 0;
        this.name = name;
        facingRight = true;
        xCoord = 300; // starting position is (50, 435), right on top of ground
        yCoord = 385;
        score = 0;
        walking = false;
        try {
            right = ImageIO.read(new File(rightImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<BufferedImage> run_animation = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String filename = "src/ChunLiIdle/ChunLi-Idle" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        idle = new Animation(run_animation, 200);

        run_animation = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "src/ChunLiWalking/ChunLi-Walking" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        run = new Animation(run_animation, 150);

        run_animation = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            String filename = "src/ChunLiCrouch/ChunLi-Crouch" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        crouchs = new Animation(run_animation, 150);
        run_animation = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String filename = "src/ChunLiJumping/ChunLi-Jumping" + i + ".png";
            jumpCount++;
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        jumps = new Animation(run_animation, 200);

        run_animation = new ArrayList<>();
        for (int i = 2; i <= 3; i++) {
            String filename = "src/ChunLiPunch/ChunLi-Punch" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        punchs = new Animation(run_animation, 150);

        run_animation = new ArrayList<>();
        for (int i = 3; i <= 3; i++) {
            String filename = "src/ChunLiKO/ChunLi-KO" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        KO = new Animation(run_animation, 150);

    }

    // Method chooses what animation should be played
    public void play() {
        if (KOed)   {
            currentAnimation = KO;
        } else {
            if (!walking && !crouch && !jump && !punch) {
                currentAnimation = idle;
            } else if (crouch && !jump && !punch) {
                currentAnimation = crouchs;
            } else if (jump && !punch && !crouch) {
                currentAnimation = jumps;
            } else if (punch && !crouch) {
                currentAnimation = punchs;
            } else if (walking) {
                currentAnimation = run;
            }
        }
    }

    private void KO() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/KOtrack.wav").getAbsoluteFile());
            Clip freedomDive = AudioSystem.getClip();
            freedomDive.open(audioInputStream);
            freedomDive.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public int getHealthPlayerOne(){
        return  healthPlayerOne;
    }
    public int getxCoord() {
        if (facingRight) {
            return (int) xCoord;
        } else {
            return (int) (xCoord + (getPlayerImage().getWidth()));
        }
    }
    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
    }
    public int getyCoord() {
        return (int) yCoord;
    }
    public int gethealthPlayerOne() {
        return healthPlayerOne;
    }
    public int getHeight() {
        return getPlayerImage().getHeight();
    }
    public int getWidth() {
        if (facingRight) {
            return getPlayerImage().getWidth();
        } else {
            return getPlayerImage().getWidth() * -1;
        }
    }

    // Method used to deduct the health of the player
    public void setHealthPlayerOne(int damage){
        healthPlayerOne -= damage;
    }
    public void faceRight() {
        facingRight = true;
    }
    public void faceLeft() {
        facingRight = false;
    }

    // Movement
    public void moveRight() {
        if (xCoord + MOVE_AMT <= 1920 - playerRect().getWidth()) {
            xCoord += MOVE_AMT;
        }
    }
    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }
    public void walking() {
        walking = true;
        MOVE_AMT = 0.8;
    }
    public void idle() {
        walking = false;
        crouch = false;
        punch = false;
        jump = false;
        yCoord = 385;
    }
    public void crouching() {
        crouch = true;
        yCoord = 455;
        MOVE_AMT = 0.25;
    }
    public void removePlayer()  {
        currentAnimation = null;
    }
    public void jumping() {
        jump = true;
        MOVE_AMT = 0.3;
        int i = 1;
        while (i <= 5) {
            if (i == 1) {
                yCoord = 420;
            }
            if (i == 2) {
                yCoord = 350;
            }
            if (i == 3) {
                yCoord = 200;
            }
            if (i == 4) {
                yCoord = 150;
            }
            i++;
        }
    }
    public void stopJump()  {
        currentAnimation = idle;
        jump = false;
        yCoord = 385;
    }
    public void punching()  {
        crouch = false;
        punch = true;
        yCoord = 415;
    }
    public void KOing() {
        KOed = true;
        yCoord = 500;

    }
    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
