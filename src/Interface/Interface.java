package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {
    private Controller controller;
    private CardLayout layout;

    private StarterPanel first;
    private MainMenu mainMenu = null;
    private TableList tableList = null;
    private CheckoutPanel checkoutPanel = null;

    public Interface() {
        super("Script'o'Matic");
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
            case PageChoice.MM_INSTALLER:
                tableList = new TableList(controller, PageChoice.MM_INSTALLER, null);
                add(tableList, PageChoice.MM_INSTALLER);
                break;
            case PageChoice.CHECKOUT:
                checkoutPanel = new CheckoutPanel(controller, true);
                add(checkoutPanel, PageChoice.CHECKOUT);
                break;
            case PageChoice.CHK_INSTALL:
                checkoutPanel = new CheckoutPanel(controller, false);
                add(checkoutPanel, PageChoice.CHK_INSTALL);
                break;
            case PageChoice.FINAL:
                JPanel p = new JPanel();
                p.add(new JLabel("TEMPORARY END"));
                add(p, PageChoice.FINAL);
                break;
        }
    }

    public void setUpFrame() {
        //pack();
        //setMinimumSize(getSize());
        repaint();
        validate();
    }

    public void enlargeWindow() {
        setSize(new Dimension(700, 700));
        setLocationRelativeTo(null);
    }

    @Override
    public CardLayout getLayout() {
        return layout;
    }

    public TableList getTableList() {
        return tableList;
    }

    public CheckoutPanel getCheckoutPanel() {
        return checkoutPanel;
    }
}
