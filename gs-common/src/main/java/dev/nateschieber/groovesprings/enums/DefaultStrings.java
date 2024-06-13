package dev.nateschieber.groovesprings.enums;

public enum DefaultStrings {
    // TODO:
    // private String en;
    // private String es;
    // private String fr;
    UNKNOWN_ARTIST("Unknown Artist"),
    UNTITLED_ALBUM("Untitled (Album)"),
    UNTITLED_TRACK("Untitled (Track)");

    private String str;

    DefaultStrings(String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }
}
