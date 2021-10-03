package Interface;

import ControlCenter.Controller;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {
    private Controller controller;
    private CardLayout layout;

    private JPanel frame;

    private StarterPanel first;
    private MainMenu mainMenu = null;
    private TableList tableList = null;
    private CheckoutPanel checkoutPanel = null;

    public Interface() {
        super("Script'o'Matic");

        frame = new JPanel(layout = new CardLayout());
        frame.setBorder(BorderFactory.createEmptyBorder());

        controller = new Controller(Interface.this);

        first = new StarterPanel(controller);

        frame.add(first, PageChoice.FIRST);

        add(frame);

        setMinimumSize(new Dimension(250, 250));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //TODO add warning
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initialiseCard(String card) {
        switch (card) {
            case PageChoice.MAIN_MENU -> {
                mainMenu = new MainMenu(controller);
                frame.add(mainMenu, PageChoice.MAIN_MENU);
            }
            case PageChoice.MM_INSTALLER -> {
                tableList = new TableList(controller, PageChoice.MM_INSTALLER, null);
                frame.add(tableList, PageChoice.MM_INSTALLER);
            }
            case PageChoice.CHECKOUT -> {
                checkoutPanel = new CheckoutPanel(controller, true);
                frame.add(checkoutPanel, PageChoice.CHECKOUT);
            }
            case PageChoice.CHK_INSTALL -> {
                checkoutPanel = new CheckoutPanel(controller, false);
                frame.add(checkoutPanel, PageChoice.CHK_INSTALL);
            }
            case PageChoice.FINAL -> {
                JPanel p = new JPanel();
                p.add(new JLabel("TEMPORARY END"));
                frame.add(p, PageChoice.FINAL);
            }
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

    public JPanel getFrame() {
        return frame;
    }
}
