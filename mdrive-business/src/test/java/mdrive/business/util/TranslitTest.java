package mdrive.business.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: andrey.osipov
 * Date: 1/24/12
 * Time: 5:45 PM
 */
public class TranslitTest {


    @Test
    public void testToTranslitUa() throws Exception {
        final String test = "Добрий день, це українська транслітерація з цифрами 550/A Єреван";
        assertEquals("Dobrii den', ce ukrains'ka transliteraciya z ciframi 550/A Erevan", Translit.translitRusUkr2En(test));
    }

    @Test
    public void testToTranslitRu() throws Exception {
        final String test = "Привет, Мир. Это Длинная Строка с Разными символами русского алфавита.";
        assertEquals("Privet, Mir. Eto Dlinnaya Stroka s Raznymi simvolami russkogo alfavita.",
                Translit.translitRusUkr2En(test));
    }
}