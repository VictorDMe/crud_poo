package application;

import java.io.IOException;

import static tools.Crud.*;
import static tools.Database.createNewDatabase;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        String[] cls = new String[] {"cmd.exe", "/c", "cls"};
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
                case 4 -> comprar();
                case 5 -> vender();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}