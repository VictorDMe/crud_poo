package tools;

import entities.Aluno;

import java.util.ArrayList;
import java.util.Scanner;

public class Crud {
    static Scanner scan = new Scanner(System.in);
    static Database db = new Database();

    public static int menu() {

        int option;

		ArrayList<String> produtosDestaque = new ArrayList();
		ArrayList<String> vendedoresDestaque = new ArrayList<>();
		ArrayList<String> catagoriasDestaque = new ArrayList<>();


        while (true) {
			produtosDestaque  = db.selectProdutosDestaque();
			vendedoresDestaque = db.selectVendedoresDestaque();
			catagoriasDestaque = db.selectCategoriasDestaque();

            System.out.println("================================================================");
            System.out.println("                      PATOSHOP - HOME");
            System.out.println("================================================================");
            System.out.println("              1- Login  2- Registro 3- Buscar  99 - Sair ");
            System.out.println("----------------------------------------------------------------");
            System.out.println("3- Buscar produtos");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Produtos em destaque:");
            System.out.printf( "- %s \n"
            		+ "- %s \n"
            		+ "- %s \n", produtosDestaque.get(0), produtosDestaque.get(1), produtosDestaque.get(2));
            System.out.print("14- Listar top 10 produtos \n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Produtos mais vendidos:");
			System.out.printf( "- %s \n"
					+ "- %s \n"
					+ "- %s \n", produtosDestaque.get(0), produtosDestaque.get(1), produtosDestaque.get(2));
            System.out.print("24- Listar top 10 produtos mais vendidos \n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Top 3 vendedores:");
			System.out.printf( "- %s \n"
					+ "- %s \n"
					+ "- %s \n", vendedoresDestaque.get(0), vendedoresDestaque.get(1), vendedoresDestaque.get(2));
            System.out.print("34- Listar top Categorias\n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Categorias");
			System.out.printf( "- %s \n"
					+ "- %s \n"
					+ "- %s \n", catagoriasDestaque.get(0), catagoriasDestaque.get(1), catagoriasDestaque.get(2));
            System.out.println("----------------------------------------------------------------");
            System.out.println("\n----------------------------------------------------------------");
            System.out.print("Digite sua opção: ");
            
            option = scan.nextInt();
            return option;
        }
    }
    public static void login(){
    	int escolha;
    	do {
    		System.out.println("================================================================");
	        System.out.println("                      PATOSHOP - LOGIN");
	        System.out.println("================================================================");
	        System.out.println("Escolha o perfil de acesso: ");
	        System.out.println("1- Acesso publico");
	        System.out.println("2- Anunciante");
	        System.out.println("3- Comprador");
	        System.out.println("4- Super Admin");
	        
	        System.out.print("Continuar como: ");
	        escolha = scan.nextInt();
	        
        	switch(escolha) {
        		case 2:
        			System.out.println("Usuario: ");
        			System.out.println("Senha: ");
        	}
    	} while(escolha >= 1 && escolha <= 4);
    }
    
    public static void registro() {
    	String username;
    	String password;
    	
    	
    	System.out.println("================================================================");
        System.out.println("                      PATOSHOP - REGISTRAR-SE");
        System.out.println("================================================================");
        scan.nextLine();
		System.out.print("Usuario: ");
        username = scan.nextLine();
		System.out.print("Senha: ");
		password = scan.nextLine();

		db.registrarUsuario(username, password);
    }
    
    public static void buscar() {
    	int escolha2;
    	int escolhaBusca;
    	
    	do{
	    	System.out.println("================================================================");
	        System.out.println("                      PATOSHOP - BUSCA");
	        System.out.println("================================================================");
	        System.out.println("Como deseja buscar?: ");
	        System.out.println("1- Por categoria");
	        System.out.println("2- Por nome do produto");
	        System.out.println("Buscar: ");
	        escolhaBusca = scan.nextInt();
	        
	        if(escolhaBusca == 1) {
	        	System.out.println("Escolha a categoria: ");
		        System.out.println("41- %s");
		        System.out.println("42- %s");
		        System.out.println("43- %s");
		        System.out.println("44- %s");
		        System.out.println("88- Todas as categorias");
		        System.out.println("Categoria: ");
		        escolha2 = scan.nextInt();
		        //mostrar produtos da categoria escolhida
	        }
	        
	        if(escolhaBusca == 2) {
	        	System.out.println("Nome do produto que está buscando: ");
	        	escolha2 = scan.nextInt();
	        	//mostrar produto com o nome 
	        }
    	} while(escolhaBusca == 1 || escolhaBusca == 2);
    }
}

