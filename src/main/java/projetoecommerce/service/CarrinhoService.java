package projetoecommerce.service;

import projetoecommerce.exception.EstoqueInsuficienteException;
import projetoecommerce.exception.ProdutoNaoEncontradoException;
import projetoecommerce.exception.UsuarioNaoLogadoException;
import projetoecommerce.model.Produto;
import projetoecommerce.model.Usuario;
import projetoecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CarrinhoService {

    static final Logger LOGGER = Logger.getLogger(CarrinhoService.class.getName());
    private Usuario usuarioLogado;
    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    // Método para adicionar ou atualizar produto no carrinho e calcular o total
    public void atualizarOuInserir(Produto produto, int quantidade)
            throws SQLException, EstoqueInsuficienteException, UsuarioNaoLogadoException {
        verificarUsuarioLogado();
        verificarDisponibilidadeEstoque(produto, quantidade);

        double total = produto.getPreco() * quantidade;

        String queryInsert = "INSERT INTO carrinho_produtos (usuario_nome, produto_nome, quantidade, total) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantidade = quantidade + ?, total = total + ?";
        String queryUpdateEstoque = "UPDATE produtos SET quantidade_estoque = quantidade_estoque - ? WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(queryInsert);
                 PreparedStatement pstmtUpdateEstoque = conn.prepareStatement(queryUpdateEstoque)) {

                // Inserir ou atualizar o produto no carrinho
                pstmt.setString(1, usuarioLogado.getNome());
                pstmt.setString(2, produto.getNome());
                pstmt.setInt(3, quantidade);
                pstmt.setDouble(4, total);
                pstmt.setInt(5, quantidade);
                pstmt.setDouble(6, total);
                pstmt.executeUpdate();

                // Atualizar o estoque do produto
                pstmtUpdateEstoque.setInt(1, quantidade);
                pstmtUpdateEstoque.setString(2, produto.getNome());
                pstmtUpdateEstoque.executeUpdate();

                conn.commit();
                LOGGER.info("Produto adicionado/atualizado com sucesso e total calculado: " + produto.getNome() + " R$: " + total);

            } catch (SQLException e) {
                conn.rollback();
                LOGGER.severe("Erro ao adicionar/atualizar produto: " + e.getMessage());
                throw e;
            } finally {
                conn.setAutoCommit(true); // Garantir que o AutoCommit seja restaurado
            }
        }
    }

    // Método para remover produto do carrinho
    public void removerProduto(Produto produto, int quantidade)
            throws ProdutoNaoEncontradoException, SQLException, UsuarioNaoLogadoException, IllegalArgumentException {

        verificarUsuarioLogado();

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        if (!isProdutoNoCarrinho(produto)) {
            throw new ProdutoNaoEncontradoException("O produto não está no carrinho");
        }

        int quantidadeAtual = obterQuantidadeNoCarrinho(produto);

        if (quantidade > quantidadeAtual) {
            throw new IllegalArgumentException("Quantidade a remover é maior do que a quantidade no carrinho");
        }

        atualizarQuantidadeCarrinho(produto, quantidadeAtual - quantidade);
        atualizarEstoqueProduto(produto, quantidade);
        atualizarTotalCarrinho();

        // Mensagem de sucesso
        LOGGER.info("Produto removido do carrinho com sucesso: " + produto.getNome() + ". Quantidade removida: " + quantidade);
    }

    // Verifica se o produto está no carrinho
    private boolean isProdutoNoCarrinho(Produto produto) throws SQLException {
        String query = "SELECT COUNT(*) FROM carrinho_produtos WHERE usuario_nome = ? AND produto_nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuarioLogado.getNome());
            pstmt.setString(2, produto.getNome());
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    // Obtém a quantidade atual do produto no carrinho
    private int obterQuantidadeNoCarrinho(Produto produto) throws SQLException, ProdutoNaoEncontradoException {
        String query = "SELECT quantidade FROM carrinho_produtos WHERE usuario_nome = ? AND produto_nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuarioLogado.getNome());
            pstmt.setString(2, produto.getNome());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantidade");
            } else {
                throw new ProdutoNaoEncontradoException("Produto não encontrado no carrinho");
            }
        }
    }

    // Atualiza a quantidade do produto no carrinho
    private void atualizarQuantidadeCarrinho(Produto produto, int novaQuantidade) throws SQLException {
        String query = "UPDATE carrinho_produtos SET quantidade = ? WHERE usuario_nome = ? AND produto_nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, novaQuantidade);
            pstmt.setString(2, usuarioLogado.getNome());
            pstmt.setString(3, produto.getNome());
            pstmt.executeUpdate();
        }
    }

    // Atualiza o estoque do produto
    private void atualizarEstoqueProduto(Produto produto, int quantidadeRemovida) throws SQLException {
        String query = "UPDATE produtos SET quantidade_estoque = quantidade_estoque + ? WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, quantidadeRemovida);
            pstmt.setString(2, produto.getNome());
            pstmt.executeUpdate();
        }
    }

    // Atualiza o total do carrinho
    private void atualizarTotalCarrinho() throws SQLException {
        String queryUpdateTotal = "UPDATE carrinho_produtos cp " +
                "JOIN (SELECT usuario_nome, SUM(quantidade * total) AS total_carrinho " +
                "FROM carrinho_produtos GROUP BY usuario_nome) t " +
                "ON cp.usuario_nome = t.usuario_nome " +
                "SET cp.total = t.total_carrinho " +
                "WHERE cp.usuario_nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryUpdateTotal)) {
            pstmt.setString(1, usuarioLogado.getNome());
            pstmt.executeUpdate();
        }
    }

    private void verificarUsuarioLogado() throws UsuarioNaoLogadoException {
        if (usuarioLogado == null) {
            usuarioLogado = loginService.getUsuarioLogado();
            if (usuarioLogado == null) {
                throw new UsuarioNaoLogadoException("Faça o login para inserir o produto no carrinho");
            }
        }
    }

    private void verificarDisponibilidadeEstoque(Produto produto, int quantidade) throws EstoqueInsuficienteException {
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
        }
    }
}
