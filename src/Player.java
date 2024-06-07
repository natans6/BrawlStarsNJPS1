import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private double MOVE_AMT;
    private BufferedImage right;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int score;
    private String name;
    private Boolean walking;
    private Boolean crouch;
    private Animation run;
    private Animation idle;
    private Animation crouchs;
    private Animation currentAnimation;


    public Player(String rightImg, String name) {
        MOVE_AMT = 1.6;
        crouch = false;
        this.name = name;
        facingRight = true;
        xCoord = 100; // starting position is (50, 435), right on top of ground
        yCoord = 350;
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

    }

    public void play() {
        if (!walking && !crouch) {
            currentAnimation = idle;
        }else if (crouch)  {
            currentAnimation = crouchs;
        } else if (walking) {
            currentAnimation = run;
        }
    }

    //This function is changed from the previous version to let the player turn left and right
    //This version of the function, when combined with getWidth() and getHeight()
    //Allow the player to turn without needing separate images for left and right

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

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void faceRight() {
        facingRight = true;
    }

    public void faceLeft() {
        facingRight = false;
    }

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
        MOVE_AMT = 1.6;
    }

    public void idle() {
        walking = false;
        crouch = false;
        yCoord = 350;
    }

    public void crouching() {
        crouch = true;
        yCoord = 420;
        MOVE_AMT = 0.8;
    }

    //These functions are newly added to let the player turn left and right
    //These functions when combined with the updated getxCoord()
    //Allow the player to turn without needing separate images for left and right
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

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
