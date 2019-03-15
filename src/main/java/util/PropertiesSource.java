package util;

public enum PropertiesSource {
    LOCALIZATION_STRINGS("localization"),
    REGEX_STRINGS("regex"),
    DATABASE("database");

    String source;

    PropertiesSource(String source) {
        this.source = source;
    }
}
