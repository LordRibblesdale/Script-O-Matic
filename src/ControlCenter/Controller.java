package ControlCenter;

import Data.Program;
import Data.Script;
import Interface.Interface;
import Interface.PageChoice;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private ResourceBundle language;

    private Interface ui;
    private String currentCard;
    private ArrayList<String> initialisedCards;
    private Stack<String> windows;

    private ArrayList<Program> executables;
    private String dataLocation;

    private int status;

    public Controller(Interface ui) {
        this.ui = ui;
        this.currentCard = PageChoice.FIRST;
        this.status = Status.AVAILABLE;
        this.initialisedCards = new ArrayList<>(1);
        this.windows = new Stack<>();
        this.executables = new ArrayList<>(1);

        initialisedCards.add(PageChoice.FIRST);
        windows.add(PageChoice.FIRST);
    }

    public void askNextPage(String fromCard) {
        if (currentCard.equals(fromCard)) {
            windows.add(fromCard);

            String nextCard = nextCard();

            ui.initialiseCard(nextCard);

            if (!initialisedCards.contains(nextCard)) {
                initialisedCards.add(nextCard);
            }

            ui.getLayout().show(ui.getContentPane(), nextCard);
            currentCard = nextCard;
        }
    }

    public void askNextPage(String fromCard, String choice) {
        if (currentCard.equals(fromCard)) {
            windows.add(fromCard);

            String nextCard = nextCard(choice);

            ui.initialiseCard(nextCard);

            if (!initialisedCards.contains(nextCard)) {
                initialisedCards.add(nextCard);
            }

            ui.getLayout().show(ui.getContentPane(), nextCard);
            askForLargerWindow();
            currentCard = nextCard;
        }
    }

    public void askPreviousPage(String fromCard) {
        if (currentCard.equals(fromCard)) {
            if (status != Status.BUSY) {
                String previousCard = previousCard();
                ui.getLayout().show(ui.getContentPane(), previousCard);
                currentCard = previousCard;
            }
        }
    }

    public void processProgramCreation(Program exec) {
        executables.add(exec);

        ui.getTableList().getModelTable().addProgram(exec);
        ui.getTableList().getModelTable().fireTableDataChanged();
    }

    public void processProgramModify(Program exec) {
        int index = getUi().getTableList().getTable().getSelectedRow();
        executables.set(index, exec);
        ui.getTableList().getModelTable().editProgram(index, exec);
        ui.getTableList().getModelTable().fireTableDataChanged();
    }

    public void processProgramDeletion() {
        if (!executables.isEmpty()) {
            int index = ui.getTableList().getTable().getSelectedRow();

            if (index != -1) {
                ui.getTableList().getModelTable().removeProgram(index);
                ui.getTableList().getModelTable().fireTableDataChanged();

                executables.remove(index);
                if (executables.isEmpty()) {
                    ui.getTableList().disableNext();
                }
            }
        }
    }

    public void processCleaningTable() {
        executables.clear();
        ui.getTableList().getModelTable().removeAllPrograms();
        ui.getTableList().getModelTable().fireTableDataChanged();
        ui.getTableList().disableNext();
    }

    public void processFileScriptCreation(File folder, String fromCard) {
        Script script = null;
        ObjectOutputStream out = null;
        File data = new File(folder.getPath() + File.separator + "DATA");
        int result = -1;

        status = Status.BUSY;

        try {
            data.mkdir();

            for (Program p : executables) {
                //FileOutputStream fileOutputStream;

                if (p.hasDependencies()) {
                    FileUtils.copyDirectoryToDirectory(p.getExecLocation().toPath().getParent().toFile(),
                            data);

                    p.setExecLocation(File.pathSeparator
                            + "DATA"
                            + File.separator
                            + p.getExecLocation().toPath().getParent().toFile().getName()
                            + p.getExecLocation().getName());
                } else {
                    if (!p.getExecLocation().isDirectory()) {
                        FileUtils.copyFile(p.getExecLocation().toPath().toFile(),
                                new File(data + File.separator + p.getExecLocation().getName()));
                    } else {
                        FileUtils.copyDirectory(p.getExecLocation().toPath().toFile(),
                                new File(data + File.separator + p.getExecLocation().getName()));
                    }

                    p.setExecLocation("DATA" + File.separator + p.getExecLocation().getName());
                }

            }

            script = new Script(executables);

            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(folder + File.separator + new SimpleDateFormat("yyyy-MM-dd HH-mm").format(new Date()) + ".som")));
            out.writeObject(script);
            out.close();

            result = Result.COMPLETE;
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.ERROR;
        }

        if (result == Result.COMPLETE) {
            askNextPage(fromCard);
        }

        status = Status.AVAILABLE;
    }

    public void prepareProgramArray(File somFile) {
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(somFile)));
            Script som;
            Object script = in.readObject();
            in.close();

            if (script instanceof Script) {
                som = (Script) script;

                for (Program p : som.getList()) {
                    executables.add(p);
                    p.setExecLocation(somFile.getParent() + File.separator + p.getExecLocation());
                }

                ui.getCheckoutPanel().setLabels();
            } else {
                //TODO add here
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }

    }

    public void startInstallProcess() {
        for (Program p : executables) {
            try {
                if (p.getExecLocation().isDirectory()) {
                    for (File folderExe : Objects.requireNonNull(p.getExecLocation().listFiles())) {
                        if (!folderExe.isDirectory()) {
                            Process process = Runtime.getRuntime().exec(folderExe.getAbsolutePath());
                            try {
                                process.waitFor();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Process process = Runtime.getRuntime().exec(p.getExecLocation().getAbsolutePath());
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void askForRefresh() {
        ui.setUpFrame();
    }

    public void askForLargerWindow() {
        ui.enlargeWindow();
    }

    public ResourceBundle getLanguage() {
        return language;
    }

    public Interface getUi() {
        return ui;
    }

    public ArrayList<Program> getExecutables() {
        return executables;
    }

    public void setLanguage(ResourceBundle language) {
        this.language = language;
    }

    private String nextCard() {
        String nextCard = null;

        switch (currentCard) {
            case PageChoice.FIRST:
                nextCard = PageChoice.MAIN_MENU;
                break;
            case PageChoice.MM_INSTALLER:
                nextCard = PageChoice.CHECKOUT;
                break;
            case PageChoice.MM_LOAD:
                nextCard = PageChoice.CHK_INSTALL;
                break;
            case PageChoice.CHECKOUT:
            case PageChoice.CHK_INSTALL:
                nextCard = PageChoice.FINAL;
                break;

        }

        return nextCard;
    }

    private String nextCard(String choice) {
        String nextCard = null;

        switch (currentCard) {
            case PageChoice.MAIN_MENU:
                switch (choice) {
                    case PageChoice.MM_INSTALLER:
                    case PageChoice.MM_EDIT:
                        nextCard = choice;
                        break;
                    case PageChoice.MM_LOAD:
                        nextCard = PageChoice.CHK_INSTALL;
                        break;
                }
                break;
        }

        return nextCard;
    }

    private String previousCard() {
        return windows.pop();
    }
}
