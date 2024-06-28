package org.example.app;

import org.example.com.DisplayLocales;
import org.example.com.Info;
import org.example.com.SetLocale;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResourceBundle bundle = ResourceBundle.getBundle("res.Messages", Locale.getDefault());

        while (true) {
            System.out.println(bundle.getString("prompt"));
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("DisplayLocales")) {
                new DisplayLocales().execute();
            } else if (command.startsWith("SetLocale")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    new SetLocale().execute(parts[1]);
                } else {
                    System.out.println(bundle.getString("invalid"));
                }
            } else if (command.startsWith("Info")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    new Info().execute(parts[1]);
                } else if (parts.length == 1) {
                    new Info().execute("");
                } else {
                    System.out.println(bundle.getString("invalid"));
                }
            } else {
                System.out.println(bundle.getString("invalid"));
            }
        }
    }
}