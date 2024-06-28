package org.example.com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public void execute() {
        ResourceBundle bundle = ResourceBundle.getBundle("res.Messages", Locale.getDefault());
        System.out.println(bundle.getString("locales"));
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            System.out.println(locale.getDisplayName());
        }
    }
}
