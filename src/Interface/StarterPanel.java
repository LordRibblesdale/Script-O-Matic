package Interface;

import ControlCenter.Language;
import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class StarterPanel extends JPanel {
    private SpringLayout layout;
    private JPanel welcome;
    private JComboBox<Language> languages;
    private JButton next;

    private final String[] text = {
            "Welcome to Script'o'Matic.",
            "Choose a language to continue:"
    };

    private final Language[] locales = {
            Language.ENGLISH,
            Language.ITALIAN
    };

    private Language locale;

    private Controller controller;

    StarterPanel(Controller controller) {
        this.controller = controller;

        setLayout(layout = new SpringLayout());

        welcome = new JPanel(new GridLayout(0, 1));
        languages = new JComboBox<>(locales);
        next = new JButton("Next");
        locale = locales[0];

        for (String s : text) {
            welcome.add(new JLabel(s));
        }

        languages.addActionListener(e -> {
            if (e.getSource() instanceof JComboBox) {
                JComboBox<?> tmp = (JComboBox<?>) e.getSource();

                if (tmp.getSelectedItem() instanceof Language) {
                    locale = (Language) tmp.getSelectedItem();
                }
            }
        });

        next.addActionListener(e -> {
            System.out.println(locale.getLanguage());
            controller.setLanguage(ResourceBundle.getBundle("ResourceBundle", locale.getLanguage()));
            controller.askNextPage(PageChoice.FIRST);
            //languages.setEnabled(false);
        });

        add(welcome);
        add(languages);
        add(next);

        layout.putConstraint(SpringLayout.NORTH, welcome,
                5,
                SpringLayout.NORTH, StarterPanel.this);
        layout.putConstraint(SpringLayout.WEST, welcome,
                5,
                SpringLayout.WEST, StarterPanel.this);

        layout.putConstraint(SpringLayout.NORTH, languages,
                5,
                SpringLayout.SOUTH, welcome);
        layout.putConstraint(SpringLayout.WEST, languages,
                5,
                SpringLayout.WEST, StarterPanel.this);

        layout.putConstraint(SpringLayout.EAST, next,
                -5,
                SpringLayout.EAST, StarterPanel.this);
        layout.putConstraint(SpringLayout.SOUTH, next,
                -5,
                SpringLayout.SOUTH, StarterPanel.this);

    }
}
