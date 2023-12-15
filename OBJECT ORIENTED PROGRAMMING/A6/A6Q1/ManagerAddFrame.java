package gui;

import javax.swing.JFrame;

/**
 * The frame for the window to enter data for a new managar
 * and cause the creation of the manager
 */
public class ManagerAddFrame extends JFrame {

    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 350;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 200;


    /*** Create the frame to add a student*/
    public ManagerAddFrame(){
        setTitle("Manager Addition");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        ManagerAddPanel panel = new ManagerAddPanel();
        add(panel);
    }
    public static final long serialVersionUID = 1;
}
