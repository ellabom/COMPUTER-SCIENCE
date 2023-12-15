/*
  EMMANUELLA EYO
  11291003
  CMPT 270
  EEE917
 */

package model;

import java.util.List;

/**
 * The information provided by the GameModel for outside access.
 */
public interface GameInfoProvider {
    public void addObserver(GameObserver observer);

    public List<GameObject> getGameObjects();

    public boolean isOver();

    public boolean isPaused();

    public int getLevel();

    public int getPlayerScore();

    public int getPlayerLives();

    public int getTick();

    public int getHeatbuild();

    public int getInvaderCount();

    public void setInvaderSynchronizationObject(Object reference);
}
