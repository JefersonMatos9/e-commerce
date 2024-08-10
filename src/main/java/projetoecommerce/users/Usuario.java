package projetoecommerce.users;

import projetoecommerce.database.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario extends Pessoa {
    @Override
    public void cadastrar() {
        String query = "INSERT INTO usuarios (nome, email, senha, telefone, rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
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
