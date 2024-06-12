import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerTwo {
    private int healthPlayerTwo;
    private double MOVE_AMT;
    private BufferedImage left;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int score;
    private int jumpCount;
    private String name;
    private Boolean walking;
    private Boolean crouch;
    private Boolean jump;
    private Timer timer;
    private int time;
    private Boolean punch;
    private Boolean KOed;
    private Animation run;
    private Animation idle;
    private Animation crouchs;
    private Animation jumps;
    private Animation punchs;
    private Animation KO;
    private Animation currentAnimation;

    // Constructor used to set animations
    public PlayerTwo(String leftImg, String name) {
        healthPlayerTwo = 5000;
        MOVE_AMT = 1;
        crouch = false;
        jump = false;
        punch = false;
        KOed = false;
        jumpCount = 0;
        this.name = name;
        facingRight = false;
        xCoord = 1320;
        yCoord = 375;
        score = 0;
        walking = false;
        try {
            left = ImageIO.read(new File(leftImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<BufferedImage> run_animation = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String filename = "src/RyuIdle/Ryu-Idle" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        idle = new Animation(run_animation, 200);

        run_animation = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String filename = "src/RyuWalking/Ryu-Walking" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        run = new Animation(run_animation, 150);

        run_animation = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            String filename = "src/RyuCrouch/Ryu-Crouch" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        crouchs = new Animation(run_animation, 150);
        run_animation = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String filename = "src/RyuJumping/Ryu-Jumping" + i + ".png";
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
            String filename = "src/RyuPunch/Ryu-Punch" + i + ".png";
            try {
                run_animation.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        punchs = new Animation(run_animation, 100);
        time = 0;

        run_animation = new ArrayList<>();
        for (int i = 3; i <= 3; i++) {
            String filename = "src/RyuKO/Ryu-KO" + i + ".png";
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
    public int gethealthPlayerTwo() {
        return healthPlayerTwo;
    }
    public int getHealthPlayerTwo(){
        return  healthPlayerTwo;
    }
    public int getxCoord() {
        if (facingRight) {
            return (int) xCoord;
        } else {
            return (int) (xCoord + (getPlayerImage().getWidth()));
        }
    }
    public int getyCoord() {
        return (int) yCoord;
    }
    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
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
    public void setHealthPlayerTwo(int damage){
        healthPlayerTwo -= damage;
    }
    public void faceRight() {
        facingRight = true;
    }
    public void faceLeft() {
        facingRight = false;
    }

    // Movement
    public void moveRight() {
        if (xCoord + MOVE_AMT <= 1920 - playerTwoRect().getWidth()) {
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
        yCoord = 375;
    }
    public void crouching() {
        crouch = true;
        yCoord = 513;
        MOVE_AMT = 0.25;
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
        yCoord = 375;
    }
    public void punching()  {
        crouch = false;
        punch = true;
        yCoord = 380;
    }
    public void KOing() {
        KOed = true;
        yCoord = 500;
    }
    public void removePlayer()  {
        currentAnimation = null;
    }
    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerTwoRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
