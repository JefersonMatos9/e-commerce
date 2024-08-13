//package projetoecommerce.users;

import projetoecommerce.database.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

//public class TestPessoas {
//  public static void main(String[] args) {
        /*
        // Instancia o serviço de login, responsável por gerenciar o processo de login.
        ServicoDeLogin loginService = new ServicoDeLogin();

        // Cria um novo usuário
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
            // Cadastra o usuário no banco de dados
            novoUsuario.cadastrar();

            // Tenta realizar o login com o e-mail e senha fornecidos
            try {
                boolean loginSucesso = loginService.login("exemplo@email.com", "12345");
                if (loginSucesso) {
                    System.out.println("Login realizado com sucesso");
                }
            } catch (EmailOuSenhaIncorretoException e) {
                // Captura a exceção caso o e-mail ou senha estejam incorretos
                System.out.println("Erro no login: " + e.getMessage());
            }

            // Atualiza os dados do perfil do usuário com ID 2
            loginService.atualizarDadosPerfil(2, "Paulo", "paulo@email.com", "984624731", "Rua 20", 156, "Bairro C", "Cidade C", "25664-321");

            // Atualiza a senha do usuário com o e-mail "novo@email.com"
            loginService.atualizarSenha("novo@email.com", "novaSenha123");

        } catch (SQLException e) {
            // Captura e imprime qualquer exceção SQL que possa ocorrer
            e.printStackTrace();
        }
        */
//}
//}
