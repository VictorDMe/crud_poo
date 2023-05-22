package application;

import static tools.Crud.*;
import static tools.Database.createNewDatabase;

public class Program {
    public static void main(String[] args) {

        char menuOption;
        createNewDatabase();

        while (true) {

            System.out.println();
            menuOption = menu();
            System.out.println();

            // Fechar programa.
            if (menuOption == 'S') break;

            switch (menuOption) {
                case 'C' -> inserir();
                case 'R' -> ler();
                case 'U' -> atualizar();
                case 'D' -> deletar();
            }
        }
    }
}