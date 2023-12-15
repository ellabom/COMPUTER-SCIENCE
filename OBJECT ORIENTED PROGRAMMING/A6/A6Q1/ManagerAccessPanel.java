package gui;

import entities.Manager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class ManagerAccessPanel extends JPanel{

    /**
     * The text field for the entry of the Manager's employeeId
     */
    JTextField textField;

    /**
     * Create the panel with the prompt label and text field. If data is entered into the text field
     * that is not valid string, a brief error message is entered at the front of thr text field
     * Otherwise, a new window is created with the Manager's data and operations on the Manager
     * */

    public ManagerAccessPanel() {
        JLabel promptLabel = new JLabel("Access Manager");
        add(promptLabel);
        textField = new JTextField(20);
        add(textField);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String mName= textField.getText();
                ManagerFrame frame = null;
                try {
                    frame  = new ManagerFrame(mName);
                }catch (RuntimeException e){
                    textField.setText("Invalid input: " + textField.getText());
                    textField.revalidate();
                    return;
                }
                frame.setLocation(300 , 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
                textField.setText("");
                textField.revalidate();
                }
        });
    }
    public static final long serialVersionUID = 1;
}
