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
    - Implementar o atributo instituição de ensino/escola (opcional).
    - Estabelecer o relacionamento com Usuario.

### T004 - Criar repositórios para persistência dos usuários
- Camada: back-end
- Status: A fazer
- Dependências: T001, T002 e T003.
- Critérios de conclusão:
  - Criar os repositórios necessários (UsuarioRepository, AlunoRepository, MentorRepository).
  - Implementar operações de consulta por e-mail.
  - Permitir persistência dos usuários e seus respectivos perfis.

### T005 - Implementar geração de identificadores (UUID)
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Definir estratégia de geração de IDs sem AUTO_INCREMENT (conforme SPEC-001 Seção 7).
  - Gerar UUID para o id_user no momento do cadastro.
  - Reaproveitar o mesmo UUID como id_aluno ou id_mentor, garantindo que ambos tenham o mesmo identificador do usuário.

Cadastro
### T006 - Criar DTOs para recebimento dos dados de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T001, T002 e T003.
- Critérios de conclusão:
  - Criar estrutura de entrada para cadastro de Aluno (CadastroAlunoRequest): nome, dataNascimento, genero, email, senha, confirmaSenha.
  - Criar estrutura de entrada para cadastro de Mentor (CadastroMentorRequest): nome, escolaAtua, email, senha, confirmaSenha.
  - Incluir os campos necessários de acordo com cada perfil.
  - Incluir campo de confirmação de senha (não persistido no banco).
  - Especificar tamanhos máximos: Nome 100, E-mail 100, Senha 255.

### T007 - Implementar validação dos dados de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T006.
- Critérios de conclusão:
  - Impedir envio de campos obrigatórios vazios (RN01).
  - Validar formato do e-mail (RN02).
  - Validar existência do domínio do e-mail (RN02).
  - Validar data de nascimento, impedindo datas futuras (RN03).
  - Validar tamanho mínimo de 8 caracteres da senha (RN05).
  - Impedir espaços em branco na senha (RN06).
  - Verificar correspondência entre senha e confirmação (EX004).
  - Tratar exceções EX006, EX007, EX008 e EX009.

### T008 - Implementar verificação de unicidade de e-mail
- Camada: back-end
- Status: A fazer
- Dependências: T004, T006.
- Critérios de conclusão:
  - Verificar se o e-mail já está cadastrado (RN04).
  - Impedir a criação de contas com e-mail duplicado.

### T009 - Implementar serviço de cadastro de Aluno
- Camada: back-end
- Status: A fazer
- Dependências: T002, T004, T005, T006, T007, T008.
- Critérios de conclusão:
  - Receber os dados de cadastro do aluno.
  - Aplicar as validações necessárias.
  - Gerar identificador UUID para o usuário.
  - Realizar o tratamento seguro da senha (hash com BCrypt).
  - Registrar Usuario e Aluno no banco de dados, usando o mesmo id para id_user e id_aluno.
  - Garantir que o cadastro não seja parcialmente persistido em caso de falha (EX005).

### T010 - Implementar serviço de cadastro de Mentor
- Camada: back-end
- Status: A fazer
- Dependências: T003, T004, T005, T006, T007, T008.
- Critérios de conclusão:
  - Receber os dados de cadastro do mentor.
  - Aplicar as validações necessárias.
  - Gerar identificador UUID para o usuário.
  - Realizar o tratamento seguro da senha (hash com BCrypt).
  - Registrar Usuario e Mentor no banco de dados, usando o mesmo id para id_user e id_mentor.
  - Garantir que o cadastro não seja parcialmente persistido em caso de falha (EX005).

### T011 - Implementar endpoint de cadastro
- Camada: back-end
- Status: A fazer
- Dependências: T009 e T010.
- Critérios de conclusão:
  - Disponibilizar endpoint POST /auth/cadastro para cadastro de Aluno e Mentor.
  - Receber os dados enviados pelo frontend.
  - Retornar respostas adequadas para sucesso e erros de validação.
  - Não retornar senha_hash na resposta da API.

Login
### T012 - Criar DTOs para recebimento dos dados de login
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Criar estrutura para recebimento de e-mail e senha (LoginRequest).
  - Implementar validação dos campos obrigatórios.

