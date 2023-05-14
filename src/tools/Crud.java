package tools;

import entities.Aluno;

import java.util.ArrayList;
import java.util.Scanner;

public class Crud {
    static Scanner scan = new Scanner(System.in);
    static Database db = new Database();

    public static char menu() {

        char crudOption;
        char[] options = new char[] {'C', 'R', 'U', 'D', 'S'};

        while (true) {
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            System.out.println(
                    """
                            C - Criar;\s
                            R - Ler;\s
                            U - Atualizar;\s
                            D - Deletar;\s

                            S - Sair""");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            System.out.print("Digite sua opção: ");
            crudOption = Character.toUpperCase(scan.next().charAt(0));

            // Limpando Buffer
            scan.nextLine();

            if (!(new String(options).indexOf(crudOption) == -1)) return crudOption;
        }
    }

    public static void inserir() {

        char option;

        String nome;
        String matricula;

        do {
            System.out.print("""
                    =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                    A - Adicionar somente um aluno;
                    B - Adicionar +1 de um aluno;
                    =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                    Digite sua opção:\s""");
            option = Character.toUpperCase(scan.next().charAt(0));
            scan.nextLine();
        } while ((option != 'A') && (option != 'B'));
        System.out.println();
        // Caso a opção 'somente um' tenha sido escolhida:
        if (option == 'A') {
            System.out.print("Insira o nome do aluno: ");
            nome = scan.nextLine();
            System.out.print("Insira a matrícula do aluno: ");
            matricula = scan.nextLine();

            if (!db.checkIsExist(matricula)) {
                db.insert(nome, matricula);
                System.out.println("Novo registro adicionado.");
            }
            else System.out.println("Já existe um registro com essa matrícula.");
        }

        // Caso a opção +1 tenha sido escolhida:
        else {
            int q;
            ArrayList<Aluno> alunos = new ArrayList<>();

            System.out.print("Quantidade de alunos que serão digitados: ");
            q = scan.nextInt();
            System.out.println();
            scan.nextLine();

            System.out.println();
            for (int x = 0; x < q; x++) {
                System.out.printf("Insira o nome do %d° aluno: ", x+1);
                nome = scan.nextLine();
                System.out.printf("Insira a matrícula do %d° aluno: ", x+1);
                matricula = scan.nextLine();

                alunos.add(new Aluno(nome, matricula));
            }

            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            for (Aluno a : alunos) {
                System.out.printf("\nTentando adicionar %s ao banco...\n", a.getMatricula());
                if (!db.checkIsExist(a.getMatricula())) {
                    db.insert(a.getNome(), a.getMatricula());
                    System.out.println("Novo registro adicionado.");
                }
                else System.out.println("Já existe um registro com essa matrícula.");
            }
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        }
    }

    public static void ler() {

        char crudOption;
        char[] options = new char[] {'F', 'S', 'V'};

        do {
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            System.out.println(
                    """
                            F - Com filtro;\s
                            S - Sem filtro;\s
                            V - Voltar""");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            System.out.print("Digite sua opção: ");
            crudOption = Character.toUpperCase(scan.next().charAt(0));

        } while (new String(options).indexOf(crudOption) == -1);

        try {
            if (crudOption == 'F') {
                System.out.print("Digite a matricula: ");
                scan.nextLine();
                String matricula = scan.nextLine();
                System.out.println();
                System.out.println("\u001B[34mMATRÍCULA \t NOME \u001B[0m");
                db.selectWithFilter(matricula);
            } else if (crudOption == 'S') {
                System.out.println("\nMATRÍCULA \tNOME");
                db.selectAll();
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void atualizar() {
        String matricula;
        String nome;

        System.out.print("Digite a matricula do aluno que deseja alterar: ");
        matricula = scan.nextLine();
        System.out.print("Digite o novo nome: ");
        nome = scan.nextLine();

        db.update(matricula, nome);
    }

    public static void deletar() {
        String matricula;

        System.out.print("Digite a matrícula que deseja excluir: ");
        matricula = scan.nextLine();

        db.delete(matricula);
    }
}