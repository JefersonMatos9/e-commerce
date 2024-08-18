package projetoecommerce.cart;

import projetoecommerce.product.Produto;
import java.util.List;
import java.util.Map;

public abstract class Carrinho {
    private int id;
    private String usuarioId;
    private List<Produto>produtoList;
    Map<Produto,Integer> quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public abstract Produto adicionarProduto(Produto produto, int quantidade);
    public abstract Produto removerProduto();
    public  abstract double calcularTotal();

}