package ControlCenter;

import Data.Program;
import Data.Script;
import Interface.MainUI;
import Interface.PageChoice;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private static MainUI ui;

    private static ResourceBundle language;

    private static String currentCard;
    private static ArrayList<String> initialisedCards;
    private static Stack<String> windows;

    private static ArrayList<Program> executables;
    private String dataLocation;

    private static int status;

    public static void initialise() {
        currentCard = PageChoice.FIRST;
        status = Status.AVAILABLE;
        initialisedCards = new ArrayList<>(1);
        windows = new Stack<>();
        executables = new ArrayList<>(1);
        language = ResourceBundle.getBundle("ResourceBundle", Language.ENGLISH.getLanguage());
        ui = new MainUI();

        initialisedCards.add(PageChoice.FIRST);
        windows.add(PageChoice.FIRST);
    }

    public static void askNextPage(String fromCard) {
        if (currentCard.equals(fromCard)) {
            windows.add(fromCard);

            String nextCard = nextCard();

            MainUI.initialiseCard(nextCard);

            if (!initialisedCards.contains(nextCard)) {
                initialisedCards.add(nextCard);
            }

            MainUI.showLayoutCard(nextCard);
            currentCard = nextCard;
        }
    }

    public static void setLanguage(ResourceBundle resourceBundle) {
        language = resourceBundle;
    }

    public static void askNextPage(String fromCard, String choice) {
        if (currentCard.equals(fromCard)) {
            windows.add(fromCard);

            String nextCard = nextCard(choice);

            MainUI.initialiseCard(nextCard);

            if (!initialisedCards.contains(nextCard)) {
                initialisedCards.add(nextCard);
            }

            MainUI.showLayoutCard(nextCard);
            askForLargerWindow();
            currentCard = nextCard;
        }
    }

    public static void askPreviousPage(String fromCard) {
        if (currentCard.equals(fromCard)) {
            if (status != Status.BUSY) {
                String previousCard = previousCard();
                MainUI.showLayoutCard(previousCard);
                currentCard = previousCard;
            }
        }
    }

    public static void processProgramCreation(Program exec) {
        executables.add(exec);
        MainUI.addProgramToTable(exec);
    }

    public static void processProgramModify(Program exec) {
        int index = MainUI.getTableSelectedRow();
        executables.set(index, exec);
        MainUI.editProgramInsideTable(index, exec);
    }

    public static void processProgramDeletion() {
        if (!executables.isEmpty()) {
            int index = MainUI.getTableSelectedRow();

            if (index != -1) {
                MainUI.removeProgramFromTable(index);

                executables.remove(index);
                if (executables.isEmpty()) {
                    MainUI.disableNext();
                }
            }
        }
    }

    public static void processCleaningTable() {
        executables.clear();
        MainUI.removeAllProgramsFromTable();
        MainUI.disableNext();
    }

    public static void processFileScriptCreation(File folder, String fromCard) {
        Script script;
        ObjectOutputStream out;
        File data = new File(folder.getPath() + File.separator + "DATA");
        int result;

        status = Status.BUSY;

        try {
            if (!data.mkdir()) {
                System.err.printf("ERROR: Cannot create folder in %s", data.getPath());
                return;
            }

            for (Program p : executables) {
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

    public static void prepareProgramArray(File somFile) {
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

    public static void startInstallProcess() {
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

    public static void askForRefresh() {
        ui.setUpFrame();
    }

    public static void askForLargerWindow() {
        ui.enlargeWindow();
    }

    public static MainUI getUI() {
        return ui;
    }

    public static Program getExecutableFromList(int index) {
        return executables.get(index);
    }

    private static String nextCard() {
        return switch (currentCard) {
            case PageChoice.FIRST -> PageChoice.MAIN_MENU;
            case PageChoice.MM_INSTALLER -> PageChoice.CHECKOUT;
            case PageChoice.MM_LOAD -> PageChoice.CHK_INSTALL;
            case PageChoice.CHECKOUT, PageChoice.CHK_INSTALL -> PageChoice.FINAL;
            default -> null;
        };
    }

    private static String nextCard(String choice) {
        if (PageChoice.MAIN_MENU.equals(currentCard)) {
            return switch (choice) {
                case PageChoice.MM_INSTALLER, PageChoice.MM_EDIT -> choice;
                case PageChoice.MM_LOAD -> PageChoice.CHK_INSTALL;
                default -> null;
            };
        }

        return null;
    }

    private static String previousCard() {
        return windows.pop();
    }

    public static String getLanguageString(String key) {
        return language.getString(key);
    }

    // TODO remove access to array
    public static ArrayList<Program> getExecutables() {
        return executables;
    }
}
