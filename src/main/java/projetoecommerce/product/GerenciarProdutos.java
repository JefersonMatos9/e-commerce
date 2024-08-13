package projetoecommerce.product;

import projetoecommerce.database.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import projetoecommerce.database.DataBaseConnection;

public class GerenciarProdutos extends Produto{
    @Override
    public void adicionarProduto() {
        String query = "INSERT INTO produtos (nome, descricao, preco, quantidade_estoque, tamanho, cor) VALUES (?, ?, ?, ?, ? ,?)";

        // Usando try-with-resources para garantir que a conexão e o statement sejam fechados corretamente
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Definindo os valores dos parâmetros da consulta
            pstmt.setString(1, this.getNome());
            pstmt.setString(2, this.getDescricao());
            pstmt.setDouble(3, this.getPreco());
            pstmt.setInt(4, this.getQuantidadeEstoque());
            pstmt.setString(5, this.getTamanho());
            pstmt.setString(6, this.getCor());

            // Executando a consulta e verificando se o produto foi adicionado com sucesso
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            // Tratamento de exceções
            e.printStackTrace();
        }
    }
    @Override
    public void atualizarEstoque(int novaQuantidade) {
String query = "UPDATE produtos SET quantidade_estoque = ? WHERE id = ?";

// Usando try-with-resources para garantir que a conexão e o statement sejam fechados corretamente
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            //Definindo os valores dos parametros da consulta
            pstmt.setInt(1,novaQuantidade); // adicionando as novas quantidades
            pstmt.setInt(2,getId()); // o id do produto desejado

            // Executando a consulta e verificando se o estoque foi atualizado com sucesso
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Estoque atualizado com sucesso!");
            }
        } catch (SQLException e) {
            // Tratamento de exceções
            e.printStackTrace();
        }
    }

    @Override
    public void removerProduto() {

    }
}
