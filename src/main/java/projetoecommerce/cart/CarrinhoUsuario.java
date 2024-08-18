package projetoecommerce.cart;

import projetoecommerce.database.DataBaseConnection;
import projetoecommerce.product.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarrinhoUsuario extends Carrinho {
    // Mapa de quantidades
    private Map<Produto, Integer> quantidade = new HashMap<>();

    @Override
    public Produto adicionarProduto(Produto produto, int quantidade) {
        String query = "SELECT * FROM produtos WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, produto.getId());

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    // Atualiza os detalhes do produto com base no banco de dados
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setQuantidadeEstoque(resultSet.getInt("quantidade_estoque"));

                    // Verifica se o usuário está logado
                    if (getUsuarioId() == null) {
                        throw new IllegalArgumentException("Faça o login para adicionar o produto no carrinho");
                    }

                    // Verifica se a quantidade no estoque é suficiente
                    if (produto.getQuantidadeEstoque() >= quantidade) {
                        this.quantidade.put(produto, this.quantidade.getOrDefault(produto, 0) + quantidade);
                        // Atualiza a quantidade em estoque
                        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

                        // Inserir no banco de dados
                        String insertQuery = "INSERT INTO carrinho_produtos (usuario_id, produto_id, quantidade) VALUES (?, ?, ?)";
                        try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                            statement.setString(1, getUsuarioId());
                            statement.setInt(2, produto.getId());
                            statement.setInt(3, quantidade);
                            statement.executeUpdate();
                        }

                        return produto;
                    } else {
                        throw new RuntimeException("Produto indisponível");
                    }
                } else {
                    throw new RuntimeException("Produto não encontrado");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados.");
        }
    }

    @Override
    public Produto removerProduto() {
        // Implementação necessária
        return null;
    }

    @Override
    public double calcularTotal() {
        // Implementação necessária
        return 0;
    }
}