### T013 - Implementar serviço de autenticação
- Camada: back-end
- Status: A fazer
- Dependências: T004, T012.
- Critérios de conclusão:
  - Verificar se o e-mail está cadastrado (RN10).
  - Verificar se a senha informada corresponde à senha armazenada (RN09).
  - Impedir login de usuários não cadastrados (RN08).
  - Identificar o tipo de perfil do usuário (RN11).
  - Retornar resultado adequado para autenticação bem-sucedida ou malsucedida.

### T014 - Implementar mecanismo de autenticação e sessão
- Camada: back-end
- Status: A fazer
- Dependências: T013.
- Critérios de conclusão:
  - Implementar mecanismo de autenticação escolhido pela equipe.
  - Permitir identificação do usuário autenticado.
  - Disponibilizar as informações necessárias para identificar seu perfil.

### T015 - Implementar endpoint de login
- Camada: back-end
- Status: A fazer
- Dependências: T014.
- Critérios de conclusão:
  - Disponibilizar endpoint POST /auth/login para autenticação.
  - Receber e-mail e senha.
  - Retornar resposta de sucesso ou erro.
  - Disponibilizar o tipo de perfil para direcionamento após o login.
  - Não retornar senha_hash na resposta da API.

Redefinição de senha
### T016 - Criar DTO para redefinição de senha
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Criar estrutura de entrada para redefinição de senha (RedefinicaoSenhaRequest): email, novaSenha, confirmaSenha, token.

### T017 - Implementar serviço de geração e envio de token de redefinição de senha
- Camada: back-end
- Status: A fazer
- Dependências: T004.
- Critérios de conclusão:
  - Gerar token de redefinição de senha associado ao e-mail do usuário.
  - Enviar o token por e-mail para o usuário (conforme RN12).
  - Armazenar o token com expiração definida.

### T018 - Implementar serviço de validação de token e atualização de senha
- Camada: back-end
- Status: A fazer
- Dependências: T017.
- Critérios de conclusão:
  - Validar o token de redefinição recebido.
  - Verificar se a nova senha possui no mínimo 8 caracteres e não contém espaço em branco.
  - Verificar se a nova senha e a confirmação são iguais.
  - Atualizar a senha_hash do usuário no banco de dados.
  - Invalidar o token após o uso.

### T019 - Implementar endpoint de redefinição de senha
- Camada: back-end
- Status: A fazer
- Dependências: T016, T017 e T018.
- Critérios de conclusão:
  - Disponibilizar endpoint POST /auth/redefinir-senha.
  - Receber e-mail, nova senha, confirmação e token.
  - Retornar respostas adequadas para sucesso e erros.

Segurança
### T020 - Configurar SecurityConfig para endpoints públicos
- Camada: back-end
- Status: A fazer
- Dependências: T011, T015, T019.
- Critérios de conclusão:
  - Liberar o endpoint POST /auth/cadastro para acesso sem autenticação prévia.
  - Liberar o endpoint POST /auth/login para acesso sem autenticação prévia.
  - Liberar o endpoint POST /auth/redefinir-senha para acesso sem autenticação prévia.
  - Manter os demais endpoints protegidos por autenticação.

Tratamento de erros
### T021 - Implementar tratamento padronizado de erros
- Camada: back-end
- Status: A fazer
- Dependências: T007, T008, T009, T010, T013, T018.
- Critérios de conclusão:
  - Tratar campos obrigatórios não preenchidos (EX001).
  - Tratar e-mail inválido (EX002).
  - Tratar domínio de e-mail inválido (EX006).
  - Tratar e-mail já cadastrado (EX003).
  - Tratar data de nascimento inválida (EX007).
  - Tratar senha menor que o mínimo exigido (EX008).
  - Tratar senha com espaço em branco (EX009).
  - Tratar senhas diferentes (EX004).
  - Tratar login com e-mail não cadastrado (EX010).
  - Tratar login com senha incorreta (EX011).
  - Tratar falhas de persistência (EX005).
  - Retornar mensagens e códigos HTTP adequados.

2. Front-end

Cadastro
### T022 - Criar tela de seleção do tipo de perfil
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Apresentar as opções de cadastro como Aluno e Mentor.
  - Permitir selecionar o tipo de perfil.
  - Direcionar para o formulário correspondente.

