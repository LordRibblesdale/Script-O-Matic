package ControlCenter;

import java.util.Locale;

public enum Language {
    ENGLISH (new Locale("en", "UK")),
    ITALIAN (new Locale("it", "IT"));

    private Locale language;

    Language(Locale language) {
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return super.toString().charAt(0) + super.toString().substring(1).toLowerCase();
    }
}
