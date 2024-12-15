import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class i18nTest {

    @Test
    public void test() {
        String title;
        Locale enLanguage = new Locale.Builder().setLanguage("en").build();
        ResourceBundle bundle = ResourceBundle.getBundle("introLangData", enLanguage);

        title = bundle.getString("title");
        System.out.println(title);

    }
}
