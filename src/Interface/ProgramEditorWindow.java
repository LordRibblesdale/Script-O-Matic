package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ProgramEditorWindow extends JFrame {
    private JTextField fileField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField linkField;
    private JButton open;
    private JButton addExtras;
    private JButton back;
    private JButton save;
    private JCheckBox isAloneExec;
    private JLabel fileLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel linkLabel;
    private JLabel isAloneExecLabel;

    private Controller controller;

    private SpringLayout layout;

    ProgramEditorWindow(Controller controller) {
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        fileField = new JTextField(10);
        nameField = new JTextField(10);
        descriptionField = new JTextField(10);
        linkField = new JTextField(10);

        open = new JButton(controller.getLanguage().getString("openButton"));
        addExtras = new JButton(controller.getLanguage().getString("addExtrasButton"));
        back = new JButton(controller.getLanguage().getString("previousButton"));
        save = new JButton(controller.getLanguage().getString("saveButton"));

        isAloneExec = new JCheckBox();

        fileLabel = new JLabel(controller.getLanguage().getString("fileField"));
        nameLabel = new JLabel(controller.getLanguage().getString("nameField"));
        descriptionLabel = new JLabel(controller.getLanguage().getString("descriptionField"));
        linkLabel = new JLabel(controller.getLanguage().getString("linkField"));
        isAloneExecLabel = new JLabel(controller.getLanguage().getString("isAloneLabel"));

        add(fileField);
        add(nameField);
        add(descriptionField);
        add(linkField);
        add(open);
        add(addExtras);
        add(back);
        add(save);
        add(isAloneExec);
        add(fileLabel);
        add(nameLabel);
        add(descriptionLabel);
        add(linkLabel);
        add(isAloneExecLabel);

        setUpLayout();
        addAllListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(controller.getUi());
        setVisible(true);
    }

    private void setUpLayout() {
        layout.putConstraint(SpringLayout.NORTH, fileLabel,
                5,
                SpringLayout.NORTH, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, fileLabel,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, fileField,
                5,
                SpringLayout.SOUTH, fileLabel);
        layout.putConstraint(SpringLayout.WEST, fileField,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());

        layout.putConstraint(SpringLayout.WEST, open,
                5,
                SpringLayout.EAST, fileField);
        layout.putConstraint(SpringLayout.NORTH, open,
                0,
                SpringLayout.NORTH, fileField);

        layout.putConstraint(SpringLayout.NORTH, isAloneExecLabel,
                5,
                SpringLayout.SOUTH, fileField);
        layout.putConstraint(SpringLayout.WEST, isAloneExecLabel,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());

        layout.putConstraint(SpringLayout.WEST, isAloneExec,
                5,
                SpringLayout.EAST, isAloneExecLabel);
        layout.putConstraint(SpringLayout.NORTH, isAloneExec,
                0,
                SpringLayout.NORTH, isAloneExecLabel);

        layout.putConstraint(SpringLayout.WEST, nameLabel,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, nameLabel,
                5,
                SpringLayout.SOUTH, isAloneExecLabel);

        layout.putConstraint(SpringLayout.WEST, nameField,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, nameField,
                5,
                SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, descriptionLabel,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, descriptionLabel,
                5,
                SpringLayout.SOUTH, nameField);

        layout.putConstraint(SpringLayout.WEST, descriptionField,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, descriptionField,
                5,
                SpringLayout.SOUTH, descriptionLabel);

        layout.putConstraint(SpringLayout.WEST, linkLabel,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, linkLabel,
                5,
                SpringLayout.SOUTH, descriptionField);

        layout.putConstraint(SpringLayout.WEST, linkField,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, linkField,
                5,
                SpringLayout.SOUTH, linkLabel);

        layout.putConstraint(SpringLayout.WEST, addExtras,
                5,
                SpringLayout.WEST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, addExtras,
                5,
                SpringLayout.SOUTH, linkField);

        layout.putConstraint(SpringLayout.EAST, save,
                -5,
                SpringLayout.EAST, ProgramEditorWindow.this.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, save,
                -5,
                SpringLayout.SOUTH, ProgramEditorWindow.this.getContentPane());

        layout.putConstraint(SpringLayout.EAST, back,
                -5,
                SpringLayout.WEST, save);
        layout.putConstraint(SpringLayout.SOUTH, back,
                -5,
                SpringLayout.SOUTH, ProgramEditorWindow.this.getContentPane());
    }

    private void addAllListeners() {
        back.addActionListener(e -> {
            dispose();
        });

        save.addActionListener(e -> {
            try {
                controller.processProgramCreation(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        new URL(linkField.getText()),
                        new File(fileField.getText()),
                        isAloneExec.isSelected()
                ));
            } catch (MalformedURLException e1) {
                /*
                JOptionPane.showMessageDialog(ProgramEditorWindow.this,
                        controller.getLanguage().getString("urlException"),
                        controller.getLanguage().getString("exception"),
                        JOptionPane.ERROR_MESSAGE);
                        */

                controller.processProgramCreation(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        null,
                        new File(fileField.getText()),
                        isAloneExec.isSelected()
                ));
            } finally {
                dispose();
            }
        });

        open.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            int result = file.showOpenDialog(controller.getUi());

            if (result == JFileChooser.APPROVE_OPTION) {
                fileField.setText(file.getSelectedFile().getPath());
            }
        });
    }
}
