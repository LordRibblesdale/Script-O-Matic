package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Script implements Serializable {
    private Program[] list;
    private Date date;

    public Script(ArrayList<Program> exec) {
        list = exec.toArray(new Program[0]);
        date = new Date();
    }
}
