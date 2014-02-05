package mdrive.business.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: andrey.osipov
 * Date: 8/17/11
 * Time: 1:43 PM
 */
public class Translit {

    static Map<Character, String> charMap = new HashMap<Character, String>() {
        {
            put('А', "A");
            put('Б', "B");
            put('В', "V");
            put('Г', "G");
            put('Д', "D");
            put('Е', "E");
            put('Ё', "E");
            put('Є', "E");
            put('Ж', "ZH");
            put('З', "Z");
            put('И', "I");
            put('Й', "I");
            put('Ї', "I");
            put('І', "I");
            put('К', "K");
            put('Л', "L");
            put('М', "M");
            put('Н', "N");
            put('О', "O");
            put('П', "P");
            put('Р', "R");
            put('С', "S");
            put('Т', "T");
            put('У', "U");
            put('Ф', "F");
            put('Х', "H");
            put('Ц', "C");
            put('Ч', "CH");
            put('Ш', "SH");
            put('Щ', "SH");
            put('Ъ', "'");
            put('Ы', "Y");
            put('Ь', "'");
            put('Э', "E");
            put('Ю', "U");
            put('Я', "YA");
            put('.', ".");
            put('\'', "'");
        }
    };

    static {
        //add same in lower case
        Map<Character, String> fullCharMap = new HashMap<Character, String>(charMap);
        for (Map.Entry<Character, String> entry : charMap.entrySet()) {
            fullCharMap.put(Character.toLowerCase(entry.getKey()), entry.getValue().toLowerCase());
        }
        charMap = fullCharMap;
    }


    /**
     * @param text
     * @return
     */
    public static String translitRusUkr2En(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            String value = charMap.get(c);
            if (value != null) {
                sb.append(value);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}