### T023 - Criar formulário de cadastro de Aluno
- Camada: front-end
- Status: A fazer
- Dependências: T022.
- Critérios de conclusão:
  - Disponibilizar campo Nome (máximo 100 caracteres).
  - Disponibilizar campo Data de nascimento (não pode ser data futura).
  - Disponibilizar campo Gênero (valores aceitos: M, F, O).
  - Disponibilizar campo E-mail (máximo 100 caracteres).
  - Disponibilizar campo Senha (máximo 255 caracteres).
  - Disponibilizar campo Confirmação de senha.
  - Disponibilizar opção para cancelar o cadastro.
  - Permitir envio dos dados preenchidos.

### T024 - Criar formulário de cadastro de Mentor
- Camada: front-end
- Status: A fazer
- Dependências: T022.
- Critérios de conclusão:
  - Disponibilizar campo Nome (máximo 100 caracteres).
  - Disponibilizar campo Instituição de ensino / Escola (opcional).
  - Disponibilizar campo E-mail (máximo 100 caracteres).
  - Disponibilizar campo Senha (máximo 255 caracteres).
  - Disponibilizar campo Confirmação de senha.
  - Disponibilizar opção para cancelar o cadastro.
  - Permitir envio dos dados preenchidos.

### T025 - Implementar validações e mensagens nos formulários de cadastro
- Camada: front-end
- Status: A fazer
- Dependências: T023 e T024.
- Critérios de conclusão:
  - Sinalizar campos obrigatórios não preenchidos.
  - Apresentar mensagens de validação.
  - Informar quando as senhas não correspondem.
  - Apresentar os erros retornados pelo backend.

### T026 - Integrar formulários de cadastro com a API
- Camada: front-end
- Status: A fazer
- Dependências: T011, T023, T024 e T025.
- Critérios de conclusão:
  - Enviar os dados do aluno para o endpoint POST /auth/cadastro.
  - Enviar os dados do mentor para o endpoint POST /auth/cadastro.
  - Tratar respostas de sucesso e erro.
  - Apresentar confirmação de cadastro realizado.
  - Direcionar o usuário para a tela de login após o sucesso.

Login
### T027 - Criar tela de login
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Disponibilizar campo de e-mail.
  - Disponibilizar campo de senha.
  - Disponibilizar botão para realizar login.
  - Disponibilizar opção de recuperação de senha.

### T028 - Integrar tela de login com a API
- Camada: front-end
- Status: A fazer
- Dependências: T015 e T027.
- Critérios de conclusão:
  - Enviar e-mail e senha ao endpoint POST /auth/login.
  - Receber e tratar a resposta do backend.
  - Apresentar mensagens para credenciais inválidas.
  - Manter as informações necessárias para identificar a sessão autenticada.

### T029 - Implementar direcionamento conforme o tipo de perfil
- Camada: front-end
- Status: A fazer
- Dependências: T028.
- Critérios de conclusão:
  - Identificar o tipo de perfil retornado pelo backend.
  - Direcionar Alunos para o ambiente correspondente.
  - Direcionar Mentores para o ambiente correspondente.

Recuperação de senha
### T030 - Criar tela de solicitação de recuperação de senha
- Camada: front-end
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Disponibilizar campo para informar o e-mail.
  - Disponibilizar opção para solicitar recuperação.
  - Apresentar retorno da solicitação.

### T031 - Criar tela de redefinição de senha
- Camada: front-end
- Status: A fazer
- Dependências: T030.
- Critérios de conclusão:
  - Disponibilizar campo para informar o token de redefinição.
  - Disponibilizar campos para nova senha e confirmação.
  - Validar que a nova senha possui no mínimo 8 caracteres.
  - Validar que a nova senha não contenha espaço em branco.
  - Validar que a nova senha e a confirmação são iguais.
  - Apresentar mensagens de validação.
  - Permitir envio da nova senha.

### T032 - Integrar tela de redefinição de senha com a API
- Camada: front-end
- Status: A fazer
- Dependências: T019, T031.
- Critérios de conclusão:
  - Enviar e-mail, nova senha, confirmação e token ao endpoint POST /auth/redefinir-senha.
  - Tratar respostas de sucesso e erro.
  - Apresentar confirmação de redefinição realizada.
  - Direcionar o usuário para a tela de login após o sucesso.