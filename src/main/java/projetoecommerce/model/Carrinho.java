package projetoecommerce.model;

import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private int id;
    private String usuarioId;
    private Map<Produto, Integer> itens = new HashMap<>();

    // Getters e Setters
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

    public Map<Produto, Integer> getItens() {
        return new HashMap<>(itens);
    }

    public void setItens(Map<Produto, Integer> itens) {
        this.itens = new HashMap<>(itens);
    }

    public void adicionarItem(Produto produto,int quantidade){
        itens.merge(produto,quantidade,Integer ::sum);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }
}
