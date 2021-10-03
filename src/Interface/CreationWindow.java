package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;

public class CreationWindow extends JDialog {
    private JButton abort;
    private JLabel fileLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel linkLabel;
    private JLabel isAloneExecLabel;

    private Controller controller;

    private SpringLayout layout;

    CreationWindow(Controller controller) {
        super(Controller.getUI(), Controller.getLanguageString("programWindow"), true);
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        fileLabel = new JLabel(Controller.getLanguageString("fileField"));
        nameLabel = new JLabel(Controller.getLanguageString("nameField"));
        descriptionLabel = new JLabel(Controller.getLanguageString("descriptionField"));
        linkLabel = new JLabel(Controller.getLanguageString("linkField"));
        isAloneExecLabel = new JLabel(Controller.getLanguageString("isAloneLabel"));

        add(fileLabel);
        add(nameLabel);
        add(descriptionLabel);
        add(linkLabel);
        add(isAloneExecLabel);

        setUpLayout();
        addAllListeners();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        pack();
        setLocationRelativeTo(Controller.getUI());
        setVisible(true);
    }

    private void setUpLayout() {
        layout.putConstraint(SpringLayout.NORTH, fileLabel,
                5,
                SpringLayout.NORTH, CreationWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, fileLabel,
                5,
                SpringLayout.WEST, CreationWindow.this.getContentPane());


        layout.putConstraint(SpringLayout.WEST, nameLabel,
                5,
                SpringLayout.WEST, CreationWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, nameLabel,
                5,
                SpringLayout.SOUTH, isAloneExecLabel);

    }

    private void addAllListeners() {

    }
}
