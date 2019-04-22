package ua.den.util;

import java.util.*;

public enum ResourceBundleGetter {
    INSTANCE;

    private ResourceBundle resourceBundle;

    public Map<String, String> getResourceMap(String source, Locale locale) {
        Map<String, String> resultedMap = new HashMap<>();
        resourceBundle = ResourceBundle.getBundle(source, locale);

        Enumeration<String> keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();

            resultedMap.put(key, resourceBundle.getString(key));
        }

        return resultedMap;
    }

    public String getResourceString(String source, Locale locale, String key) {
        resourceBundle = ResourceBundle.getBundle(source, locale);

        return resourceBundle.getString(key);
    }
}
