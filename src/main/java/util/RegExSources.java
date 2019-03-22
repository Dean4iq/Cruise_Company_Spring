package util;

public enum RegExSources {
    NAME("name", "regex.name"),
    LAST_NAME("surname", "regex.lastName"),
    LOGIN("login", "regex.login"),
    PASSWORD("password", "regex.password"),
    EMAIL("email", "regex.email");

    String field;
    String link;

    RegExSources(String field, String link) {
        this.field = field;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getField() {
        return field;
    }
}
