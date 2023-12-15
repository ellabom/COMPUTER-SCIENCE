/*
 EMMANUELLA EYO
  EEE917
  11291003
  CMPT 270
 */

package spaceinvaders;

import control.Controller;

/**
 * This is the root class for a game where the player must shoot the invaders before they kill the
 * player.
 */
public class SpaceInvaders {
    /**
     * Create and start a controller that will control the game.
     * 
     * @param args any arguments are ignored
     */
    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}
