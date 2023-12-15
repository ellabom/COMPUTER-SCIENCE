/*
Emmanuella Eyo 11291003 eee917 270
 */

package gui;

import entities.Student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


//button to initiate student op window
//button to initiate Manager op window
//button to initiate Residence op window
//button to terminate the project


/**
 */
public class MainMenuPanel extends JPanel {

    public MainMenuPanel(){

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        //button to access student operations
        JButton studentOpsButton = new JButton("Student operations");
        studentOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentOpsButton.setMaximumSize(studentOpsButton.getPreferredSize());
        add(studentOpsButton);

        studentOpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentOpsFrame frame = new StudentOpsFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        add(Box.createVerticalGlue());

        //button for manager operations
        JButton managerOpsButton = new JButton("Manager operations");
        managerOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        managerOpsButton.setMaximumSize(studentOpsButton.getPreferredSize());
        add(managerOpsButton);

        managerOpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerOpsFrame frame = new ManagerOpsFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        add(Box.createVerticalGlue());

        JButton residenceOpsButton = new JButton("Residence Information");
        residenceOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        residenceOpsButton.setMaximumSize(residenceOpsButton.getPreferredSize());
        add(residenceOpsButton);
        residenceOpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResidenceFrame frame = new ResidenceFrame();
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setLocation(300, 300);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(exitButton.getPreferredSize());
        add(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
                System.exit(0);
            }
        });
        add(Box.createVerticalGlue());
    }
}
