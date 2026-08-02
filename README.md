# Projeto Catálogo - Spring Boot

Este projeto é uma versão moderna em Java (migrada do PHP nativo) de um sistema de catálogo eletrônico de produtos. A aplicação segue o padrão MVC (Model-View-Controller) e utiliza o ecossistema Spring para fornecer um backend robusto, em conjunto com o Thymeleaf para a renderização das telas e Bootstrap para o design responsivo.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
  - Spring Web (MVC)
  - Spring Data JPA (Persistência de Dados)
- **Thymeleaf** (Motor de templates para as views HTML)
- **MySQL** (Banco de dados relacional)
- **Lombok** (Redução de código boilerplate - getters, setters, etc.)
- **Bootstrap 5** (Framework CSS no Front-end)

## 📁 Estrutura do Projeto

O projeto segue a estrutura padrão do Maven para aplicações Spring Boot:

- `src/main/java/com/alura/catalago/`: Código-fonte Java
  - `config/`: Configurações do projeto, interceptadores de sessão (segurança).
  - `controller/`: Controladores Spring MVC (rotas da aplicação).
  - `model/`: Entidades JPA representando as tabelas do banco.
  - `repository/`: Interfaces do Spring Data JPA para comunicação com o banco.
- `src/main/resources/`:
  - `application.properties`: Configurações da aplicação (banco de dados, JPA, etc).
  - `templates/`: Páginas HTML usando a sintaxe do Thymeleaf.
  - `static/`: Arquivos estáticos (fotos de upload, CSS, JS customizados).

## ⚙️ Pré-requisitos

Para rodar este projeto na sua máquina, você vai precisar ter instalado:

- [Java Development Kit (JDK) 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Apache Maven](https://maven.apache.org/) (se não for rodar pela IDE)
- Um servidor [MySQL](https://www.mysql.com/) rodando localmente na porta 3306.

## 🛠️ Configuração e Execução

### 1. Banco de Dados
O sistema utiliza o Hibernate (JPA) com a propriedade `update`, o que significa que **ele criará as tabelas no banco de dados automaticamente** ao iniciar pela primeira vez.

Você precisa garantir que o schema/banco chamado `db_prog_web` exista no seu MySQL, ou que o usuário tenha permissão para criá-lo.

Abra o arquivo `src/main/resources/application.properties` e ajuste as credenciais (se necessário) para baterem com as do seu MySQL local:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_prog_web?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

### 2. Rodando o Projeto

Se você estiver usando uma **IDE** (como IntelliJ IDEA, Eclipse ou VS Code), basta procurar a classe `CatalagoApplication.java` e executar a função `main`.

Se preferir rodar via **Terminal** (linha de comando), vá até a pasta raiz do projeto (onde está o `pom.xml`) e execute:
```bash
mvn spring-boot:run
```
*(Ou `mvn clean install` para apenas compilar o arquivo `.jar`)*

### 3. Acessando o Sistema

Com o projeto no ar, você pode acessar:

- **Vitrine (Pública):** [http://localhost:8080/](http://localhost:8080/) ou [http://localhost:8080/loja](http://localhost:8080/loja)
- **Painel Administrativo:** [http://localhost:8080/usuario/login](http://localhost:8080/usuario/login)

> **Nota:** Como as tabelas serão criadas vazias no primeiro acesso, se você não tiver usuários, precisará inserir manualmente o primeiro usuário administrador direto no banco de dados, ou desativar temporariamente o interceptador no código (`AuthInterceptor.java`) para conseguir acessar a tela de cadastro e criar o primeiro admin.

## 📝 Regras de Negócio e Segurança
- O sistema possui um `AuthInterceptor` que barra o acesso a páginas administrativas (como listagem e cadastro de produtos e usuários) caso não exista uma sessão HTTP ativa.
- Upload de imagens dos produtos é salvo diretamente na pasta `/static/fotos` sob o timestamp do upload.
