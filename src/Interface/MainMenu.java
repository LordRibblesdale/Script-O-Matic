package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private ArrayList<JRadioButton> list;
    private ButtonGroup bg;
    private JPanel panel;
    private JButton next;
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
        bg = new ButtonGroup();
        panel = new JPanel(new GridLayout(0, 1));

        for (String s : text) {
            list.add(new JRadioButton(s));
            bg.add(list.get(list.size()-1));
            panel.add(list.get(list.size()-1));
        }

        list.get(0).setSelected(true);

        next = new JButton(controller.getLanguage().getString("nextButton"));
        previous = new JButton(controller.getLanguage().getString("previousButton"));

        next.addActionListener(e -> {
            for (int i = 0; i < text.length; i++) {
                if (list.get(i).isSelected()) {
                    controller.askNextPage(PageChoice.MAIN_MENU, pages[i]);
                }
            }
        });

        previous.addActionListener(e -> {
            controller.askPreviousPage(PageChoice.MAIN_MENU);
        });

        add(panel);
        add(previous);
        add(next);

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

        layout.putConstraint(SpringLayout.EAST, next,
                -5,
                SpringLayout.EAST, MainMenu.this);
        layout.putConstraint(SpringLayout.SOUTH, next,
                -5,
                SpringLayout.SOUTH, MainMenu.this);
    }
}
