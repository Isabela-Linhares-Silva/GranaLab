# Arquitetura
Responsabilidade: explica como as diferentes partes da aplicação se comunicam e trabalham juntas.

## 1. Visão geral
O GranaLab é uma aplicação web educacional voltada à educação financeira, que conecta alunos e mentores por meio de conteúdos, atividades, turmas e campanhas educativas.
A aplicação adota uma arquitetura cliente-servidor, dividida em duas partes principais:
- Frontend: responsável pela interface gráfica, interação com os usuários, apresentação das informações e envio das solicitações ao servidor.
- Backend: responsável pelo processamento das requisições, aplicação das regras de negócio, autenticação dos usuários e comunicação com o banco de dados.

O frontend é desenvolvido utilizando HTML, CSS e JavaScript, enquanto o backend utiliza Java com Spring Boot. A persistência dos dados é realizada em um banco de dados MySQL.
A comunicação entre frontend e backend ocorre por meio de uma API REST, utilizando requisições HTTP e troca de informações no formato JSON.

De maneira geral, o funcionamento da aplicação segue o fluxo:
1. O usuário interage com a interface do sistema.
2. O frontend coleta os dados e realiza as validações necessárias na interface.
3. O frontend envia uma requisição HTTP para o backend.
4. O backend recebe a requisição por meio da camada Controller.
5. A camada Service processa as regras de negócio.
6. Quando necessário, a camada Repository realiza operações de consulta ou persistência no banco de dados.
7. O backend retorna uma resposta ao frontend.
8. O frontend interpreta a resposta e atualiza a interface apresentada ao usuário.

## 2. Tecnologias utilizadas
### Backend
- Java
- Spring Boot

### Frontend
- HTML
- CSS
- JavaScript

### Banco de dados
- MySQL

## 3. Organização do projeto
frontend/
├── index.html
├── pages/
│   └── cadastro.html
├──recources/
│    ├── css/
│    │    └── style.css
│    ├── img/
│    │    └── LogoGranaLab.png
│    └── app.js
│
backend/
├── pom.xml
├── src/
│    └── main/
│        ├── java/
│        │   └── com/
│        │       └── example/
│        │               └── granalab/
│        │                   ├── GranaLabApplication.java
│        │                   ├── model/
│        │                   │   └── Usuario.java
│        │                   ├── repository/
│        │                   │   └── UsuarioRepository.java
│        │                   ├── controller/
│        │                   │    └── UsuarioController.java
│        │                   ├── service/
│        │                   │       └── UsuarioService.java
│        │                   ├── dto/
│        │                   │     └── UsuarioResponse.java
│        │                   ├── security/
│        │                   │       └── SecurityConfig.java
│        │                   └── exception/
│        └── resources/
│            └── application.properties
docs/
├── arthitecture/
│        ├── arquiterura.md
│        └── DER.md
│
├── requirements/
│        └── requisitos.md
│
├── specs/
│        └── SPEC-001-cadastro-e-login-usuario.md
│
└── tasks/
        └── TASKS-001-cadastro-e-login-usuario.md

## 4. Arquitetura do Backend
Explicar as responsabilidades de:
- Controller: recebe e responde às requisições HTTP.
- Service: implementa as regras de negócio.
- Repository: permite acessar e manipular os dados persistidos.
- Model: representa os dados e as entidades do sistema.
- DTO: define como os dados entram e saem da aplicação.

## 5. Comunicação entre frontend e backend
A comunicação entre frontend e backend é realizada por meio de uma API REST, utilizando o protocolo HTTP.
O frontend atua como cliente da aplicação, enviando requisições para os endpoints disponibilizados pelo backend.
O backend recebe essas requisições, processa as informações e retorna respostas contendo os resultados das operações solicitadas.

### 5.1. Formato de comunicação
As informações são transmitidas preferencialmente no formato JSON (JavaScript Object Notation).
As requisições que enviam dados devem utilizar o cabeçalho Content-Type: application/json, enquanto as respostas podem utilizar application/json no cabeçalho Accept.

### 5.2. Métodos HTTP
Os principais métodos HTTP utilizados na API são:
* GET: consultar informações.
* POST: enviar informações ou solicitar a criação de novos registros.
* PUT: atualizar integralmente um recurso.
* PATCH: atualizar parcialmente um recurso.
* DELETE: remover um recurso.

A utilização de cada método deverá respeitar a finalidade da operação disponibilizada pelo endpoint.

