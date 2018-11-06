package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Script implements Serializable {
    private static final long serialVersionUID = 1L;
    private Program[] list;
    private Date date;

    public Script(ArrayList<Program> exec) {
        this.list = exec.toArray(new Program[0]);
        this.date = new Date();
    }

    public Program[] getList() {
        return list;
    }

    public Date getDate() {
        return date;
    }
}
