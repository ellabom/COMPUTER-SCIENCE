package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import commands.AddManager;
import commands.CommandArguments;

public class ManagerAddPanel extends JPanel {

    /**
     * A text area to be used to display an error if one should occur
     */
    JTextArea error = null;

    /**
     * A panel for the entry of the name of the manager.
     */
    ValueEntryPanel namePanel;

    /**
     * A panel for the entry of the social security number of the manager.
     */
    ValueEntryPanel sinPanel;

    /**
     * A panel for the entry of the employee id of the manager
     */
    ValueEntryPanel employeeIdPanel;
    /**
     *
     */
    ValueEntryPanel isConsultantPanel;

    /**
     * Create the panel to obtain data for the creation of a manager, and to cause the
     * manager to be created
     */
    public ManagerAddPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());


        //add a label with a prompt to enter the manager data
        JLabel prompt = new JLabel("Enter Manager's Information");
        prompt.setMaximumSize(prompt.getPreferredSize());
        add(prompt);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        //add a panel with the field for the entry of the manager name
        namePanel = new ValueEntryPanel("Name");
        namePanel.setMaximumSize(namePanel.getPreferredSize());
        add(namePanel);
        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());


        //add panel with the field for the entry of the manager's sin
        sinPanel = new ValueEntryPanel("SIN");
        sinPanel.setMaximumSize(sinPanel.getPreferredSize());
        add(sinPanel);
        sinPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        //add a panel with the field for the entry of the manager's employee id
        employeeIdPanel = new ValueEntryPanel("Employee ID");
        employeeIdPanel.setMaximumSize(employeeIdPanel.getPreferredSize());
        add(employeeIdPanel);
        employeeIdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        isConsultantPanel = new ValueEntryPanel("A Consultant?");
        isConsultantPanel.setMaximumSize(isConsultantPanel.getPreferredSize());
        add(isConsultantPanel);
        isConsultantPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());


        //add a button to submit the information and create the manager
        JButton submitButton = new JButton("Submit");
        submitButton.setMaximumSize(submitButton.getPreferredSize());
        add(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new SubmitListener());
        add(Box.createVerticalGlue());
    }

    /**
     * The class lstening for the press of the submit button. It accesses the name and the
     * employeeID entered and uses the, to add a new Manager to the system.
     * */

    private class SubmitListener implements ActionListener{

        /**
         *
         */
        public void actionPerformed(ActionEvent event){
            if(error != null){
                // remove error from the previous submission
                remove(error);
                error = null;
            }

            String name  = namePanel.getValueAsString();
            String mSin = sinPanel.getValueAsString();
            String employeeId = employeeIdPanel.getValueAsString();
            String Consultant = isConsultantPanel.getValueAsString();

            CommandArguments commandArguments = new CommandArguments();
            AddManager addManager = new AddManager();

            commandArguments.mName = name;
            commandArguments.mSIN = mSin;
            commandArguments.mEN = employeeId;
            commandArguments.response = Consultant;

            addManager.setCommnadArguments(commandArguments);
            addManager.execute();


            if(addManager.wasSuccessful()){
                getTopLevelAncestor().setVisible(false);
            }
            else{
                error = new JTextArea(SplitString.at(addManager.getErrorMessage(), 50));
                error.setMaximumSize(error.getPreferredSize());
                add(error);
                error.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(Box.createVerticalGlue());
                revalidate(); // redraw the window as it is changed
            }
        }
    }

    public static final long serialVersionUID = 1;
}