package tools;

import java.sql.*;

public class Database {
    private static final String DATABASENAME = "NOME_DO_BANCO.db";

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:C:\\sqlite\\" + DATABASENAME;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Nome do driver: " + meta.getDriverName());
                System.out.println("Conexão com o banco estabelecida.");
            }

            String sql = "CREATE TABLE IF NOT EXISTS alunos (\n" +
                    "matricula TEXT PRIMARY KEY NOT NULL," +
                    "nome TEXT NOT NULL" +
                    ")";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\" + DATABASENAME);
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

            while (rs.next()) {
                System.out.println(rs.getString("matricula") + "\t" +
                        rs.getString("nome"));
            }
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
}