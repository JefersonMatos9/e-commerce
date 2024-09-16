package projetoecommerce.service;

import projetoecommerce.exception.EmailOuSenhaIncorretoException;
import projetoecommerce.model.Usuario;
import projetoecommerce.repository.UsuarioRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginService {
    private static final Logger logger = Logger.getLogger(LoginService.class.getName());
    private UsuarioService usuarioService;
    private Usuario usuarioLogado;

    // Método setter para injeção de dependência
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Tenta realizar o login com o email e senha fornecidos.
     *
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     * @return true se o login for bem-sucedido, false caso contrário.
     * @throws EmailOuSenhaIncorretoException Se o email ou a senha estiverem incorretos.
     */
    public boolean login(String email, String senha) throws EmailOuSenhaIncorretoException {
        if (usuarioService == null) {
            throw new IllegalStateException("UsuarioService não foi inicializado.");
        }

        Usuario usuario = usuarioService.obterPorEmail(email);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            logger.warning("Falha de login: Email ou senha incorretos para o email " + email);
            throw new EmailOuSenhaIncorretoException("Email ou senha incorretos");
        }

        usuarioLogado = usuario; // Define o usuário logado
        logger.info("Login bem-sucedido para o email " + email);
        return true;
    }

    /**
     * Retorna o usuário atualmente logado.
     *
     * @return O usuário logado ou null se nenhum usuário estiver logado.
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Realiza o logout do usuário atualmente logado.
     */
    public void logout() {
        usuarioLogado = null;
        logger.info("Usuário deslogado com sucesso");
    }
}
