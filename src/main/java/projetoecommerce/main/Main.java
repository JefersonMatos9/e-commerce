package projetoecommerce.main;

import projetoecommerce.cart.Carrinho;
import projetoecommerce.cart.CarrinhoUsuario;
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
        CarrinhoUsuario carrinho = new CarrinhoUsuario();
        ServicoDeLogin servicoDeLogin = new ServicoDeLogin();

        //Simulação de login e definição do usuario
        String email ="deco@email.com";
        String senha = "novaSenha123";

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

            //verifica se o login foi bem sucedido
            boolean loginSucesso = servicoDeLogin.login(email,senha);
            if (loginSucesso){
                //usando email para definir o usuario e definir o ID do usuario no carrinho
                Usuario usuario = servicoDeLogin.obterUsuarioPorEmail(email);
                if (usuario != null){
                    carrinho.setUsuarioId(String.valueOf(usuario.getId())); //convertendo o ID do usuario para String
                }else {
                    System.out.println("Usuario não encontrado");
                    return;
                }
            }

            //assumindo que o ID do produto no banco de dados é o numero desejado, pode ser usados outros atributos como nome.
            gerenciarProdutos.setId(1);

            //definindo a quantidade a ser adicionada ao carrinho;
            int quantidade = 2;
            //tentando adicionar o produto ao carrinho
            Produto produtoAdicionado = carrinho.adicionarProduto(gerenciarProdutos, quantidade);
            System.out.println("Produto adicionado ao carrinho " + produtoAdicionado.getNome());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmailOuSenhaIncorretoException e) {
            throw new RuntimeException(e);
        }
    }
}

