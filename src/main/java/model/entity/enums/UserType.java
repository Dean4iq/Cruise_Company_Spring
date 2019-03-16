package model.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
    //TODO
    GUEST(null), USER(new HashMap<>()), ADMIN(new HashMap<>());

    Map<String,String> menuBarLinks;

    UserType(Map<String, String> menuBarLinks) {
        this.menuBarLinks = menuBarLinks;
    }

    public Map<String, String> getMenuBarLinks() {
        return menuBarLinks;
    }
}
