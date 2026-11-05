# TASKS-001 - cadastro-e-login-usuário
Responsabilidade do arquivo: representa as tarefas derivadas da SPEC-001 e acompanha o desenvolvimento ao longo das sprints

*SPEC relacionada: * SPEC-001

## Tarefas

1. Back-end
Estrutura e persistência
### T001 - Criar entidade Usuario
- Camada: back-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Criar a entidade Usuario conforme o modelo de dados definido.
  - Definir os atributos comuns aos usuários.
  - Configurar a identificação única do usuário.
  - Definir a restrição de unicidade do e-mail.

### T002 - Criar entidade Aluno
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Criar a entidade Aluno.
  - Implementar os atributos específicos: data de nascimento e gênero.
  - Estabelecer o relacionamento com Usuario.

### T003 - Criar entidade Mentor
  - Camada: back-end
  - Status: A fazer
  - Dependências: T001.
  - Critérios de conclusão:
    - Criar a entidade Mentor.
    - Implementar o atributo instituição de ensino/escola.
    - Estabelecer o relacionamento com Usuario.

### T004 - Criar repositórios para persistência dos usuários
- Camada: back-end
- Status: A fazer
- Dependências: T001, T002 e T003.
- Critérios de conclusão:
  - Criar os repositórios necessários.
  - Implementar operações de consulta por e-mail.
  - Implementar operações de consulta por nickname.
  - Permitir persistência dos usuários e seus respectivos perfis.

Cadastro
### T005 - Criar DTOs para recebimento dos dados de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T001, T002 e T003.
- Critérios de conclusão:
  - Criar estruturas de entrada para cadastro de Aluno.
  - Criar estruturas de entrada para cadastro de Mentor.
  - Incluir os campos necessários de acordo com cada perfil.
  - Incluir campo de confirmação de senha.

### T006 - Implementar validação dos dados de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T005.
- Critérios de conclusão:
  - Impedir envio de campos obrigatórios vazios (RN01).
  - Validar formato do e-mail (RN02).
  - Validar data de nascimento, impedindo datas futuras (RN03).
  - Validar tamanho mínimo de 8 caracteres da senha (RN05).
  - Impedir espaços em branco na senha (RN06).
  - Verificar correspondência entre senha e confirmação (EX004).

### T007 - Implementar verificação de unicidade de e-mail e nickname
- Camada: back-end
- Status: A fazer
- Dependências: T004, T005.
- Critérios de conclusão:
  - Verificar se o e-mail já está cadastrado (RN04).
  - Verificar se o nickname já está sendo utilizado (RN07).
  - Impedir a criação de contas com valores duplicados.

### T008 - Implementar serviço de cadastro de Aluno
- Camada: back-end
- Status: A fazer
- Dependências: T002, T004, T005, T006 e T007.
- Critérios de conclusão:
  - Receber os dados de cadastro do aluno.
  - Aplicar as validações necessárias.
  - Realizar o tratamento seguro da senha.
  - Registrar Usuario e Aluno no banco de dados.
  - Garantir que o cadastro não seja parcialmente persistido em caso de falha (EX005).

### T009 - Implementar serviço de cadastro de Mentor
- Camada: back-end
- Status: A fazer
- Dependências: T003, T004, T005, T006 e T007.
- Critérios de conclusão:
  - Receber os dados de cadastro do mentor.
  - Aplicar as validações necessárias.
  - Realizar o tratamento seguro da senha.
  - Registrar Usuario e Mentor no banco de dados.
  - Garantir que o cadastro não seja parcialmente persistido em caso de falha (EX005).

### T010 - Implementar endpoint de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T008 e T009.
- Critérios de conclusão:
  - Disponibilizar endpoint para cadastro de Aluno.
  - Disponibilizar endpoint para cadastro de Mentor.
  - Receber os dados enviados pelo frontend.
  - Retornar respostas adequadas para sucesso e erros de validação.

Login
### T011 - Criar DTOs para recebimento dos dados de login
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Criar estrutura para recebimento de e-mail e senha.
  - Implementar validação dos campos obrigatórios.

### T012 - Implementar serviço de autenticação
- Camada: back-end
- Status: A fazer
- Dependências: T004, T011.
- Critérios de conclusão:
  - Verificar se o e-mail está cadastrado (RN10).
  - Verificar se a senha informada corresponde à senha armazenada (RN09).
  - Impedir login de usuários não cadastrados (RN08).
  - Identificar o tipo de perfil do usuário (RN11).
  - Retornar resultado adequado para autenticação bem-sucedida ou malsucedida.

