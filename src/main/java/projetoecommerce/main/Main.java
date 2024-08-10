package projetoecommerce.main;

import projetoecommerce.database.DataBaseConnection;
import projetoecommerce.exceptions.EmailOuSenhaIncorretoException;
import projetoecommerce.users.ServicoDeLogin;
import projetoecommerce.users.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServicoDeLogin loginService = new ServicoDeLogin();
        //Criando um novo usuario
      Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Felix");
        novoUsuario.setEmail("Felix@email.com");
        novoUsuario.setSenha("12345");
        novoUsuario.setTelefone("123456789");
        novoUsuario.setRua("Rua D");
        novoUsuario.setNumero(123);
        novoUsuario.setBairro("Bairro D");
        novoUsuario.setCidade("Cidade D");
        novoUsuario.setCep("28345-678");

        // Conectar ao banco de dados e realizar operações
        try (Connection connection = DataBaseConnection.getConnection()) {
           novoUsuario.cadastrar(); // Cadastra o usuário

            // Tenta fazer login
        /*    try {
                boolean loginSucesso = loginService.login("exemplo@email.com", "12345");
                if (loginSucesso) {
                    System.out.println("Login realizado com sucesso");
                }
            } catch (EmailOuSenhaIncorretoException e) {
                System.out.println("Erro no login: " + e.getMessage());
            }

         */

            // Atualiza os dados do perfil
          //  loginService.atualizarDadosPerfil(2, "Paulo", "paulo@email.com", "984624731", "Rua 20", 156, "Bairro C", "Cidade C", "25664-321");

            // Atualiza a senha
//loginService.atualizarSenha("novo@email.com", "novaSenha123");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
