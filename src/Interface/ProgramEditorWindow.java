package Interface;

import ControlCenter.Controller;
import ControlCenter.Status;
import Data.Program;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ProgramEditorWindow extends JDialog {
    private JTextField fileField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField linkField;
    private JButton openFile;
    private JButton openFolder;
    private JButton addExtras;
    private JButton back;
    private JButton save;
    private JCheckBox isAloneExec;
    private JLabel fileLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel linkLabel;
    private JLabel isAloneExecLabel;

    private int status;

    private SpringLayout layout;

    ProgramEditorWindow() {
        super(Controller.getUI(), Controller.getLanguageString("programWindow"), true);
        this.status = Status.CREATING;

        setLayout(layout = new SpringLayout());

        fileField = new JTextField(25);
        fileField.setEditable(false);

        nameField = new JTextField(15);
        descriptionField = new JTextField(20);
        linkField = new JTextField(15);

        openFile = new JButton(Controller.getLanguageString("openButton"));
        openFolder = new JButton(Controller.getLanguageString("openFolderButton"));
        addExtras = new JButton(Controller.getLanguageString("addExtrasButton"));
        back = new JButton(Controller.getLanguageString("discardButton"));
        save = new JButton(Controller.getLanguageString("saveButton"));

        isAloneExec = new JCheckBox();

        fileLabel = new JLabel(Controller.getLanguageString("fileField"));
        nameLabel = new JLabel(Controller.getLanguageString("nameField"));
        descriptionLabel = new JLabel(Controller.getLanguageString("descriptionField"));
        linkLabel = new JLabel(Controller.getLanguageString("linkField"));
        isAloneExecLabel = new JLabel(Controller.getLanguageString("isAloneLabel"));

        add(fileField);
        add(nameField);
        add(descriptionField);
        add(linkField);
        add(openFile);
        add(openFolder);
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
        pack();
        setLocationRelativeTo(Controller.getUI());
        setVisible(true);
    }

    ProgramEditorWindow(String location, String pName, String pDescription,
                        String pLink, boolean hasDependencies) {
        super(Controller.getUI(), Controller.getLanguageString("programWindow"), true);
        this.status = Status.EDITING;

        setLayout(layout = new SpringLayout());

        fileField = new JTextField(location, 25);
        fileField.setEditable(false);

        nameField = new JTextField(pName, 15);
        descriptionField = new JTextField(pDescription, 20);
        linkField = new JTextField(pLink, 15);

        openFile = new JButton(Controller.getLanguageString("openButton"));
        openFolder = new JButton(Controller.getLanguageString("openFolderButton"));
        addExtras = new JButton(Controller.getLanguageString("addExtrasButton"));
        back = new JButton(Controller.getLanguageString("discardButton"));
        save = new JButton(Controller.getLanguageString("saveButton"));

        isAloneExec = new JCheckBox();
        isAloneExec.setSelected(hasDependencies);

        fileLabel = new JLabel(Controller.getLanguageString("fileField"));
        nameLabel = new JLabel(Controller.getLanguageString("nameField"));
        descriptionLabel = new JLabel(Controller.getLanguageString("descriptionField"));
        linkLabel = new JLabel(Controller.getLanguageString("linkField"));
        isAloneExecLabel = new JLabel(Controller.getLanguageString("isAloneLabel"));

        add(fileField);
        add(nameField);
        add(descriptionField);
        add(linkField);
        add(openFile);
        add(openFolder);
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
        pack();
        setLocationRelativeTo(Controller.getUI());
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

        layout.putConstraint(SpringLayout.WEST, openFile,
                5,
                SpringLayout.EAST, fileField);
        layout.putConstraint(SpringLayout.NORTH, openFile,
                0,
                SpringLayout.NORTH, fileField);

        layout.putConstraint(SpringLayout.WEST, openFolder,
                5,
                SpringLayout.EAST, openFile);
        layout.putConstraint(SpringLayout.NORTH, openFolder,
                0,
                SpringLayout.NORTH, openFile);

        layout.putConstraint(SpringLayout.NORTH, isAloneExecLabel,
                10,
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
            if (!fileField.getText().equals("") && fileField.getText() != null &&
                    !nameField.getText().equals("") && nameField.getText() != null) {
                File file = new File(fileField.getText());

                if (file.exists()) {
                    createProgramInstance(file);
                } else {
                    JOptionPane.showMessageDialog(ProgramEditorWindow.this,
                            Controller.getLanguageString("fileNotExists"),
                            Controller.getLanguageString("exception"),
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(ProgramEditorWindow.this,
                        Objects.equals(fileField.getText(), "") ?
                                Controller.getLanguageString("fileNameEmpty") :
                                Controller.getLanguageString("programNameEmpty"),
                        Controller.getLanguageString("exception"),
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        openFile.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            file.setFileFilter(new FileNameExtensionFilter(
                    Controller.getLanguageString("msExecutables"), "exe"));

            int result = file.showOpenDialog(Controller.getUI());

            if (result == JFileChooser.APPROVE_OPTION) {
                fileField.setText(file.getSelectedFile().getPath());
            }
        });

        openFolder.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = file.showOpenDialog(Controller.getUI());

            if (result == JFileChooser.APPROVE_OPTION) {
                fileField.setText(file.getSelectedFile().getPath());
            }
        });
    }

    private void createProgramInstance(File file) {
        if (status == Status.CREATING) {
            try {
                Controller.processProgramCreation(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        !linkField.getText().equals("") ? new URL(linkField.getText()) : null,
                        file,
                        isAloneExec.isSelected()
                ));
            } catch (MalformedURLException e1) {
                JOptionPane.showMessageDialog(ProgramEditorWindow.this,
                        Controller.getLanguageString("urlException"),
                        Controller.getLanguageString("exception"),
                        JOptionPane.INFORMATION_MESSAGE);
                Controller.processProgramCreation(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        null,
                        file,
                        isAloneExec.isSelected()
                ));
            } finally {
                dispose();

                Interface.enableNext(); //TODO fix here
            }
        } else if (status == Status.EDITING) {
            try {
                Controller.processProgramModify(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        !linkField.getText().equals("") ? new URL(linkField.getText()) : null,
                        file,
                        isAloneExec.isSelected()
                ));
            } catch (MalformedURLException e1) {
                JOptionPane.showMessageDialog(ProgramEditorWindow.this,
                        Controller.getLanguageString("urlException"),
                        Controller.getLanguageString("exception"),
                        JOptionPane.INFORMATION_MESSAGE);
                Controller.processProgramModify(new Program(
                        nameField.getText(),
                        descriptionField.getText(),
                        null,
                        file,
                        isAloneExec.isSelected()
                ));
            } finally {
                dispose();

                Interface.enableNext(); //TODO fix here
            }

        }
    }
}
