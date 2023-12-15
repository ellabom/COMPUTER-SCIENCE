package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import containers.ManagerMapAccess;

public class ManagerOpsPanel extends JPanel {

        public ManagerOpsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(Box.createVerticalGlue());


            JButton addButton = new JButton("Add Manager");
            addButton.setMaximumSize(addButton.getPreferredSize());
            add(addButton);
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ManagerAddFrame frame = new ManagerAddFrame();
                    frame.setLocation(300, 300);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
            add(Box.createVerticalGlue());

            //add a panel with field to access a specific Manager
            ManagerAccessPanel accessPanel = new ManagerAccessPanel();
            add(accessPanel);
            add(Box.createVerticalGlue());

            //add a button to display all the students
            JButton listAllButton  = new JButton("List all");
            listAllButton.setMaximumSize(listAllButton.getPreferredSize());
            add(listAllButton);
            listAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            listAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null,
                            ManagerMapAccess.getInstance().values());
                }
            });

            add(Box.createVerticalGlue());

            //add a button to exit from the window containing this panel
            final JButton exitButton = new JButton("Exit");
            exitButton.setMaximumSize(exitButton.getSize());
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exitButton.getTopLevelAncestor().setVisible(false);
                }
            });
            add(Box.createVerticalGlue());
        }

    public static final long serialVersionUID = 1;
}
