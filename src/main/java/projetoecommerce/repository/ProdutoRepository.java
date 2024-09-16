package projetoecommerce.repository;

import projetoecommerce.model.Produto;

public interface ProdutoRepository {
    void salvar(Produto produto);
    Produto obterPorId(int id);
    void atualizar(Produto produto);
    void remover(int id);
    void atualizarEstoque(int produtoId, int novaQuantidade);
}