### 5.3. Fluxo de comunicação
O processo de comunicação segue as seguintes etapas:

1. O usuário realiza uma ação na interface.
2. O JavaScript captura os dados necessários.
3. O frontend realiza a requisição HTTP para o endpoint correspondente.
4. O Controller recebe os dados e encaminha o processamento.
5. O Service executa as regras de negócio.
6. O resultado é retornado ao Controller.
7. O backend envia uma resposta HTTP ao frontend.
8. O frontend interpreta a resposta e apresenta o resultado ao usuário.

### 5.4. Tratamento das respostas
O backend deverá utilizar códigos de status HTTP adequados para representar o resultado das operações.

Exemplos:
* 200 OK: operação realizada com sucesso.
* 201 Created: recurso criado com sucesso.
* 400 Bad Request: dados enviados inválidos.
* 401 Unauthorized: usuário não autenticado.
* 403 Forbidden: usuário autenticado, mas sem permissão para acessar o recurso.
* 404 Not Found: recurso não encontrado.
* 409 Conflict: conflito com dados existentes, como e-mail já cadastrado.
* 500 Internal Server Error: erro interno inesperado.

As respostas deverão apresentar informações suficientes para que o frontend consiga identificar o resultado da operação e apresentar mensagens adequadas ao usuário.

### 5.5. Responsabilidades
O frontend não deverá acessar diretamente o banco de dados.
Toda operação envolvendo regras de negócio ou persistência deverá ser realizada pelo backend, por meio dos endpoints disponibilizados pela API.

## 6. Persistência de dados
A persistência das informações do GranaLab é realizada utilizando o banco de dados relacional MySQL.
O backend utiliza Spring Data JPA e Hibernate para realizar o mapeamento entre as entidades Java e as tabelas do banco de dados.

### 6.1. Comunicação com o banco de dados
A comunicação entre a aplicação e o MySQL ocorre por meio da camada Repository.
O fluxo de persistência segue a seguinte organização:
1. O Controller recebe uma requisição.
2. O Service executa as regras de negócio.
3. O Service solicita operações de consulta ou alteração por meio dos repositórios.
4. O Spring Data JPA utiliza o Hibernate para traduzir as operações sobre as entidades em comandos SQL.
5. O MySQL executa os comandos e retorna os resultados.
6. O resultado é devolvido ao Service, que continua o processamento.

### 6.2. Mapeamento objeto-relacional
As entidades da aplicação são representadas por classes Java, utilizando as anotações do JPA para estabelecer o mapeamento com as tabelas do banco de dados.

As principais configurações incluem:
* Identificação das entidades com @Entity.
* Definição das chaves primárias com @Id.
* Geração de identificadores quando aplicável com @GeneratedValue.
* Mapeamento de relacionamentos com anotações como @OneToOne, @OneToMany e @ManyToOne.
* Definição de restrições de unicidade e obrigatoriedade.

O modelo de dados deverá respeitar o DER documentado em DER.md.

### 6.3. Integridade e transações
As operações de persistência deverão respeitar as restrições de integridade definidas no banco de dados.
Operações que envolvam a criação ou atualização de múltiplos registros relacionados deverão utilizar transações, garantindo que todas as alterações sejam concluídas com sucesso ou revertidas em caso de falha.

No Spring, esse comportamento poderá ser implementado utilizando @Transactional na camada Service.

### 6.4. Configuração da conexão
As configurações de conexão com o banco de dados deverão ser centralizadas no arquivo application.properties ou em arquivo de configuração equivalente.

As credenciais de acesso ao banco não deverão ser expostas diretamente no código-fonte ou em repositórios públicos.

## 7. Segurança
A segurança da aplicação é responsabilidade do backend, que deverá controlar a autenticação dos usuários, a autorização de acesso às funcionalidades e a proteção das informações armazenadas.

### 7.1. Autenticação
A autenticação é responsável por verificar a identidade do usuário que deseja acessar a plataforma.
O GranaLab utilizará autenticação por e-mail e senha.

O processo deverá:
1. Receber as credenciais enviadas pelo frontend.
2. Verificar se o e-mail está associado a uma conta cadastrada.
3. Comparar a senha informada com o hash armazenado.
4. Permitir o acesso somente quando as credenciais forem válidas.
5. Estabelecer o contexto de autenticação do usuário.

O gerenciamento da autenticação deverá ser realizado utilizando os recursos do Spring Security.

A estratégia de manutenção da sessão autenticada deverá ser definida conforme a necessidade da aplicação.