### T013 - Implementar mecanismo de autenticação e sessão
- Camada: back-end
- Status: A fazer
- Dependências: T012.
- Critérios de conclusão:
  - Implementar mecanismo de autenticação escolhido pela equipe.
  - Permitir identificação do usuário autenticado.
  - Disponibilizar as informações necessárias para identificar seu perfil.

### T014 - Implementar endpoint de login
  - Camada: back-end
  - Status: A fazer
  - Dependências: T013.
  - Critérios de conclusão:
    - Disponibilizar endpoint para autenticação.
    - Receber e-mail e senha.
    - Retornar resposta de sucesso ou erro.
    - Disponibilizar o tipo de perfil para direcionamento após o login.

Tratamento de erros
### T015 - Implementar tratamento padronizado de erros
- Camada: back-end
- Status: A fazer
- Dependências: T006, T007, T008, T009, T012.
- Critérios de conclusão:
  - Tratar campos obrigatórios não preenchidos (EX001).
  - Tratar e-mail inválido (EX002).
  - Tratar e-mail já cadastrado (EX003).
  - Tratar senhas diferentes (EX004).
  - Tratar falhas de persistência (EX005).
  - Retornar mensagens e códigos HTTP adequados.

2. Front-end

Cadastro
### T016 - Criar tela de seleção do tipo de perfil
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Apresentar as opções de cadastro como Aluno e Mentor.
  - Permitir selecionar o tipo de perfil.
  - Direcionar para o formulário correspondente.

### T017 - Criar formulário de cadastro de Aluno
- Camada: front-end
- Status: A fazer
- Dependências: T016.
- Critérios de conclusão:
  - Disponibilizar campos de nome/nickname, data de nascimento, gênero, e-mail e senha.
  - Disponibilizar campo de confirmação de senha.
  - Disponibilizar opção para cancelar o cadastro.
  - Permitir envio dos dados preenchidos.

### T018 - Criar formulário de cadastro de Mentor
- Camada: front-end
- Status: A fazer
- Dependências: T016.
- Critérios de conclusão:
  - Disponibilizar campos de nome/nickname, instituição de ensino/escola, e-mail e senha.
  - Disponibilizar campo de confirmação de senha.
  - Disponibilizar opção para cancelar o cadastro.
  - Permitir envio dos dados preenchidos.

### T019 - Implementar validações e mensagens nos formulários de cadastro
- Camada: front-end
- Status: A fazer
- Dependências: T017 e T018.
- Critérios de conclusão:
  - Sinalizar campos obrigatórios não preenchidos.
  - Apresentar mensagens de validação.
  - Informar quando as senhas não correspondem.
  - Apresentar os erros retornados pelo backend.

### T020 - Integrar formulários de cadastro com a API
- Camada: front-end
- Status: A fazer
- Dependências: T010, T017, T018 e T019.
- Critérios de conclusão:
  - Enviar os dados do aluno para o endpoint correspondente.
  - Enviar os dados do mentor para o endpoint correspondente.
  - Tratar respostas de sucesso e erro.
  - Apresentar confirmação de cadastro realizado.
  - Direcionar o usuário para a tela de login após o sucesso.

Login
### T021 - Criar tela de login
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Disponibilizar campo de e-mail.
  - Disponibilizar campo de senha.
  - Disponibilizar botão para realizar login.
  - Disponibilizar opção de recuperação de senha.

### T022 - Integrar tela de login com a API
- Camada: front-end
- Status: A fazer
- Dependências: T014 e T021.
- Critérios de conclusão:
  - Enviar e-mail e senha ao endpoint de autenticação.
  - Receber e tratar a resposta do backend.
  - Apresentar mensagens para credenciais inválidas.
  - Manter as informações necessárias para identificar a sessão autenticada.

### T023 - Implementar direcionamento conforme o tipo de perfil
- Camada: front-end
- Status: A fazer
- Dependências: T022.
- Critérios de conclusão:
  - Identificar o tipo de perfil retornado pelo backend.
  - Direcionar Alunos para o ambiente correspondente.
  - Direcionar Mentores para o ambiente correspondente.

Recuperação de senha
### T024 - Criar tela de solicitação de recuperação de senha
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Disponibilizar campo para informar o e-mail.
  - Disponibilizar opção para solicitar recuperação.
  - Apresentar retorno da solicitação.

### T025 - Criar tela de redefinição de senha
- Camada: front-end
- Status: A fazer
- Dependências: T024.
- Critérios de conclusão:
  - Disponibilizar campos para nova senha e confirmação.
  - Apresentar mensagens de validação.
  - Permitir envio da nova senha.