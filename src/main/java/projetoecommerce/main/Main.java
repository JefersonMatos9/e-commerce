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
        gerenciarProdutos.setNome("CAMISETA CAVALERA");
        gerenciarProdutos.setDescricao("T-SHIRT CAVALERA");
        gerenciarProdutos.setPreco(199.99);
        gerenciarProdutos.setQuantidadeEstoque(2);
        gerenciarProdutos.setTamanho("M");
        gerenciarProdutos.setCor("VERMELHA");

        // Conectar ao banco de dados e realizar operações
        try (Connection connection = DataBaseConnection.getConnection()) {
           gerenciarProdutos.adicionarProduto(); // Cadastra o produto

           // gerenciarProdutos.setId(1); // Escolhendo o id do produto que sera alterado a quantidade;
            // gerenciarProdutos.atualizarEstoque(5); // adicionando a nova quantidade de estoque;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