### 7.2. Autorização
A autorização é responsável por determinar quais funcionalidades e recursos cada usuário poderá acessar.

O GranaLab possui dois tipos de perfil:
* Aluno: possui acesso às funcionalidades destinadas aos estudantes.
* Mentor: possui acesso às funcionalidades destinadas aos mentores.

O tipo de perfil deverá ser identificado durante a autenticação e utilizado para controlar o acesso aos recursos protegidos.
A autorização deverá ser validada no backend, não sendo suficiente apenas ocultar ou exibir elementos na interface.

### 7.3. Proteção de senhas
As senhas dos usuários nunca deverão ser armazenadas em texto puro.
O backend deverá utilizar uma função de hash específica para senhas, preferencialmente BCrypt ou Argon2, disponibilizada por meio do Spring Security.

Durante o cadastro:
1. A senha é recebida pelo backend.
2. O PasswordEncoder realiza o hash.
3. Somente o resultado do hash é armazenado no banco de dados.

Durante o login, a senha informada é comparada com o hash armazenado utilizando o mecanismo de verificação apropriado.

### 7.4. Proteção das requisições
As funcionalidades protegidas deverão exigir autenticação.

O backend deverá verificar a identidade e as permissões do usuário antes de permitir operações que envolvam dados ou recursos restritos.

Informações sensíveis, como senhas e credenciais de acesso, não deverão ser retornadas nas respostas da API.

As configurações de segurança deverão ser centralizadas na estrutura de configuração do Spring Security.

## 8. Decisões arquiteturais
Esta seção registra as principais decisões técnicas adotadas pelo projeto e suas justificativas.

O objetivo é manter uma referência para a equipe e orientar futuras implementações, inclusive aquelas realizadas com auxílio de ferramentas de inteligência artificial.

### 8.1. Arquitetura cliente-servidor
**Decisão:** utilizar arquitetura cliente-servidor, separando frontend e backend.
**Justificativa:** permitir a separação entre interface e regras de negócio, facilitando a manutenção, evolução e organização do sistema.

### 8.2. API REST para comunicação
**Decisão:** utilizar uma API REST para comunicação entre frontend e backend.
**Justificativa:** estabelecer uma comunicação padronizada por HTTP, permitindo que as partes da aplicação evoluam de maneira independente.

### 8.3. Organização do backend em camadas
**Decisão:** organizar o backend utilizando as camadas Controller, Service, Repository, Model e DTO.
**Justificativa:** separar as responsabilidades da aplicação, evitando que regras de negócio, acesso aos dados e comunicação HTTP fiquem concentrados em uma única classe.

### 8.4. Banco de dados relacional
**Decisão:** utilizar MySQL como sistema gerenciador de banco de dados.
**Justificativa:** permitir a organização dos dados em entidades relacionadas, garantindo integridade referencial e consistência das informações.

### 8.5. Persistência com JPA e Hibernate
**Decisão:** utilizar Spring Data JPA e Hibernate para realizar o mapeamento objeto-relacional.
**Justificativa:** facilitar a comunicação entre as entidades Java e as tabelas do banco de dados, reduzindo a necessidade de implementação manual de operações SQL.

### 8.6. Separação dos perfis de usuário
**Decisão:** utilizar uma estrutura comum de usuário, associada a um perfil específico de Aluno ou Mentor.
**Justificativa:** centralizar informações compartilhadas, como nome, e-mail e senha, evitando duplicação de dados e permitindo diferenciar as informações específicas de cada perfil.

### 8.7. Segurança com Spring Security
**Decisão:** utilizar Spring Security para implementar os mecanismos de autenticação e autorização.
**Justificativa:** aproveitar os recursos de segurança do ecossistema Spring, centralizando o controle de acesso e a proteção das funcionalidades.

### 8.8. Orientações para implementação
As decisões registradas neste documento deverão ser consideradas durante o desenvolvimento de novas funcionalidades.
Ao utilizar ferramentas de IA:
    - Consultar este documento antes de propor alterações arquiteturais.
    - Respeitar a separação de responsabilidades entre as camadas.
    - Utilizar as tecnologias e bibliotecas adotadas pelo projeto.
    - Não criar novas estruturas ou tecnologias sem justificativa.
    - Propor alterações arquiteturais quando forem necessárias, apresentando suas justificativas antes da implementação.

Alterações nas decisões arquiteturais deverão ser registradas neste documento, mantendo a documentação alinhada à implementação atual do sistema.
