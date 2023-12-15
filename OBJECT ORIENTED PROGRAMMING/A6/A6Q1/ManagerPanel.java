/*
EMMANUELLA EYO EEE917 11291003
 */

package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import commands.AssignManagerStudent;
import commands.CommandArguments;
import commands.DropAssociation;

import entities.*;


/**
 *
 *
 */
public class ManagerPanel extends JPanel {


    public ManagerPanel(Manager manager) {

        build(manager);

    }

    private void build(Manager manager) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Name: " + manager.getName()));
        add(new JLabel("Manager's SIN: " + manager.getSIN()));
        add(new JLabel("Employee Id: " + manager.getEmployeeId()));
        if(manager instanceof Consultant){
            JLabel consultant = new JLabel("Speciality: Consultant");
            add(consultant);
        }

        JPanel addStudentPanel = addStudentPanel(manager);
        add(addStudentPanel);
        addStudentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addStudentPanel.setMaximumSize(addStudentPanel.getPreferredSize());

        JPanel removeStudentPanel = removeStudentPanel(manager);
        add(removeStudentPanel);
        removeStudentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeStudentPanel.setMaximumSize(removeStudentPanel.getPreferredSize());

        JPanel accessStudent = accessStudentPanel(manager);
        add(accessStudent);
        accessStudent.setMaximumSize(accessStudent.getPreferredSize());
        accessStudent.setAlignmentX(Component.LEFT_ALIGNMENT);


        add(new JLabel(" "));
        final JButton exitButton = new JButton("Exit");
        add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
            }
        });
    }

    private JPanel addStudentPanel(final Manager manager) {
        JPanel addStudentPanel = new JPanel();
        final JTextField textField = new JTextField(20);
        addStudentPanel.add(textField);

        JButton addButton  = new JButton("Add student");
        addStudentPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String sSin = textField.getText();
                CommandArguments commandArguments = new CommandArguments();
                AssignManagerStudent assignMS = new AssignManagerStudent();

                commandArguments.sSIN = sSin;
                commandArguments.mName = manager.getName();

                assignMS.setCommnadArguments(commandArguments);

                assignMS.execute();
                if(assignMS.wasSuccessful()) {
                    removeAll();
                    build(manager);
                    revalidate();
                }
                else {
                    JOptionPane.showMessageDialog(ManagerPanel.this,
                           assignMS.getErrorMessage());
                }
            }
        });

        return addStudentPanel;
    }

    private JPanel accessStudentPanel(final Manager manager){
        JPanel accessStudentPanel = new JPanel();
        final JTextField textField = new JTextField(20);
        accessStudentPanel.add(textField);
        JButton accessStudent = new JButton("Access Student");
        accessStudentPanel.add(accessStudent);
        accessStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JTextField textField = new JTextField();
                String nsid = textField.getText();

                if(!manager.hasStudent(nsid)) {
                    JOptionPane.showMessageDialog(ManagerPanel.this,
                            manager.getName() + "does not have a student with this nsid" + nsid + "assigned to them");
                }
                else{
                    StudentFrame frame = null;
                    try {
                        frame = new StudentFrame(nsid);
                    } catch (RuntimeException e) {
                        textField.setText("Invalid id: " + textField.getText());
                        textField.revalidate();
                        return;
                    }
                    frame.setLocation(300, 300);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setVisible(true);
                    textField.setText("");
                    textField.revalidate();
                }

            }
        });
        return accessStudentPanel;
    }

    JPanel removeStudentPanel(final Manager manager){

        JPanel removeStudentPanel = new JPanel();
        final JTextField textField = new JTextField(20);
        removeStudentPanel.add(textField);

        final JButton removeStudentB = new JButton("Remove Student");
        removeStudentPanel.add(removeStudentB);

        removeStudentB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sSin = textField.getText();
                DropAssociation dropAssociation = new DropAssociation();
                CommandArguments commandArguments = new CommandArguments();

                commandArguments.mName = manager.getName();
                commandArguments.sSIN = sSin;
                dropAssociation.setCommnadArguments(commandArguments);
                dropAssociation.execute();

                if(dropAssociation.wasSuccessful()){
                    removeAll();
                    build(manager);
                    revalidate();
                }else{

                    JOptionPane.showMessageDialog(ManagerPanel.this, dropAssociation.getErrorMessage());

                }
            }
        });
        return removeStudentPanel;
    }


    public static final long serialVersionUID = 1;

}
