package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String DATABASE = "patoshop.db";
    private static final String RELATIVE = System.getProperty("user.dir");

    public static void createNewDatabase() {

        // =========== CRIANDO DIRETORIO 'DATABASE' SE NÃO EXISTIR ===========
        try {
            Files.createDirectories(Paths.get(RELATIVE + "\\database"));
            System.out.println("Diretório 'database' identificado!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // =========== CRIANDO BANCO DE DADOS OU SOMENTE SE CONECTANDO ===========
        String url = "jdbc:sqlite:" + RELATIVE + "\\database\\" + DATABASE;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Nome do driver: " + meta.getDriverName());
                System.out.println("Conexão com o banco estabelecida.");
            }

            // =========== SQL INICIAL PARA CRIAÇÃO DAS TABELAS ===========

            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE Usuarios (\n" +
                    "    ID_usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    login TEXT,\n" +
                    "    senha TEXT\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Clientes (\n" +
                    "    ID_cliente INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ID_usuario INTEGER,\n" +
                    "    Nome TEXT,\n" +
                    "    Endereco TEXT,\n" +
                    "    Telefone TEXT,\n" +
                    "    Email TEXT,\n" +
                    "    FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID_usuario)\n" +
                    ");";

            stmt.execute(sql);

            sql = "CREATE TABLE Vendedor (\n" +
                    "    ID_vendedor INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ID_usuario INTEGER,\n" +
                    "    Nome TEXT,\n" +
                    "    Endereco TEXT,\n" +
                    "    Telefone TEXT,\n" +
                    "    Email TEXT,\n" +
                    "     FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID_usuario)\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Fabricante (\n" +
                    "    ID_fabricante INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ID_usuario INTEGER,\n" +
                    "    Nome TEXT,\n" +
                    "    Endereco TEXT,\n" +
                    "    Telefone TEXT,\n" +
                    "    FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID_usuario)\n" +
                    ");\n";


            stmt.execute(sql);

            sql = "CREATE TABLE Categorias (\n" +
                    "    ID_categoria INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Nome TEXT\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Produtos (\n" +
                    "    ID_produto INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Nome TEXT,\n" +
                    "    Preco REAL,\n" +
                    "    ID_fabricante INTEGER,\n" +
                    "    ID_categoria INTEGER,\n" +
                    "    FOREIGN KEY (ID_fabricante) REFERENCES Fabricante(ID_fabricante),\n" +
                    "    FOREIGN KEY (ID_categoria) REFERENCES Categorias(ID_categoria)\n" +
                    ");\n";


            stmt.execute(sql);

            sql = "CREATE TABLE Vendas (\n" +
                    "    ID_venda INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ID_cliente INTEGER,\n" +
                    "    ID_vendedor INTEGER,\n" +
                    "    ID_produto INTEGER,\n" +
                    "    Data TEXT,\n" +
                    "    Valor_total REAL,\n" +
                    "    FOREIGN KEY (ID_cliente) REFERENCES Clientes(ID_cliente),\n" +
                    "    FOREIGN KEY (ID_vendedor) REFERENCES Vendedor(ID_vendedor),\n" +
                    "    FOREIGN KEY (ID_produto) REFERENCES Produtos(ID_produto)\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Admin (\n" +
                    "    ID_admin INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Nome TEXT,\n" +
                    "    Usuario TEXT,\n" +
                    "    Senha TEXT\n" +
                    ");\n";

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + RELATIVE + "\\database\\" + DATABASE);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected boolean checkIsExist(String matricula) {
        try {
            String query = "SELECT (count(*) > 0) as found FROM alunos WHERE matricula LIKE ?";
            Connection conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, matricula);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    protected void insert(String nome, String matricula) {
        String sql = "INSERT INTO alunos(nome, matricula) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, matricula);
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void selectAll() throws ClassNotFoundException {
        String sql = "SELECT matricula, nome FROM alunos";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.print("\u001B[32m");
            while (rs.next()) {
                System.out.println(rs.getString("matricula") + "\t" +
                        rs.getString("nome"));
            }
            System.out.print("\u001B[0m");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void selectWithFilter(String matricula) {
        try {
            String query = "SELECT matricula, nome FROM alunos WHERE matricula LIKE ?";
            Connection conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, matricula);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("matricula") + "\t" +
                        rs.getString("nome"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void update(String matricula, String nome) {
        if (this.checkIsExist(matricula)) {
            try {
                String query = "UPDATE alunos SET nome = ? WHERE matricula = ?";
                Connection conn = this.connect();
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, nome);
                pst.setString(2, matricula);

                pst.executeUpdate();

                System.out.printf("Matricula %s atualizada. \n", matricula);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Matrícula não encontrada.");
        }
    }

    protected void delete(String matricula) {
        if (this.checkIsExist(matricula)) {
            try {
                String sql = "DELETE FROM alunos WHERE matricula = ?";
                Connection conn = this.connect();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, matricula);

                pst.executeUpdate();

                System.out.printf("Matrícula %s deletada do banco. \n", matricula);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("Matrícula %s não existe.", matricula);
        }
    }
    protected ArrayList<String> selectProdutosDestaque(){
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Produtos limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }
            for (int i = arrayRetornavel.size(); i<=3; i++){
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
     return arrayRetornavel;
    }

    protected ArrayList<String> selectVendedoresDestaque(){
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Vendedor limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }

            for (int i = arrayRetornavel.size(); i<=3; i++){
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arrayRetornavel;
    }

    protected ArrayList<String> selectCategoriasDestaque(){
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Categorias limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }
            for (int i = arrayRetornavel.size(); i<=3; i++){
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arrayRetornavel;
    }

    protected void registrarUsuario( String username, String password ){
        String sql = "INSERT INTO Usuarios(login, senha) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void selectProdutoCategoria(String categoria){
        try {
            String query = "SELECT \n" +
                    "Produtos.Nome, Produtos.Preco\n" +
                    "\n" +
                    "FROM \n" +
                    "Produtos \n" +
                    "Inner Join Categorias\n" +
                    "ON Categorias.ID_categoria = Produtos.ID_categoria\n" +
                    "\n" +
                    "WHERE\n" +
                    "Categorias.Nome LIKE ?";
            Connection conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, categoria);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Nome") + "\t" +
                        rs.getString("Preco"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected void selectProdutoPesquisa(String produto){
        try {
            String query = "SELECT\n" +
                    "Nome, Preco\n" +
                    "\n" +
                    "FROM\n" +
                    "Produtos\n" +
                    "\n" +
                    "WHERE\n" +
                    "Nome LIKE ? || '%'";
            Connection conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, produto);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Nome") + "\t" +
                        rs.getString("Preco"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected boolean login(String usuario, String senha) {
        try {
            String query = "SELECT (count(*) > 0) as found FROM Usuarios WHERE login LIKE ? AND senha like ?";
            Connection conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, usuario);
            pst.setString(2, senha);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

}