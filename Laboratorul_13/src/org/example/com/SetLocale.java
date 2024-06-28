package org.example.com;

import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public void execute(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("res.Messages", Locale.getDefault());
        System.out.println(bundle.getString("locale.set").replace("{0}", locale.toString()));
    }
}