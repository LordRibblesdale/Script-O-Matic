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

    private String[] text;
    private String[] tipText;
    private String[] pages = {
            PageChoice.MM_INSTALLER,
            PageChoice.MM_LOAD,
            PageChoice.MM_EDIT
    };

    public MainMenu() {
        setLayout(layout = new SpringLayout());

        text = new String[] {
                Controller.getLanguageString("createScriptInstaller"),
                Controller.getLanguageString("loadScript"),
                Controller.getLanguageString("editScript")
        };

        tipText = new String[] {
                Controller.getLanguageString("installerTip"),
        };

        list = new ArrayList<>(3);
        panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(Controller.getLanguageString("chooseAction")
                )));

        for (String s : text) {
            list.add(new JButton(s));
            list.get(list.size()-1).addActionListener(e ->
                    Controller.askNextPage(PageChoice.MAIN_MENU, pages[list.indexOf(e.getSource())])
            );
            panel.add(list.get(list.size()-1));
        }

        previous = new JButton(Controller.getLanguageString("previousButton"));

        previous.addActionListener(e -> Controller.askPreviousPage(PageChoice.MAIN_MENU));

        add(panel);
        add(previous);

        layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, MainMenu.this);
        layout.putConstraint(SpringLayout.WEST, panel, 5, SpringLayout.WEST, MainMenu.this);

        layout.putConstraint(SpringLayout.WEST, previous, 5, SpringLayout.WEST, MainMenu.this);
        layout.putConstraint(SpringLayout.SOUTH, previous, -5, SpringLayout.SOUTH, MainMenu.this);
    }
}
