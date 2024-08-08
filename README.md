# E-Commerce

## Descrição

Este é um projeto de e-commerce desenvolvido em Java. O sistema permite a gestão de produtos, categorias e usuários, além de realizar operações básicas de compra e venda. O projeto utiliza o banco de dados MySQL para armazenar dados e foi desenvolvido utilizando o Java puro.

## Funcionalidades

- **Cadastro de Usuários:** Permite o registro e gerenciamento de usuários.
- **Gestão de Produtos:** Adiciona, edita e remove produtos do catálogo.
- **Categorias:** Organiza produtos em categorias.
- **Compra e Venda:** Realiza transações de compra e venda de produtos.
- **Relatórios:** Gera relatórios básicos sobre vendas e estoque.

## Tecnologias Utilizadas

- **Java:** Linguagem de programação principal.
- **MySQL:** Banco de dados para armazenamento de informações.
- **JDBC:** Conector Java para MySQL.
- **Maven:** Gerenciador de dependências e build.

## Pré-requisitos

- **Java JDK 17:** Certifique-se de ter o JDK 17 instalado. [Download](https://docs.aws.amazon.com/corretto/latest/userguide/installing.html)
- **MySQL:** Instale e configure o MySQL. [Download](https://dev.mysql.com/downloads/installer/)
- **MySQL Connector/J:** Incluído como dependência no projeto.

## Diagrama ER

```mermaid
erDiagram
    USER {
        int id PK "Primary Key"
        string nome
        string email
        string senha
        string endereco
        string telefone
    }
    
    PRODUCT {
        int id PK "Primary Key"
        string nome
        string descricao
        decimal preco
        int quantidade_estoque
        int categoria_id FK "Foreign Key"
    }
    
    CATEGORY {
        int id PK "Primary Key"
        string nome
        string descricao
    }
    
    ORDER {
        int id PK "Primary Key"
        int usuario_id FK "Foreign Key"
        date data
        decimal total
    }
    
    ORDER_ITEM {
        int id PK "Primary Key"
        int pedido_id FK "Foreign Key"
        int produto_id FK "Foreign Key"
        int quantidade
        decimal preco_unitario
    }
    
    USER ||--o{ ORDER : places
    ORDER ||--|{ ORDER_ITEM : contains
    PRODUCT ||--o{ ORDER_ITEM : is
    CATEGORY ||--o{ PRODUCT : includes
