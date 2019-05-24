package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private ArrayList<JButton> list;
    private JPanel panel;
    private JButton previous;

    private SpringLayout layout;

    private Controller controller;

    private String[] text;
    private String[] tipText;
    private String[] pages = {
            PageChoice.MM_INSTALLER,
            PageChoice.MM_LOAD,
            PageChoice.MM_EDIT
    };

    public MainMenu(Controller controller) {
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        text = new String[] {
                controller.getLanguage().getString("createScriptInstaller"),
                controller.getLanguage().getString("loadScript"),
                controller.getLanguage().getString("editScript")
        };

        tipText = new String[] {
                controller.getLanguage().getString("installerTip"),
        };

        list = new ArrayList<>(3);
        panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(controller.getLanguage().getString("chooseAction")
                )));

        for (String s : text) {
            list.add(new JButton(s));
            list.get(list.size()-1).addActionListener(e -> {

                controller.askNextPage(PageChoice.MAIN_MENU, pages[list.indexOf(e.getSource())]);
            });
            panel.add(list.get(list.size()-1));
        }

        previous = new JButton(controller.getLanguage().getString("previousButton"));

        previous.addActionListener(e -> {
            controller.askPreviousPage(PageChoice.MAIN_MENU);
        });

        add(panel);
        add(previous);

        layout.putConstraint(SpringLayout.NORTH, panel,
                5,
                SpringLayout.NORTH, MainMenu.this);
        layout.putConstraint(SpringLayout.WEST, panel,
                5,
                SpringLayout.WEST, MainMenu.this);

        layout.putConstraint(SpringLayout.WEST, previous,
                5,
                SpringLayout.WEST, MainMenu.this);
        layout.putConstraint(SpringLayout.SOUTH, previous,
                -5,
                SpringLayout.SOUTH, MainMenu.this);

        /*
        layout.putConstraint(SpringLayout.EAST, next,
                -5,
                SpringLayout.EAST, MainMenu.this);
        layout.putConstraint(SpringLayout.SOUTH, next,
                -5,
                SpringLayout.SOUTH, MainMenu.this);
        */
    }
}
