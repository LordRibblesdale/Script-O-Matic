package ControlCenter;

import Interface.Interface;
import Interface.PageChoice;

import java.util.ArrayList;
import java.util.Stack;
import java.util.ResourceBundle;

public class Controller {
    private ResourceBundle language;

    private Interface ui;
    private String currentCard;
    private ArrayList<String> initialisedCards;
    private Stack<String> windows;

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

    public void setLanguage(ResourceBundle language) {
        this.language = language;
    }

    private String nextCard() {
        String nextCard = null;

        switch (currentCard) {
            case PageChoice.FIRST:
                nextCard = PageChoice.MAIN_MENU;
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
