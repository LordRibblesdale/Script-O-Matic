package Interface;

import ControlCenter.Language;
import ControlCenter.Controller;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class StarterPanel extends JPanel {
    private SpringLayout layout;
    private JPanel welcome;
    private JComboBox<Language> languages;
    private JButton next;

    private final Language[] locales = {
            Language.ENGLISH,
            Language.ITALIAN
    };

    private Language locale;

    StarterPanel() {
        setLayout(layout = new SpringLayout());
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(Controller.getLanguageString("welcome"))));

        welcome = new JPanel(new GridLayout(0, 1));

        languages = new JComboBox<>(locales);
        next = new JButton("Next");
        locale = locales[0];

        languages.addActionListener(e -> {
            if (e.getSource() instanceof JComboBox<?> tmp) {
                if (tmp.getSelectedItem() instanceof Language) {
                    locale = (Language) tmp.getSelectedItem();
                }
            }
        });

        next.addActionListener(e -> {
            Controller.setLanguage(ResourceBundle.getBundle("ResourceBundle", locale.getLanguage()));
            Controller.askNextPage(PageChoice.FIRST);
            //languages.setEnabled(false);
        });

        add(welcome);
        add(languages);
        add(next);

        layout.putConstraint(SpringLayout.NORTH, languages,
                5,
                SpringLayout.NORTH, StarterPanel.this);
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
