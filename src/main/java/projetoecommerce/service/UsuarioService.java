package projetoecommerce.service;

import projetoecommerce.model.Usuario;
import projetoecommerce.repository.UsuarioRepository;
import projetoecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UsuarioService {
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class.getName());
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrar(Usuario usuario) {
        String query = "INSERT INTO usuarios (nome, email, senha, telefone, rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.getTelefone());
            pstmt.setString(5, usuario.getRua());
            pstmt.setInt(6, usuario.getNumero());
            pstmt.setString(7, usuario.getBairro());
            pstmt.setString(8, usuario.getCidade());
            pstmt.setString(9, usuario.getCep());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Usuário cadastrado com sucesso.");
            }
        } catch (SQLException e) {
            LOGGER.severe("Erro ao cadastrar usuário: " + e.getMessage());
        }
        usuarioRepository.salvar(usuario);
    }

    public Usuario obterPorEmail(String email) {
        String query = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
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
                LOGGER.warning("Usuário não encontrado para o e-mail: " + email);
            }
        } catch (SQLException e) {
            LOGGER.severe("Erro ao obter usuário por e-mail: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o usuário
    }

    public void atualizarPerfil(Usuario usuario) {
        String query = "UPDATE usuarios SET nome = ?, email = ?, telefone = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getTelefone());
            pstmt.setString(4, usuario.getRua());
            pstmt.setInt(5, usuario.getNumero());
            pstmt.setString(6, usuario.getBairro());
            pstmt.setString(7, usuario.getCidade());
            pstmt.setString(8, usuario.getCep());
            pstmt.setInt(9, usuario.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Perfil atualizado com sucesso.");
            } else {
                LOGGER.warning("Falha ao atualizar perfil.");
            }
        } catch (SQLException e) {
            LOGGER.severe("Erro ao atualizar perfil: " + e.getMessage());
        }
        usuarioRepository.atualizar(usuario);
    }

    public void atualizarSenha(String email, String novaSenha) {
        String query = "UPDATE usuarios SET senha = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, novaSenha);
            pstmt.setString(2, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Senha atualizada com sucesso.");
            } else {
                LOGGER.warning("Falha ao atualizar senha, usuário não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.severe("Erro ao atualizar senha: " + e.getMessage());
        }
        usuarioRepository.atualizarSenha(email, novaSenha);
    }

    public void deletarUsuario(Usuario usuario) {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, usuario.getId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Usuário deletado com sucesso.");
                }else {
                LOGGER.warning("Nenhum usuário foi encontrado para deletar. ");
            }
            usuarioRepository.remover(usuario.getId());
        } catch (SQLException e) {
            LOGGER.severe("Erro ao deletar usuário: " + e.getMessage());
        }
        usuarioRepository.remover(usuario.getId());
    }
}
