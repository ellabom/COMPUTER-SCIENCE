/*
  EYO EMMANUELLA
  11291003
  CMPT 270
  EEE917
 */

package model;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The player in the space invaders game.
 */
public class Player extends GameObject {
    public static final int WIDTH = 46;
    public static final int HEIGHT = 25;

    /** The distance to move when it is time to move. */
    public static final int MOVE_DISTANCE = 15;

    /** The decrease in the score every time hit. */
    public static final int HIT_DECREMENT = 20;

    /* The initial number of lives for the Player. */
    public static final int INITIAL_NUM_LIVES = 4;

    /* The number of lives remaining for the Player. */
    protected int lives;

    /* The current score for the Player. */
    protected int score;

    /** How frequently (in terms of ticks) the player is to change image. */
    public static final int CHANGE_FREQ = 0;

    /** */
    boolean fireRecharge = true;

    /** */
    protected Timer rechargeTimer;

    protected Timer overheatTimer;

    protected Timer coolDownTimer;

    public int heatBuildup = 0;

    boolean isOverheat = false;

    boolean coolDown = true;


    /**
     * Initialize the player.
     */
    public Player(int x, int y, Game game) {
        super(x, y, game, "player");
        width = WIDTH;
        height = HEIGHT;
        lives = INITIAL_NUM_LIVES;
        score = 0;

        rechargeTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireRecharge = true;
            }
        });
        overheatTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heatBuildup = 0;
                isOverheat = false;
            }
        });
        coolDownTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heatBuildup -=1;
                coolDown = false;
            }

        });

        rechargeTimer.setRepeats(false);
        overheatTimer.setRepeats(false);
        coolDownTimer.setRepeats(false);
    }

    /**
     * No actions for the player at clock ticks.
     */
    protected void update() {}

    /**
     * Move to the left.
     */
    public void moveLeft() {
        x = Math.max(x - MOVE_DISTANCE, 0);
    }

    /**
     * Move to the right.
     */
    public void moveRight() {
        x = Math.min(x + MOVE_DISTANCE, game.getWidth() - width);
    }

    /**
     * If canFire, fire a laser.
     */
    public void fire() {
        if (fireRecharge) {
            int laserX = x + (width - Laser.WIDTH) / 2;
            int laserY = y - Laser.HEIGHT;
            game.addLaser(new Laser(laserX, laserY, game));
            fireRecharge = false;
            rechargeTimer.start();
            heatBuildup += 1;
        }

        if(coolDown){
            coolDownTimer.start();
        }
        if(fireRecharge && coolDownTimer.isRunning()){
            coolDownTimer.restart();
        }
        if (heatBuildup >= 5) {
        rechargeTimer = overheatTimer;
        overheatTimer.start();
        } else {fireRecharge = true;}


    }
    /**
     * Handle the collision with another object
     *
     * @param other the object that collied with this instance
     */
    protected void collide(GameObject other) {
        lives = lives - 1;
        moveToLeftSide();
        if (lives == 0) {
            isDead = true;
        }
        score = score - HIT_DECREMENT;
    }

    /**
     * Move to the left side.
     */
    public void moveToLeftSide() {
        x = 0;
    }

    /**
     * @return the number of lives still remaining
     */
    public int getLives() {
        return lives;
    }

    /**
     * Set a new value for the number of lives.
     * 
     * @param lives the new value for the lives field
     */
    public void setLives(int lives) {
        this.lives = lives;
        if (lives == 0) {
            isDead = true;
        }
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param amount the amount by which the score is to be increased
     */
    public void increaseScore(int amount) {
        score = score + amount;
    }

    public int getHeatBuildup(){return heatBuildup;}
}
