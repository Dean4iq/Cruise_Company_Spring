package ua.den.util;

public enum PropertiesSource {
    LOCALIZATION_STRINGS("localization"),
    REGEX_STRINGS("regex"),
    DATABASE("database"),
    COUNTRY("country_name"),
    HARBOR("harbor_names"),
    EXCURSION_INFO("excursion_info");

    public String source;

    PropertiesSource(String source) {
        this.source = source;
    }
}
