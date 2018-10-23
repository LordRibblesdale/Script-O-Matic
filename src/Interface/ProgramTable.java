package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.table.AbstractTableModel;

public class ProgramTable extends AbstractTableModel {
    private String[] columns;
    private Object[][] data;

    private Controller controller;

    ProgramTable(Controller controller) {
        this.controller = controller;   //TODO is this useful?

        columns = new String[] {
                controller.getLanguage().getString("nameProgram"),
                controller.getLanguage().getString("executableFile"),
                controller.getLanguage().getString("absoluteLocation"),
                controller.getLanguage().getString("descriptionProgram"),
                controller.getLanguage().getString("urlProgram")
        };

        data = new Object[1][columns.length];


    }

    ProgramTable(Controller controller, Program[] exec) {
        this.controller = controller;   //TODO is this useful?

        columns = new String[] {
                controller.getLanguage().getString("nameProgram"),
                controller.getLanguage().getString("executableFile"),
                controller.getLanguage().getString("absoluteLocation"),
                controller.getLanguage().getString("descriptionProgram"),
                controller.getLanguage().getString("urlProgram")
        };

        int i = 0;

        data = new Object[exec.length][columns.length];

        for (Program p : exec) {
            data[i][0] = p.getName();
            data[i][1] = p.getExecLocation();
            data[i][2] = p.getExecLocation().getAbsolutePath();
            data[i][3] = p.getDescription();
            data[i++][3] = p.getLink();
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
