package util;

public enum PropertiesSource {
    LOCALIZATION_STRINGS("localization"),
    REGEX_STRINGS("regex"),
    DATABASE("database"),
    COUNTRY("country_name"),
    HARBOR("harbor_names");

    public String source;

    PropertiesSource(String source) {
        this.source = source;
    }
}
