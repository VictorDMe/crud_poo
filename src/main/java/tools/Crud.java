package tools;

import entities.Aluno;

import java.util.ArrayList;
import java.util.Scanner;

public class Crud {
    static Scanner scan = new Scanner(System.in);
    static Database db = new Database();

    public static int menu() {

        int option;

        while (true) {
            System.out.println("================================================================");
            System.out.println("                      PATOSHOP - HOME");
            System.out.println("================================================================");
            System.out.println("              1- Login  2- Registro  99 - Sair ");
            System.out.println("----------------------------------------------------------------");
            System.out.println("3- Buscar produtos");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Produtos em destaque:");
            System.out.println("%s \n"
            		+ "%s \n"
            		+ "%s ");
            System.out.print("14- Listar top 10 produtos \n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Produtos mais vendidos:");
            System.out.println("%s \n"
            		+ "%s \n"
            		+ "%s");
            System.out.print("24- Listar top 10 produtos mais vendidos \n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Top 3 vendedores:");
            System.out.println("%s \n"
            		+ "%s \n"
            		+ "%s");
            System.out.print("34- Listar top 10 produtos mais vendidos\n");
            System.out.println("----------------------------------------------------------------");
            System.out.println("Categorias");
            System.out.println("%s");
            System.out.println("%s");
            System.out.println("%s");
            System.out.println("%s");
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
    	String email;
    	String password;
    	int cpf;
    	int numCelular;
    	
    	
    	System.out.println("================================================================");
        System.out.println("                      PATOSHOP - REGISTRAR-SE");
        System.out.println("================================================================");
        System.out.println("Usuario: ");
        username = scan.nextLine();
        System.out.println("Numero do celular: ");
        numCelular = scan.nextInt();
        System.out.println("CPF: ");
        cpf = scan.nextInt();
        System.out.println("Email: ");
        email = scan.nextLine();
		System.out.println("Senha: ");
		password = scan.nextLine();
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

