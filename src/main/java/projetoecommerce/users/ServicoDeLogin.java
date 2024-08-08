package projetoecommerce.users;

import projetoecommerce.exceptions.EmailOuSenhaIncorretoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoDeLogin {
    public boolean login(String email, String senha) throws EmailOuSenhaIncorretoException {
        Usuario usuario = obterUsuarioPorEmail(email);
        if (usuario == null){
            throw new EmailOuSenhaIncorretoException("E-mail não possui cadastro.");
        }
        if (usuario.getSenha().equals(senha)){
            return true;
        }else{
            throw new EmailOuSenhaIncorretoException("Senha incorreta.");
        }
    }

    private Usuario obterUsuarioPorEmail(String email) {
        String url = "jdbc:mysql://localhost:3306/e-commerce";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conexao = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conexao.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(resultado.getString("nome"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setTelefone(resultado.getString("telefone"));
                usuario.setRua(resultado.getString("rua"));
                usuario.setNumero(resultado.getInt("numero"));
                usuario.setBairro(resultado.getString("bairro"));
                usuario.setCidade(resultado.getString("cidade"));
                usuario.setCep(resultado.getString("cep"));
                return usuario;
            }else {
                System.out.println("Usuario não encontrado para o e-mail " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o usuário
    }
}
