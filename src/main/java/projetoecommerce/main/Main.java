package projetoecommerce.main;

import projetoecommerce.database.DataBaseConnection;
import projetoecommerce.exceptions.EmailOuSenhaIncorretoException;
import projetoecommerce.product.GerenciarProdutos;
import projetoecommerce.product.Produto;
import projetoecommerce.users.ServicoDeLogin;
import projetoecommerce.users.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        GerenciarProdutos gerenciarProdutos = new GerenciarProdutos();
        //Criando um novo produto;
        gerenciarProdutos.setNome("CAlÇA JEANS");
        gerenciarProdutos.setDescricao("CALÇA JEANS DACOTA");
        gerenciarProdutos.setPreco(399.99);
        gerenciarProdutos.setQuantidadeEstoque(2);
        gerenciarProdutos.setTamanho("42");
        gerenciarProdutos.setCor("JEANS");

        // Conectar ao banco de dados e realizar operações
        try (Connection connection = DataBaseConnection.getConnection()) {
          // gerenciarProdutos.adicionarProduto(); // Cadastra o produto
           //  gerenciarProdutos.setId(6); // Escolhendo o id do produto que sera alterado a quantidade;
            // gerenciarProdutos.atualizarEstoque(5); // adicionando a nova quantidade de estoque;
            //gerenciarProdutos.removerProduto(5); // exclui produto

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

