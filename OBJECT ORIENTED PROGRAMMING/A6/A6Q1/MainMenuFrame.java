/*
EMMANUELLA EYO EEE917 11291003 270
 */

package gui;

import javax.swing.JFrame;

public class MainMenuFrame extends JFrame {

    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 450;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 350;

    public MainMenuFrame(){
        setTitle("Main Menu");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        MainMenuPanel panel = new MainMenuPanel();
        add(panel);
    }


    public static void main(String[] args) {

        MainMenuFrame frame = new MainMenuFrame();
        frame.setLocation(300, 300);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    public static final long serialVersionUID = 1;
}

