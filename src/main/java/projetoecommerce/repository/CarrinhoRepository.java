package projetoecommerce.repository;

import projetoecommerce.model.Carrinho;

public interface CarrinhoRepository {
    void salvar(Carrinho carrinho);
    Carrinho obterPorUsuario(String usuarioId);
    void atualizar(Carrinho carrinho);
    void remover(String usuarioId);
}
