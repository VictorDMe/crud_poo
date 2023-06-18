package tools;

import java.util.ArrayList;
import java.util.Scanner;
import format.CorTexto;

public class Crud {
    static Scanner scan = new Scanner(System.in);
    static Database db = new Database();
    static CorTexto cor = new CorTexto();
    static private String TIPOUSUARIO = "Acesso Publico";

    public static int menu() {

        int option;

        ArrayList<String> produtosDestaque = new ArrayList();
        ArrayList<String> vendedoresDestaque = new ArrayList<>();
        ArrayList<String> catagoriasDestaque = new ArrayList<>();

        produtosDestaque = db.selectProdutosDestaque();
        vendedoresDestaque = db.selectVendedoresDestaque();
        catagoriasDestaque = db.selectCategoriasDestaque();

        System.out.println("================================================================");
        System.out.printf("                      PATOSHOP - HOME - %s\n", TIPOUSUARIO);
        System.out.println("================================================================");
        System.out.println("              1- Login  2- Registro 3- Buscar  99 - Sair ");
        System.out.println("----------------------------------------------------------------");
        System.out.println("3- Buscar produtos");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Produtos em destaque:");
        System.out.printf("- %s \n"
                + "- %s \n"
                + "- %s \n", produtosDestaque.get(0), produtosDestaque.get(1), produtosDestaque.get(2));
        System.out.print("14- Listar top 10 produtos \n");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Produtos mais vendidos:");
        System.out.printf("- %s \n"
                + "- %s \n"
                + "- %s \n", produtosDestaque.get(0), produtosDestaque.get(1), produtosDestaque.get(2));
        System.out.print("24- Listar top 10 produtos mais vendidos \n");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Top 3 vendedores:");
        System.out.printf("- %s \n"
                + "- %s \n"
                + "- %s \n", vendedoresDestaque.get(0), vendedoresDestaque.get(1), vendedoresDestaque.get(2));
        System.out.print("34- Listar top Categorias\n");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Categorias");
        System.out.printf("- %s \n"
                + "- %s \n"
                + "- %s \n", catagoriasDestaque.get(0), catagoriasDestaque.get(1), catagoriasDestaque.get(2));
        System.out.println("----------------------------------------------------------------");

        System.out.print("Digite sua opcao: ");

        option = scan.nextInt();
        return option;
    }

    public static void login() {
        int tipo_usuario;
        String usuario;
        String senha;

        System.out.println("================================================================");
        System.out.println("                      PATOSHOP - LOGIN");
        System.out.println("================================================================");

        do {
            System.out.println("Escolha o perfil de acesso: ");
            System.out.println("1 - Cliente");
            System.out.println("2 - Anunciante");
            System.out.println("3 - Super Admin");
            System.out.println("4  - Acesso publico");

            System.out.print("Continuar como: ");
            tipo_usuario = scan.nextInt();

        } while (tipo_usuario < 1 || tipo_usuario > 4);

        if (tipo_usuario < 4) {
            System.out.print("Usuario: ");
            usuario = scan.nextLine();
            scan.nextLine();

            System.out.print("Senha: ");
            senha = scan.nextLine();

            if (db.login(usuario, senha, tipo_usuario)) {
                switch (tipo_usuario) {
                    case 1:
                        TIPOUSUARIO = "Cliente";
                        break;
                    case 2:
                        TIPOUSUARIO = "Anunciante";
                        break;
                    case 3:
                        TIPOUSUARIO = "Super Admin";
                        break;
                }
            } else System.out.println("Login inválido.");
        } else TIPOUSUARIO = "Acesso Publico";
    }

    public static void registro() throws InterruptedException {
        int tipoUsuario = 0;
        String username, password, nome, endereco, telefone, email;

        System.out.println("================================================================");
        System.out.println("                      PATOSHOP - REGISTRAR-SE");
        System.out.println("================================================================");
        scan.nextLine();

        do {
            System.out.print("Tipo de Usuario: \n" +
                    "1 - Cliente \n" +
                    "2 - Anunciante \n" +
                    "Digite sua opcao: ");
            tipoUsuario = scan.nextInt();

            if (tipoUsuario < 1 || tipoUsuario > 2)
                System.out.println("Opcao invalida, tente novamente. \n");
        } while (tipoUsuario < 1 || tipoUsuario > 2);

        System.out.print("Usuario: ");
        username = scan.nextLine();
        scan.nextLine();

        System.out.print("Senha: ");
        password = scan.nextLine();

        System.out.print("Nome: ");
        nome = scan.nextLine();

        System.out.print("Endereco: ");
        endereco = scan.nextLine();

        System.out.print("Telefone: ");
        telefone = scan.nextLine();

        System.out.print("Email: ");
        email = scan.nextLine();

        System.out.println("CADASTRADO COM SUCESSO");
        Thread.sleep(2000);

        db.registrarUsuario(tipoUsuario, username, password,
                nome, endereco, telefone, email);
    }

    public static void buscar() {
        String categoriaDigitada;
        String produtoPesquisa;
        int escolhaBusca;

        do {
            System.out.println("================================================================");
            System.out.println("                      PATOSHOP - BUSCA");
            System.out.println("================================================================");
            System.out.println("Como deseja buscar?: ");
            System.out.println("1 - Por categoria");
            System.out.println("2 - Por nome do produto");
            System.out.print("Buscar: ");
            escolhaBusca = scan.nextInt();
            scan.nextLine();

            if (escolhaBusca == 1) {
                System.out.print("Digite a categoria: ");
                categoriaDigitada = scan.nextLine();
                db.selectProdutoCategoria(categoriaDigitada);
            }

            if (escolhaBusca == 2) {
                System.out.print("Nome do produto que está buscando: ");
                produtoPesquisa = scan.nextLine();
                db.selectProdutoPesquisa(produtoPesquisa);
            }
        } while (escolhaBusca == 1 || escolhaBusca == 2);
    }
}

