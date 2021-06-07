package LocaleFiles;

import java.util.Locale;


public class LocaleInfo {
    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage() == "fr" || locale.getLanguage() == "es" || locale.getLanguage() == "en") {
            locale = Locale.getDefault();
        }
        else {
            locale = Locale.ENGLISH;
        }
        return locale;
    }
}
