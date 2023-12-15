/*
EMMMANUELLA EYO
11291003
EEE917
CMPT 270
 */

package newview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import model.GameInfoProvider;
import model.GameObserver;
import util.PropertiesDiskStorage;
import view.GraphicsPanel;
import model.Player;


public class invaderPanel extends GraphicsPanel implements GameObserver {

    private final GameInfoProvider gameInfoProvider;

    protected Component focusComponent;

    public invaderPanel(GameInfoProvider gameInfoProvider) {
        this.gameInfoProvider = gameInfoProvider;
        setDoubleBuffered(true);
    }

    public synchronized void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D buffered = (Graphics2D) graphics;

        setBackground(Color.BLACK);
        buffered.setPaint(Color.GREEN);

        draw(buffered);
        buffered.drawLine(0 ,35, 350, 35);

    }

    private void draw(Graphics2D graphics2D) {
        int rInvader = gameInfoProvider.getInvaderCount();
        int heat = gameInfoProvider.getHeatbuild();

        drawString(40, 35, "Invaders Remaining: " + rInvader, 40, graphics2D);
        if(heat < 5){
            drawString(50, 60, "Heat level: " + heat , 20, graphics2D);
        }
        else
            drawString(50, 60, "OverHeated!", 20, graphics2D);

        /**
         *
         */
        List<String> invader = PropertiesDiskStorage.getInstance().getProperties("newview");
        /**
         *
         */
        drawImage(100, 80, 350, 350, invader.get(rInvader % 2), graphics2D);
    }


    /**
     * @return the Component of this panel that should have the focus when the frame is realized.
     */
    public Component getFocusComponent() {
        return focusComponent;
    }

    /**
     * Set the Component of this panel that should have the focus when the frame is realized.
     */
    public void setFocusComponent(Component focusComponent) {
        this.focusComponent = focusComponent;
    }


    @Override
    public void gameChanged() {
        repaint();
    }

    private static final long serialVersionUID = 1;
}
