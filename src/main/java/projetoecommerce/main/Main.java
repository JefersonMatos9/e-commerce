package projetoecommerce.main;

import projetoecommerce.exceptions.EmailOuSenhaIncorretoException;
import projetoecommerce.users.ServicoDeLogin;

public class Main {
    public static void main(String[] args) {
       /* try (Connection connection = DataBaseConnection.getConnection()) {
            // Insira um novo usuário na tabela "usuarios"
            String insertUserSQL = "INSERT INTO usuarios (nome, email, senha, telefone, rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.setInt(6,0 );
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "");
            preparedStatement.setString(9, "");

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

        */

        ServicoDeLogin loginService = new ServicoDeLogin();

        String email = "decoo.matos@live.com";
        String senha = "senha";

        try{
            boolean sucesso = loginService.login(email,senha);
            if (sucesso){
                System.out.println("Login realizado com sucesso");
            }
        }catch (EmailOuSenhaIncorretoException e){
            System.out.println("Falha no login " + e.getMessage());
        }
    }
}
// CRIAR O METODO DE ATUALIZAR PERFIL