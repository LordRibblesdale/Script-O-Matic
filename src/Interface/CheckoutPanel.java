package Interface;

import ControlCenter.Controller;
import Data.Program;
import Data.Script;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class CheckoutPanel extends JPanel {
    private ArrayList<JLabel> programList;
    private JPanel programs;
    private JLabel intro;
    private JLabel confirm;
    private JTextField location;
    private JButton back;
    private JButton create;
    private JButton selectFolder;

    private File folder;

    private SpringLayout layout;

    private boolean isCreationMode;

    public CheckoutPanel(boolean isCreationMode) {
        this.isCreationMode = isCreationMode;

        setLayout(layout = new SpringLayout());

        programList = new ArrayList<>();
        programs = new JPanel(new GridLayout(0, 1));

        if (isCreationMode) {
            for (Program p : Controller.getExecutables()) {
                programList.add(new JLabel(p.toString()));
                programs.add(programList.get(programList.size()-1));
            }
        }

        intro = new JLabel(Controller.getLanguageString("listAllPrograms"));
        back = new JButton(Controller.getLanguageString("previousButton"));
        location = new JTextField(20);
        location.setEditable(false);

        if (isCreationMode) {
            confirm = new JLabel(Controller.getLanguageString("confirmCreation"));
            create = new JButton(Controller.getLanguageString("createButton"));
            create.setEnabled(false);
            selectFolder = new JButton(Controller.getLanguageString("openFolderButton"));

            add(confirm);
        } else {

            create = new JButton(Controller.getLanguageString("installButton"));
            selectFolder = new JButton(Controller.getLanguageString("openInstallFile"));
        }

        setUpLayout();
        addAllListeners();

        add(intro);
        add(programs);
        add(location);
        add(back);
        add(create);
        add(selectFolder);
    }

    private void setUpLayout() {
        layout.putConstraint(SpringLayout.NORTH, intro,
                5,
                SpringLayout.NORTH, CheckoutPanel.this);
        layout.putConstraint(SpringLayout.WEST, intro,
                5,
                SpringLayout.WEST, CheckoutPanel.this);

        layout.putConstraint(SpringLayout.NORTH, programs,
                5,
                SpringLayout.SOUTH, intro);
        layout.putConstraint(SpringLayout.WEST, programs,
                5,
                SpringLayout.WEST, CheckoutPanel.this);

        if (confirm != null) {
            layout.putConstraint(SpringLayout.SOUTH, confirm,
                    -5,
                    SpringLayout.NORTH, location);
            layout.putConstraint(SpringLayout.WEST, confirm,
                    5,
                    SpringLayout.WEST, CheckoutPanel.this);
        }

        layout.putConstraint(SpringLayout.EAST, create,
                -5,
                SpringLayout.EAST, CheckoutPanel.this);
        layout.putConstraint(SpringLayout.SOUTH, create,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);

        layout.putConstraint(SpringLayout.EAST, back,
                -5,
                SpringLayout.WEST, create);
        layout.putConstraint(SpringLayout.SOUTH, back,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);


        layout.putConstraint(SpringLayout.EAST, selectFolder,
                -5,
                SpringLayout.WEST, back);

        layout.putConstraint(SpringLayout.SOUTH, selectFolder,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);

        layout.putConstraint(SpringLayout.EAST, location,
                -5,
                SpringLayout.WEST, selectFolder);
        layout.putConstraint(SpringLayout.SOUTH, location,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);
        layout.putConstraint(SpringLayout.WEST, location,
                5,
                SpringLayout.WEST, CheckoutPanel.this);

    }

    private void addAllListeners() {
        back.addActionListener(e -> {
            if (isCreationMode) {
                Controller.askPreviousPage(PageChoice.CHECKOUT);
            } else {
                Controller.askPreviousPage(PageChoice.CHK_INSTALL);
            }
        });

        create.addActionListener(e -> {
            if (isCreationMode) {
                Controller.processFileScriptCreation(folder, PageChoice.CHECKOUT);
            } else {
                Controller.startInstallProcess();
            }
        });

        selectFolder.addActionListener(e -> {
            JFileChooser file = new JFileChooser();

            if (isCreationMode) {
                file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = file.showOpenDialog(Controller.getUI());

                if (result == JFileChooser.APPROVE_OPTION) {
                    folder = file.getSelectedFile();
                    location.setText(file.getSelectedFile().getPath());
                    create.setEnabled(true);
                }
            } else {
                file.setFileFilter(new FileNameExtensionFilter(Controller.getLanguageString("scriptFileExtension"), "som"));
                file.setAcceptAllFileFilterUsed(false);

                int result = file.showOpenDialog(Controller.getUI());

                if (result == JFileChooser.APPROVE_OPTION) {
                    folder = file.getSelectedFile();
                    location.setText(file.getSelectedFile().getPath());
                    create.setEnabled(true);

                    Controller.prepareProgramArray(folder);
                }
            }
        });
    }

    public void setLabels() {
        for (Program p : Controller.getExecutables()) {
            programList.add(new JLabel(p.toString()));
            programs.add(programList.get(programList.size()-1));
        }

        validate();
    }
}
