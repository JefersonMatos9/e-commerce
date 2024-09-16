package projetoecommerce.repository;

import projetoecommerce.model.Usuario;

public interface UsuarioRepository {
    void salvar(Usuario usuario);
    Usuario obterPorId(int id);
    Usuario obterPorEmail(String email);
    void atualizar(Usuario usuario);
    void remover(int id);
    void atualizarSenha(String email, String novaSenha);
}
