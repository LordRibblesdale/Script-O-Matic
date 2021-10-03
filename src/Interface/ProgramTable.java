package Interface;

import ControlCenter.Controller;
import Data.Program;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProgramTable extends AbstractTableModel {
    private String[] columns;
    private ArrayList<Object[]> data;

    public ProgramTable() {

        columns = new String[] {
                Controller.getLanguageString("nameProgram"),
                Controller.getLanguageString("executableFile"),
                Controller.getLanguageString("absoluteLocation"),
                Controller.getLanguageString("descriptionProgram"),
                Controller.getLanguageString("urlProgram")
        };

        data = new ArrayList<>(1);
    }

    public ProgramTable(Program[] exec) {
        columns = new String[] {
                Controller.getLanguageString("nameProgram"),
                Controller.getLanguageString("executableFile"),
                Controller.getLanguageString("absoluteLocation"),
                Controller.getLanguageString("descriptionProgram"),
                Controller.getLanguageString("urlProgram")
        };

        data = new ArrayList<>(exec.length);

        for (Program p : exec) {
            data.add(new Object[] {
                    p.getName(),
                    p.getExecLocation().getPath(),
                    p.getExecLocation().getAbsolutePath(),
                    p.getDescription(),
                    p.getLink()
            });
        }
    }

    public void addProgram(Program exec) {
        data.add(new Object[] {
                exec.getName(),
                exec.getExecLocation().getName(),
                exec.getExecLocation().getAbsolutePath(),
                exec.getDescription(),
                exec.getLink()
        });

        fireTableDataChanged();;
    }

    public void editProgram(int index, Program exec) {
        data.set(index, new Object[] {
                exec.getName(),
                exec.getExecLocation().getName(),
                exec.getExecLocation().getAbsolutePath(),
                exec.getDescription(),
                exec.getLink()
        });

        fireTableDataChanged();
    }

    public void removeProgram(int index) {
        if (!data.isEmpty() && index != -1) {
            data.remove(index);
        }

        fireTableDataChanged();
    }

    public void removeAllPrograms() {
        data.clear();
        fireTableDataChanged();
    }

    public Object[] getProgram(int index) {
        return data.get(index);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
