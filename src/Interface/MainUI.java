package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    private static CardLayout layout;

    private static JPanel frame;

    private StarterPanel first;
    private static MainMenu mainMenu = null;
    private static TableList tableList = null;
    private static CheckoutPanel checkoutPanel = null;

    public MainUI() {
        super("Script'o'Matic");

        frame = new JPanel(layout = new CardLayout());
        frame.setBorder(BorderFactory.createEmptyBorder());

        first = new StarterPanel();

        frame.add(first, PageChoice.FIRST);

        add(frame);

        setMinimumSize(new Dimension(250, 250));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //TODO add warning
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void initialiseCard(String card) {
        switch (card) {
            case PageChoice.MAIN_MENU -> {
                mainMenu = new MainMenu();
                frame.add(mainMenu, PageChoice.MAIN_MENU);
            }
            case PageChoice.MM_INSTALLER -> {
                tableList = new TableList(PageChoice.MM_INSTALLER, null);
                frame.add(tableList, PageChoice.MM_INSTALLER);
            }
            case PageChoice.CHECKOUT -> {
                checkoutPanel = new CheckoutPanel(true);
                frame.add(checkoutPanel, PageChoice.CHECKOUT);
            }
            case PageChoice.CHK_INSTALL -> {
                checkoutPanel = new CheckoutPanel(false);
                frame.add(checkoutPanel, PageChoice.CHK_INSTALL);
            }
            case PageChoice.FINAL -> {
                JPanel p = new JPanel();
                p.add(new JLabel("TEMPORARY END"));
                frame.add(p, PageChoice.FINAL);
            }
        }
    }

    public static void showLayoutCard(String nextCard) {
        layout.show(frame, nextCard);
    }

    public static void addProgramToTable(Program exec) {
        tableList.getModelTable().addProgram(exec);
    }

    public static void editProgramInsideTable(int index, Program exec) {
        tableList.getModelTable().editProgram(index, exec);
    }

    public static void removeProgramFromTable(int index) {
        tableList.getModelTable().removeProgram(index);
    }

    public static void removeAllProgramsFromTable() {
        tableList.getModelTable().removeAllPrograms();
    }

    public static int getTableSelectedRow() {
        return tableList.getTable().getSelectedRow();
    }

    public static void enableNext() {
        tableList.enableNext();
    }

    public static void disableNext() {
        tableList.disableNext();
    }

    public static Object[] getProgramFromTable(int index) {
        return tableList.getModelTable().getProgram(index);
    }

    public void setUpFrame() {
        repaint();
        validate();
    }

    public void enlargeWindow() {
        setSize(new Dimension(700, 700));
        setLocationRelativeTo(null);
    }

    public CheckoutPanel getCheckoutPanel() {
        return checkoutPanel;
    }
}
