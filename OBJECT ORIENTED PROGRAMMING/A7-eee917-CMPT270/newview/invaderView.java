/*
EMMANUELLA EYO
CMPT 270
EEE917
11291003
 */

package newview;

import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.JFrame;

import model.GameInfoProvider;
import view.GameInfoPanel;

public class invaderView extends JFrame {
    public static final int TITLE_BAR_HEIGHT = 32;
    public static final int BORDER_WIDTH = 6;

    /*
     * Dependent upon the status of the game, different views are displayed in the frame to show
     * what is going on in the game. When the game is being shown, the game and score panels observe
     * changes in the game, and use a game provider to access information from the game.
     */

    /**
     * Initialize the frame for the various views to be inserted into it, where width and height are
     * the dimensions for the game.
     */
    public invaderView(int width, int height) {
        setTitle("Invader View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Width and height passed in are those for the game panel, so add enough height for the
         * title bar and the information panel, and enough width for the border.
         */
        setSize(width + BORDER_WIDTH, height + GameInfoPanel.HEIGHT + TITLE_BAR_HEIGHT);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
    }

    public void displayPanel(invaderPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().validate();
        Component focusComponent = panel.getFocusComponent();
        if (focusComponent != null)
            focusComponent.requestFocusInWindow();
        setVisible(true);
    }

    public void showInvaderView(GameInfoProvider gameInfoProvider){
        invaderPanel invaderpanel = new invaderPanel(gameInfoProvider);
        gameInfoProvider.addObserver(invaderpanel);
        invaderpanel.setFocusComponent(invaderpanel);
        this.setLocation(800, 0);
        displayPanel(invaderpanel);
    }
    private static final long serialVersionUID = 1;
}
