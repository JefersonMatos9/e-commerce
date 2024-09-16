package projetoecommerce.service;

import projetoecommerce.model.Produto;
import projetoecommerce.model.Usuario;
import projetoecommerce.repository.ProdutoRepository;
import projetoecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static projetoecommerce.service.CarrinhoService.LOGGER;

public class ProdutoService {
    private static final Logger logger = Logger.getLogger(ProdutoService.class.getName());
    private ProdutoRepository produtoRepository;

    // Método setter para injeção de dependência
    public void setProdutoRepository(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    public void adicionarProduto(Produto produto) {
        String query = "INSERT INTO produtos (nome, descricao, preco, quantidade_estoque, tamanho, cor) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidadeEstoque());
            pstmt.setString(5, produto.getTamanho());
            pstmt.setString(6, produto.getCor());

            // Utilize o método 'salvar' no repositório se necessário
            if (produtoRepository != null) {
                produtoRepository.salvar(produto);
            }

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Produto cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao adicionar produto", e);
        }
        produtoRepository.salvar(produto);
    }

    public void atualizarEstoque(int produtoId, int novaQuantidade) {
        String query = "UPDATE produtos SET quantidade_estoque = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, novaQuantidade);
            pstmt.setInt(2, produtoId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Estoque atualizado com sucesso!");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar estoque", e);
        }
        produtoRepository.atualizarEstoque(produtoId, novaQuantidade);
    }

    public void removerProduto(int produtoId) {
        String query = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, produtoId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Produto removido com sucesso!");
            } else {
                logger.warning("Nenhum produto encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao remover produto", e);
        }
        produtoRepository.remover(produtoId);
    }

    public Produto obterPorid(int id) {
        String query = "SELECT * FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                Produto produto = new Produto();
                produto.setId(resultado.getInt("id"));
                produto.setNome(resultado.getString("nome"));
                produto.setDescricao(resultado.getString("descricao"));
                produto.setPreco(resultado.getDouble("preco"));
                produto.setQuantidadeEstoque(resultado.getInt("quantidade_estoque"));
                return produto;
            } else {
                logger.warning("Produto não encontrado para o ID." + id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao obter produto por id: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o usuário
    }
}
