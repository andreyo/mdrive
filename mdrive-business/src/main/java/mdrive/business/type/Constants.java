package mdrive.business.type;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * User: andrey.osipov
 * Date: 7/26/11
 * Time: 6:34 PM
 */
public class Constants {
    public static final Locale LOCALE_EN = Locale.ENGLISH;
    public static final Locale LOCALE_RU = new Locale("ru");
    public static final Locale LOCALE_UK = new Locale("uk");

    public static final List<Locale> LOCALES = Arrays.asList(new Locale[]{LOCALE_EN, LOCALE_RU, LOCALE_UK});

    public static final Locale DEFAULT_LOCALE = LOCALE_EN;

    private Constants() {
    }
}
