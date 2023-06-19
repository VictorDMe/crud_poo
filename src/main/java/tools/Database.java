package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


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

            String sql = "CREATE TABLE TiposUsuarios (\n" +
                    "    ID_Usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Descricao TEXT\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Usuarios (\n" +
                    "    ID_usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    TipoUsuario INTEGER,\n" +
                    "    login TEXT,\n" +
                    "    senha TEXT,\n" +
                    "    Nome TEXT,\n" +
                    "    Endereco TEXT,\n" +
                    "    Telefone TEXT,\n" +
                    "    Email TEXT,\n" +
                    "    UNIQUE(login), \n" +
                    "    FOREIGN KEY (TipoUsuario) REFERENCES TiposUsuarios(ID_Usuario)\n" +
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
                    "    ID_usuario INTEGER,\n" +
                    "    ID_categoria INTEGER,\n" +
                    "    FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID_usuario),\n" +
                    "    FOREIGN KEY (ID_categoria) REFERENCES Categorias(ID_categoria)\n" +
                    ");\n";

            stmt.execute(sql);

            sql = "CREATE TABLE Vendas (\n" +
                    "    ID_venda INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ID_usuario INTEGER,\n" +
                    "    ID_produto INTEGER,\n" +
                    "    Data TEXT,\n" +
                    "    FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID_usuario),\n" +
                    "    FOREIGN KEY (ID_produto) REFERENCES Produtos(ID_produto)\n" +
                    ");\n";

            stmt.execute(sql);

            // =========== INSERT INICIAL PADRAO ===========
            sql = "INSERT INTO TiposUsuarios(Descricao) VALUES " +
                    "('Cliente'), ('Anunciante'), ('Super Admin');";

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


    protected ArrayList<String> selectProdutosDestaque() {
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Produtos limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }
            for (int i = arrayRetornavel.size(); i <= 3; i++) {
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arrayRetornavel;
    }

    protected ArrayList<String> selectVendedoresDestaque() {
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Usuarios where TipoUsuario = 2 limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }

            for (int i = arrayRetornavel.size(); i <= 3; i++) {
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arrayRetornavel;
    }

    protected ArrayList<String> selectCategoriasDestaque() {
        ArrayList<String> arrayRetornavel = new ArrayList<>();
        String sql = "SELECT Nome from Categorias limit 3";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                arrayRetornavel.add(rs.getString("Nome"));
            }
            for (int i = arrayRetornavel.size(); i <= 3; i++) {
                arrayRetornavel.add("Vazio");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arrayRetornavel;
    }

    protected void selectProdutoCategoria(String categoria) {
        try (Connection conn = this.connect();){
            String query = "SELECT \n" +
                    "Produtos.Nome, Produtos.Preco " +
                    "FROM " +
                    "Produtos " +
                    "Inner Join Categorias " +
                    "ON Categorias.ID_categoria = Produtos.ID_categoria " +
                    "WHERE " +
                    "Categorias.Nome LIKE ?";

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

    protected void selectProdutoPesquisa(String produto) {
        try (Connection conn = this.connect();){
            String query = "SELECT " +
                    "ID_produto, Nome, Preco " +
                    "FROM Produtos " +
                    "WHERE " +
                    "Nome LIKE ? || '%'";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, produto);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.printf("\nID: %s; \nNome: %s; \nPreco: \n%s;\n",
                        rs.getString("ID_produto"), rs.getString("Nome"),
                        rs.getString("Preco"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void registrarUsuario(int tipoUsuario, String username,
                                    String password, String nome,
                                    String endereco, String telefone,
                                    String email) {

        String sql = "INSERT INTO " +
                "Usuarios(TipoUsuario, login, senha, Nome, Endereco, Telefone, Email) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tipoUsuario);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, nome);
            pstmt.setString(5, endereco);
            pstmt.setString(6, telefone);
            pstmt.setString(7, email);

            pstmt.executeUpdate();
            System.out.println("CADASTRADO COM SUCESSO");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("CADASTRO INVALIDO, LOGIN JA EXISTE.");
        }
    }

    protected boolean login(String usuario, String senha, int tipo_usuario) {
        try( Connection conn = this.connect();) {
            String query = "SELECT (count(*) > 0) as found FROM Usuarios " +
                    "WHERE " +
                    "login LIKE ? AND " +
                    "senha LIKE ? AND " +
                    "TipoUsuario = ?";


            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, usuario);
            pst.setString(2, senha);
            pst.setInt(3, tipo_usuario);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return (rs.getBoolean(1));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    protected void compra(int ID_produto) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dataHoraAtual = dtf.format(now);
        int ID_usuario = idUsuario();

        if (ID_usuario != 0) {

            String sql = "INSERT INTO " +
                    "Vendas(ID_usuario, ID_produto, Data) " +
                    "VALUES(?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, ID_usuario);
                pstmt.setInt(2, ID_produto);
                pstmt.setString(3, dataHoraAtual);

                pstmt.executeUpdate();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    protected int idUsuario() {
        try (Connection conn = this.connect();){
            String query = "SELECT " +
                    "ID_usuario " +
                    "FROM Usuarios " +
                    "WHERE " +
                    "login LIKE ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, Crud.USUARIO);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return rs.getInt("ID_usuario");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}