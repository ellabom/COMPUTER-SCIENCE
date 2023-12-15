package gui;


import javax.swing.JFrame;
import containers.ManagerMapAccess;
import entities.Manager;

public class ManagerFrame extends JFrame{

    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 350;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 400;

    /**
     * Create the frame to display the information for a manager.
     *
     * @param mName the manager's name
     * @precond mName the manager's name
     */

    public ManagerFrame(String mName){

        Manager manager = ManagerMapAccess.getInstance() .get(mName);

        if(manager != null){
            setTitle(manager.getName() + " ("+ mName + ")");
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            ManagerPanel panel = new ManagerPanel(manager);
            add(panel);
        } else {
            throw new RuntimeException("Invalid Manager name " + mName);
        }
    }
    public static final long serialVersionUID = 1;
}
