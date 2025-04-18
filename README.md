# Sistema de Gestão Financeira Pessoal

Este sistema tem como objetivo auxiliar usuários no controle de suas finanças pessoais por meio de uma interface gráfica intuitiva e funcional, desenvolvida em Java utilizando Swing.

- Autor: Mateus Mendonça de Ávila.
- Código Unaerp: 839015

## Funcionalidades

- **Autenticação de Usuário**
    - Login e cadastro de novos usuários com validação de credenciais.
    - Cada usuário possui seus próprios dados financeiros, isolados dos demais.

- **Painel de Controle (Dashboard)**
    - Exibe resumo financeiro: saldo total, total de receitas e despesas.
    - Permite aplicar filtros por data, tipo de transação (receita ou despesa) e categoria.
    - Lista as 5 transações mais recentes de acordo com os filtros aplicados.

- **Gerenciamento de Transações**
    - Adição de receitas ou despesas com os seguintes dados:
        - Tipo (receita/despesa)
        - Valor
        - Categoria
        - Data
        - Descrição
    - Visualização de todas as transações registradas pelo usuário.
    - Tabela ordenável e não editável.

- **Gerenciamento de Categorias**
    - Criação de categorias personalizadas com nome e descrição.
    - Listagem e exclusão de categorias criadas.
    - Cada categoria pertence ao usuário autenticado.

## Estrutura do Projeto

- **model/**: Contém as classes de domínio da aplicação (`User`, `Transaction`, `Category`, `Database`).
- **controller/**: Camada de controle, responsável pela lógica de autenticação, manipulação de transações e categorias.
- **view/**: Interface gráfica da aplicação com telas de login, dashboard, transações e categorias.
- **view/components/**: Componentes reutilizáveis como combo box de categorias e tabela de transações.

## Tecnologias Utilizadas

- Java SE
- Swing (Java GUI)
- Padrão MVC (Model-View-Controller)

## Como Executar

1. Certifique-se de ter o Java JDK instalado (Java 8+).
2. Compile os arquivos `.java`.
3. Execute a classe `App.java`.
4. Cadastre um Usuário.
5. Realize o Login com esse usuário.
