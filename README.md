# Sistema de Gestão Financeira Pessoal

Este projeto auxilia os usuários no controle de suas finanças por meio de uma interface gráfica intuitiva. A aplicação é escrita em Java com Swing e utiliza Hibernate para persistência de dados em banco SQLite.

**Autor:** Mateus Mendonça de Ávila  
**Código Unaerp:** 839015

---

## ✨ Funcionalidades

### 🔐 Autenticação de Usuário
- Login e cadastro com validação de credenciais.
- Cada usuário possui seus próprios dados financeiros.

### 📊 Painel de Controle (Dashboard)
- Exibe o saldo total, valores de receitas e despesas.
- Permite filtros por data, tipo de transação e categoria.
- Mostra as 5 transações mais recentes segundo os filtros.

### 💸 Gerenciamento de Transações
- Registro de receitas ou despesas com:
    - Tipo (receita/despesa)
    - Valor
    - Categoria
    - Data
    - Descrição
- Listagem de todas as transações do usuário em tabela ordenável.

### 🗂️ Gerenciamento de Categorias
- Criação, listagem e exclusão de categorias personalizadas.
- Cada categoria pertence ao usuário autenticado.

---

## 🧱 Estrutura do Projeto

```
├── model/              # Entidades JPA (User, Transaction, Category) e HibernateUtil
├── controller/         # Regras de negócio e validações
├── dao/                # Acesso a dados com Hibernate e SQLite
├── view/               # Telas da aplicação (login, dashboard, etc.)
└── view/components/    # Componentes reutilizáveis (combos, tabelas, etc.)
```

---

## 🛠 Tecnologias Utilizadas

- Java 21
- Maven
- Swing (GUI)
- Hibernate ORM
- SQLite
- SLF4J
- Padrão MVC

---

## ▶️ Como Executar

1. Instale o **JDK 21** e o **Maven**.
2. Compile o projeto:
   ```bash
   mvn clean install
   ```
3. Execute a aplicação:
   ```bash
   mvn clean compile
   ```
4. Execute o arquivo APP
5. Cadastre um usuário e faça login para começar a usar o sistema.

---
