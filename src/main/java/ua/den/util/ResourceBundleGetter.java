package ua.den.util;

import java.nio.charset.StandardCharsets;
import java.util.*;

public enum ResourceBundleGetter {
    INSTANCE;

    private ResourceBundle resourceBundle;

    public Map<String, String> getResourceMap(String source, String locale) {
        Map<String, String> resultedMap = new HashMap<>();
        resourceBundle = ResourceBundle.getBundle(source, new Locale(locale));

        Enumeration<String> keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();

            resultedMap.put(key, resourceBundle.getString(key));
        }

        return resultedMap;
    }

    public String getResourceString(String source, String locale, String key) {
        resourceBundle = ResourceBundle.getBundle(source, new Locale(locale));

        return resourceBundle.getString(key);
    }
}
