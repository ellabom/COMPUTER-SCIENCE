package gui;

import entities.Manager;

import javax.swing.JFrame;

public class ManagerOpsFrame extends JFrame{

    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 350;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 200;

    /**
     *
     */
    public ManagerOpsFrame(){
        setTitle("Manager Operations");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        ManagerOpsPanel panel = new ManagerOpsPanel();
        add(panel);

    }

    /**
     * A main to rum the GUI version of the residence system that only involves manager operation and the residence
     */

    public static void main(String[] args){

        ManagerOpsFrame frame = new ManagerOpsFrame();
        frame.setLocation(300, 300);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    public static final long serialVersionUID = 1;
}
