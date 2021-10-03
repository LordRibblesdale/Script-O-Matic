package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableList extends JPanel {
    private JScrollPane scrollPane;
    private ProgramTable modelTable;
    private JTable table;
    private JButton back, next;
    private JButton add, remove, removeAll;
    private SpringLayout layout;

    private String currentCard;

    TableList(String currentCard, Program[] exec) {
        this.currentCard = currentCard;

        setLayout(layout = new SpringLayout());

        back = new JButton(Controller.getLanguageString("previousButton"));
        next = new JButton(Controller.getLanguageString("nextButton"));
        next.setEnabled(false);

        add = new JButton(Controller.getLanguageString("addEntry"));
        remove = new JButton(Controller.getLanguageString("removeSelectedEntry"));
        removeAll = new JButton(Controller.getLanguageString("removeAllEntries"));
        remove.setEnabled(false);
        removeAll.setEnabled(false);

        if (exec != null) {
            modelTable = new ProgramTable(exec);
        } else {
            modelTable = new ProgramTable();
        }

        table = new JTable(modelTable);
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(Controller.getLanguageString("insertProgramTitle")));  //TODO finish here

        add(scrollPane);
        add(back);
        add(next);
        add(add);
        add(remove);
        add(removeAll);

        setUpLayout();
        addAllListeners();
    }

    public ProgramTable getModelTable() {
        return modelTable;
    }

    public JTable getTable() {
        return table;
    }

    void enableNext() {
        next.setEnabled(true);
        remove.setEnabled(true);
        removeAll.setEnabled(true);
        Controller.askForRefresh();
    }

    public void disableNext() {
        next.setEnabled(false);
        remove.setEnabled(false);
        removeAll.setEnabled(false);
        Controller.askForRefresh();
    }

    private void setUpLayout() {
        layout.putConstraint(SpringLayout.NORTH, scrollPane,
                5,
                SpringLayout.NORTH, TableList.this);
        layout.putConstraint(SpringLayout.WEST, scrollPane,
                5,
                SpringLayout.WEST, TableList.this);
        layout.putConstraint(SpringLayout.EAST, scrollPane,
                -5,
                SpringLayout.EAST, TableList.this);

        layout.putConstraint(SpringLayout.SOUTH, back,
                -5,
                SpringLayout.SOUTH, TableList.this);
        layout.putConstraint(SpringLayout.WEST, back,
                5,
                SpringLayout.WEST, TableList.this);

        layout.putConstraint(SpringLayout.EAST, next,
                -5,
                SpringLayout.EAST, TableList.this);
        layout.putConstraint(SpringLayout.SOUTH, next,
                -5,
                SpringLayout.SOUTH, TableList.this);

        layout.putConstraint(SpringLayout.SOUTH, remove,
                -5,
                SpringLayout.NORTH, next);
        layout.putConstraint(SpringLayout.EAST, remove,
                -5,
                SpringLayout.EAST, TableList.this);

        layout.putConstraint(SpringLayout.EAST, removeAll,
                -5,
                SpringLayout.WEST, remove);
        layout.putConstraint(SpringLayout.SOUTH, removeAll,
                -5,
                SpringLayout.NORTH, next);

        layout.putConstraint(SpringLayout.EAST, add,
                -5,
                SpringLayout.WEST, removeAll);
        layout.putConstraint(SpringLayout.SOUTH, add,
                -5,
                SpringLayout.NORTH, next);
    }

    private void addAllListeners() {
        back.addActionListener(e -> {
            Controller.askPreviousPage(currentCard);
        });

        next.addActionListener(e -> {
            Controller.askNextPage(currentCard);
        });

        add.addActionListener(e -> {
            new ProgramEditorWindow();
        });

        remove.addActionListener(e -> {
            Controller.processProgramDeletion();
        });

        removeAll.addActionListener(e -> {
            Controller.processCleaningTable();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (scrollPane.contains(e.getPoint())) {
                    if (e.getClickCount() == 2) {
                        int index = Interface.getTableSelectedRow();

                        if (index != -1) {
                            // TODO move object collection methods to Controller instead of Interface (UI should not do this)
                            Object[] data = Interface.getProgramFromTable(index);

                            new ProgramEditorWindow(
                                    (String) data[2],
                                    (String) data[0],
                                    (String) data[3],
                                    (String) data[4],
                                    Controller.getExecutableFromList(index).hasDependencies()
                            );
                        }
                    }
                }
            }
        });
    }
}
