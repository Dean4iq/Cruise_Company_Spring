package model.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
    GUEST(null),
    USER(new HashMap<String, String>(){{put("search","menu.search");}}),
    ADMIN(new HashMap<String,String>(){{put("admin/search/users","menu.user_privilegies");}});

    Map<String,String> menuBarLinks;

    UserType(Map<String, String> menuBarLinks) {
        this.menuBarLinks = menuBarLinks;
    }

    public Map<String, String> getMenuBarLinks() {
        return menuBarLinks;
    }
}
