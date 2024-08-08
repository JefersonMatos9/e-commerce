package projetoecommerce.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario extends Pessoa {

    public void cadastrar() {
        String url = "jdbc:mysql://localhost:3306/e-commerce";
        String user = "root";
        String password = "";

        String query = "INSERT INTO usuario (nome, email, senha, endereco, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, this.getNome());
            pstmt.setString(2, this.getEmail());
            pstmt.setString(3, this.getSenha());
            pstmt.setString(4, this.getTelefone());
            pstmt.setString(5, this.getRua());
            pstmt.setInt(6, this.getNumero());
            pstmt.setString(7, this.getBairro());
            pstmt.setString(8, this.getCidade());
            pstmt.setString(9, this.getCep());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
