package projetoecommerce.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class TestDatebaseInsertion {
    public static void main(String[] args) {

      try (Connection connection = DataBaseConnection.getConnection()) {
        // Insira um novo usuário na tabela "usuarios"
        String insertUserSQL = "INSERT INTO usuarios (nome, email, senha, endereco, telefone) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL);
        preparedStatement.setString(1, "João da Silva");
        preparedStatement.setString(2, "joao@example.com");
        preparedStatement.setString(3, "senha123");
        preparedStatement.setString(4, "Rua A, 123");
        preparedStatement.setString(5, "123456789");
        int rowsAffected = preparedStatement.executeUpdate();

        // Verifique se a inserção foi bem-sucedida
        if (rowsAffected > 0) {
            System.out.println("Usuário inserido com sucesso!");
        } else {
            System.out.println("Falha ao inserir usuário.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
         }
    }
}
