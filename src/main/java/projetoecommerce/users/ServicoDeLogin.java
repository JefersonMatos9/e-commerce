package projetoecommerce.users;

import projetoecommerce.database.DataBaseConnection;
import projetoecommerce.exceptions.EmailOuSenhaIncorretoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoDeLogin {
    public boolean login(String email, String senha) throws EmailOuSenhaIncorretoException {
        Usuario usuario = obterUsuarioPorEmail(email);
        if (usuario == null) {
            throw new EmailOuSenhaIncorretoException("E-mail não possui cadastro.");
        }
        if (usuario.getSenha().equals(senha)) {
            return true; // retorne o usuario autenticado
        } else {
            throw new EmailOuSenhaIncorretoException("Senha incorreta.");
        }
    }

    public Usuario obterUsuarioPorEmail(String email) {
        String query = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

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
            } else {
                System.out.println("Usuário não encontrado para o e-mail " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o usuário
    }

    public static void atualizarDadosPerfil(int userId, String nome, String email, String telefone, String rua, int numero, String bairro, String cidade, String cep) {
        String query = "UPDATE usuarios SET nome = ?, email = ?, telefone = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, telefone);
            pstmt.setString(4, rua);
            pstmt.setInt(5, numero);
            pstmt.setString(6, bairro);
            pstmt.setString(7, cidade);
            pstmt.setString(8, cep);
            pstmt.setInt(9, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Perfil atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar perfil.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarSenha(String email, String novaSenha) {
        String query = "UPDATE usuarios SET senha = ? WHERE email = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, novaSenha);
            pstmt.setString(2, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Senha atualizada com sucesso.");
            } else {
                System.out.println("Falha ao atualizar senha, usuário não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
