package application;

import static tools.Crud.*;
import static tools.Database.createNewDatabase;

public class Program {
    public static void main(String[] args) {

        int menuOption;
        createNewDatabase();

        while (true) {

            System.out.println();
            menuOption = menu();
            System.out.println();

            // Fechar programa.
            if (menuOption == 99) break;

            switch (menuOption) {
                case 1 -> login();
                case 2 -> registro();
                case 3 -> buscar();
            }
        }
    }
}