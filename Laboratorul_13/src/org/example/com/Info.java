package org.example.com;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public void execute(String languageTag) {
        Locale locale = languageTag.isEmpty() ? Locale.getDefault() : Locale.forLanguageTag(languageTag);
        ResourceBundle bundle = ResourceBundle.getBundle("res.Messages", Locale.getDefault());
        System.out.println(bundle.getString("info").replace("{0}", locale.toString()));

        String country = locale.getCountry().isEmpty() ? "(No country specified)" : locale.getDisplayCountry(Locale.getDefault()) + " (" + locale.getDisplayCountry(locale) + ")";
        System.out.println(bundle.getString("country").replace("{0}", country));

        String language = bundle.getString("language").replace("{0}", locale.getDisplayLanguage(Locale.getDefault()) + " (" + locale.getDisplayLanguage(locale) + ")");
        System.out.println(language);

        try {
            Currency currency = Currency.getInstance(locale);
            String currencyInfo = bundle.getString("currency").replace("{0}", currency.getCurrencyCode() + " (" + currency.getDisplayName(locale) + ")");
            System.out.println(currencyInfo);
        } catch (IllegalArgumentException e) {
            System.out.println("Currency: No currency available for this locale");
        }

        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] weekdays = symbols.getWeekdays();
        String weekdaysStr = String.join(", ", weekdays).trim();
        System.out.println(bundle.getString("weekdays").replace("{0}", weekdaysStr));

        String[] months = symbols.getMonths();
        String monthsStr = String.join(", ", months).trim();
        System.out.println(bundle.getString("months").replace("{0}", monthsStr));

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
        String today = dateFormat.format(new Date());
        System.out.println(bundle.getString("today").replace("{0}", today));
    }
}