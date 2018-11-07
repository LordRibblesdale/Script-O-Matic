package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableList extends JPanel {
    private JScrollPane scrollPane;
    private ProgramTable modelTable;
    private JTable table;
    private JButton back, next;
    private JButton add, remove, removeAll;
    private SpringLayout layout;

    private Controller controller;
    private String currentCard;

    TableList(Controller controller, String currentCard, Program[] exec) {
        this.controller = controller;
        this.currentCard = currentCard;

        setLayout(layout = new SpringLayout());

        back = new JButton(controller.getLanguage().getString("previousButton"));
        next = new JButton(controller.getLanguage().getString("nextButton"));
        next.setEnabled(false);

        add = new JButton(controller.getLanguage().getString("addEntry"));
        remove = new JButton(controller.getLanguage().getString("removeSelectedEntry"));
        removeAll = new JButton(controller.getLanguage().getString("removeAllEntries"));
        remove.setEnabled(false);
        removeAll.setEnabled(false);

        if (exec != null) {
            modelTable = new ProgramTable(controller, exec);
        } else {
            modelTable = new ProgramTable(controller);
        }

        table = new JTable(modelTable);
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(controller.getLanguage().getString("insertProgramTitle")));  //TODO finish here

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
        controller.askForRefresh();
    }

    public void disableNext() {
        next.setEnabled(false);
        remove.setEnabled(false);
        removeAll.setEnabled(false);
        controller.askForRefresh();
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
            controller.askPreviousPage(currentCard);
        });

        next.addActionListener(e -> {
            controller.askNextPage(currentCard);
        });

        add.addActionListener(e -> {
            new ProgramEditorWindow(controller);
        });

        remove.addActionListener(e -> {
            controller.processProgramDeletion();
        });

        removeAll.addActionListener(e -> {
            controller.processCleaningTable();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (scrollPane.contains(e.getPoint())) {
                    if (e.getClickCount() == 2) {
                        int index = controller.getUi().getTableList().getTable().getSelectedRow();

                        if (index != -1) {
                            Object[] data = controller.getUi().getTableList().getModelTable().getProgram(index);

                            new ProgramEditorWindow(
                                    controller,
                                    (String) data[2],
                                    (String) data[0],
                                    (String) data[3],
                                    (String) data[4],
                                    controller.getExecutables().get(index).hasDependencies()
                            );
                        }
                    }
                }
            }
        });
    }
}
