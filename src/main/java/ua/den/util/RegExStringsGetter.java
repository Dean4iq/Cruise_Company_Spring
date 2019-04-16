package ua.den.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public final class RegExStringsGetter {
    private static final Logger LOG = LogManager.getLogger(RegExStringsGetter.class);
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(PropertiesSource.REGEX_STRINGS.source);

    public String getRegExString(String key) {
        LOG.debug("extracting regEx Strings");

        return RESOURCE_BUNDLE.getString(key);
    }
}
