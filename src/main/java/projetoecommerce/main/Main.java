package projetoecommerce.main;

import projetoecommerce.exception.EmailOuSenhaIncorretoException;
import projetoecommerce.exception.EstoqueInsuficienteException;
import projetoecommerce.exception.ProdutoNaoEncontradoException;
import projetoecommerce.exception.UsuarioNaoLogadoException;
import projetoecommerce.model.Carrinho;
import projetoecommerce.model.Produto;
import projetoecommerce.model.Usuario;
import projetoecommerce.repository.UsuarioRepository;
import projetoecommerce.service.CarrinhoService;
import projetoecommerce.service.LoginService;
import projetoecommerce.service.ProdutoService;
import projetoecommerce.service.UsuarioService;
import projetoecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        Produto produto = new Produto();
        Carrinho carrinho = new Carrinho();
        UsuarioService usuarioService = new UsuarioService();
        ProdutoService produtoService = new ProdutoService();
        LoginService loginService = new LoginService();
        CarrinhoService carrinhoService = new CarrinhoService();

        loginService.setUsuarioService(usuarioService); // CONFIGURAÇÃO DO LoginService.
        carrinhoService.setLoginService(loginService); //CONFIGURAÇÃO DO CarrinhoService.
        carrinhoService.setUsuarioLogado(loginService.getUsuarioLogado()); //INICIALMENTE NENHUM USUÁRIO LOGADO.

        //CADASTRANDO DADOS DE UM USUARIO
        //usuario.setId(7);//ESSE COMANDO SÓ E UTILIZADO CASO QUEIRA ALTERAR ALGUM DADO DO USUARIO.

        usuario.setNome("PM TOPSSIMO");
        usuario.setEmail("pm@topssimo");
        usuario.setSenha("123456");
        usuario.setTelefone("169960215");
        usuario.setRua("Rua F");
        usuario.setNumero(361);
        usuario.setBairro("Bairro H");
        usuario.setCidade("Cidade KKK");
        usuario.setCep("12349958");

        //CADASTRANDO UM NOVO PRODUTO
        produto.setNome("TENIS NIKE");
        produto.setDescricao("TENIS NIKE RUNNING");
        produto.setPreco(799.99);
        produto.setQuantidadeEstoque(5);
        produto.setTamanho("41");
        produto.setCor("BRANCO/PRETO");


        //PASSANDO OS DADOS DE LOGIN DO USUARIO.
        //usuario.setEmail("deco@email.com");
        //usuario.setSenha("novaSenha123");

        // VERIFICAR SE O VALOR TOTAL ENTROU NA TABELA DO BANCO DE DADOS.

        //CONECTANDO AO BANCO DE DADOS E REALIZANDO OPERAÇÕES
        try (Connection connection = DatabaseConnection.getConnection()) {
            //METODOS DA CLASSE UsuarioService

            //usuarioService.cadastrar(usuario); // CADASTRANDO UM USUARIO.

            //usuarioService.obterPorEmail("josii@email.com"); //OBTENDO UM USUÁRIO POR E-MAIL.

            //usuarioService.atualizarPerfil(usuario);//ATUALIZANDO DADOS DO USUÁRIO.

            //usuario.setId(6);//SELECIONANDO O ID A SER REMOVIDO.
            //usuarioService.deletarUsuario(usuario); // REMOVENDO O USUÁRIO.

            //usuarioService.atualizarSenha("deco@email.com","miguel123");//ATUALIZA SENHA USUARIO.

            //METODOS DA CLASSE ProdutoService

            //produtoService.adicionarProduto(produto);// CADASTRANDO UM NOVO PRODUTO.

            //produtoService.removerProduto(3); //REMOVENDO PRODUTO.

            //produtoService.atualizarEstoque(1,5); //ATUALIZA O ESTOQUE DOS PRODUTOS.

            //METODO DA CLASSE LoginService
            loginService.login("paulo@email.com", "12345"); //REALIZANDO O LOGIN SE BEM SUCEDIDO O USUÁRIO SERÁ AUTENTICADO.

            Usuario usuarioLogado = loginService.getUsuarioLogado(); //OBTEM O USUARIO LOGADO.
            carrinhoService.setUsuarioLogado(usuarioLogado); //DEFINE O USUARIO LOGADO NO CARRINHO, PERMITE QUE O CARRINHO SAIBA QUAL USUARIO ESTA LOGADO AO SERVICO DE CARRINHO.

            //loginService.logout();//DESLOGANDO O USUARIO.

            //METODOS DA CLASSE CarrinhoService

            produto = produtoService.obterPorid(7); //OBTENDO O PRODUTO POR ID.
            //Produto produto2 = produtoService.obterPorid(); //OBTENDO O PRODUTO POR ID.

            //carrinhoService.atualizarOuInserir( produto, 8); //ADICIONANDO O PRODUTO AO CARRINHO
            //carrinhoService.atualizarOuInserir( produto2, 1); //ADICIONANDO O PRODUTO AO CARRINHO

            carrinhoService.removerProduto(produto, 8);
            //VERIFICAR SE OS PRODUTOS ESTAO SENDO ADICIONADOS AO BANCO DE DADOS.

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (EmailOuSenhaIncorretoException e) {
            throw new RuntimeException(e);
        } catch (UsuarioNaoLogadoException e) {
            throw new RuntimeException(e);
        } catch (ProdutoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }
}
