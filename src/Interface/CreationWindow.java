package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

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
        super(controller.getUi(), controller.getLanguage().getString("programWindow"), true);
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        fileLabel = new JLabel(controller.getLanguage().getString("fileField"));
        nameLabel = new JLabel(controller.getLanguage().getString("nameField"));
        descriptionLabel = new JLabel(controller.getLanguage().getString("descriptionField"));
        linkLabel = new JLabel(controller.getLanguage().getString("linkField"));
        isAloneExecLabel = new JLabel(controller.getLanguage().getString("isAloneLabel"));

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
        setLocationRelativeTo(controller.getUi());
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
