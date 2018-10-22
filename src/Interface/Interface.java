package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {
    private Controller controller;
    private CardLayout layout;

    private StarterPanel first;
    private MainMenu mainMenu = null;

    public Interface() {
        setLayout(layout = new CardLayout());

        controller = new Controller(Interface.this);

        first = new StarterPanel(controller);

        add(first, PageChoice.FIRST);

        setMinimumSize(new Dimension(250, 250));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //TODO add warning
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initialiseCard(String card) {
        switch (card) {
            case PageChoice.MAIN_MENU:
                mainMenu = new MainMenu(controller);
                add(mainMenu, PageChoice.MAIN_MENU);
                break;
        }
    }

    public void setUpFrame() {
        //pack();
        //setMinimumSize(getSize());
        validate(); //??
    }

    @Override
    public CardLayout getLayout() {
        return layout;
    }
}
