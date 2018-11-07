package ControlCenter;

import Data.Program;
import Data.Script;
import Interface.Interface;
import Interface.PageChoice;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.ResourceBundle;

public class Controller {
    private ResourceBundle language;

    private Interface ui;
    private String currentCard;
    private ArrayList<String> initialisedCards;
    private Stack<String> windows;

    private ArrayList<Program> executables;

    private int status;

    public Controller(Interface ui) {
        this.ui = ui;
        this.currentCard = PageChoice.FIRST;
        this.status = Status.AVAILABLE;
        this.initialisedCards = new ArrayList<>(1);
        this.windows = new Stack<>();

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
        if (executables == null) {
            executables = new ArrayList<>(1);
        }

        executables.add(exec);

        ui.getTableList().getModelTable().addProgram(exec);
        ui.getTableList().getModelTable().fireTableDataChanged();
        //askForRefresh();
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
            data.createNewFile();

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
                    FileUtils.copyFile(p.getExecLocation().toPath().toFile(),
                            new File(data + File.separator + p.getExecLocation().getName()));

                    p.setExecLocation(File.pathSeparator + "DATA" + File.separator + p.getExecLocation().getName());
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

    public void startInstallProcess(String dataLocation, Program[] exe) {

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
            case PageChoice.MM_LOAD:
                nextCard = PageChoice.CHECKOUT;
                break;
            case PageChoice.CHECKOUT:
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
                    case PageChoice.MM_LOAD:
                        nextCard = choice;
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
