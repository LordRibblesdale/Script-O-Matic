package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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

    private Controller controller;

    private String list = "";

    public CheckoutPanel(Controller controller) {
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        programList = new ArrayList<>();
        programs = new JPanel(new GridLayout(0, 1));

        for (Program p : controller.getExecutables()) {
            programList.add(new JLabel(p.toString()));
            programs.add(programList.get(programList.size()-1));
        }

        intro = new JLabel(controller.getLanguage().getString("listAllPrograms"));
        confirm = new JLabel(controller.getLanguage().getString("confirmCreation"));
        location = new JTextField(20);
        location.setEditable(false);
        back = new JButton(controller.getLanguage().getString("previousButton"));
        create = new JButton(controller.getLanguage().getString("createButton"));
        create.setEnabled(false);
        selectFolder = new JButton(controller.getLanguage().getString("openFolderButton"));

        setUpLayout();
        addAllListeners();

        add(intro);
        add(programs);
        add(confirm);
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

        layout.putConstraint(SpringLayout.SOUTH, confirm,
                -5,
                SpringLayout.NORTH, location);
        layout.putConstraint(SpringLayout.WEST, confirm,
                5,
                SpringLayout.WEST, CheckoutPanel.this);

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

        layout.putConstraint(SpringLayout.WEST, back,
                5,
                SpringLayout.EAST, selectFolder);
        layout.putConstraint(SpringLayout.SOUTH, selectFolder,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);

        layout.putConstraint(SpringLayout.WEST, selectFolder,
                5,
                SpringLayout.EAST, location);
        layout.putConstraint(SpringLayout.SOUTH, location,
                -5,
                SpringLayout.SOUTH, CheckoutPanel.this);
        layout.putConstraint(SpringLayout.WEST, location,
                5,
                SpringLayout.WEST, CheckoutPanel.this);
    }

    private void addAllListeners() {
        back.addActionListener(e -> {
            controller.askPreviousPage(PageChoice.CHECKOUT);
        });

        create.addActionListener(e -> {
            controller.processFileScriptCreation(folder, PageChoice.CHECKOUT);
        });

        selectFolder.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = file.showOpenDialog(controller.getUi());

            if (result == JFileChooser.APPROVE_OPTION) {
                folder = file.getSelectedFile();
                location.setText(file.getSelectedFile().getPath());
                create.setEnabled(true);
            }
        });
    }
}
