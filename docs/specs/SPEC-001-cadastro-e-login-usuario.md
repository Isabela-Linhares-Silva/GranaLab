# SPEC-001-cadastro-e-login-usuario

ID: 001
Funcionalidade: Cadastro e login de usuário 
Status: em elaboração
Requisitos relacionados: RF001 - Fazer Cadastro; RF002 - Efetuar Login

# 1. Apresentação do sistema
O GranaLab é uma plataforma educacional que conecta alunos e mentores em torno de conteúdos, atividades, turmas e campanhas educativas.
A proposta é oferecer um ambiente no qual os usuários possam aprender, praticar e acompanhar resultados relacionados à educação financeira de forma interativa e rápida.

# 2. Objetivo
O usuário deve ser capaz de se cadastrar no sistema e, consequentemente, fazer login por e-mail e senha.

# 3. Escopo
## Dentro do escopo: 
- Formulário de cadastro para os dois tipos de perfil, Aluno e Mentor
- Opção de “Esqueci a senha” para o usuário / Redefinição de senha

## Fora do escopo: 
- Regras para gerenciamento do perfil / Alteração de dados do perfil

# 4. Atores envolvidos
- Usuário sem cadastro
- Usuário com cadastro de aluno
- Usuário com cadastro de mentor

Observação: as permissões específicas de cada ator estão detalhadas em ../requirements/requisitos.md

# 5. Dados de entrada
O usuário deve selecionar o tipo de perfil que deseja, Aluno ou Mentor. Após receber essa escolha na interface, os dados de entrada vão depender dessa escolha. 

### Formulário de cadastro do usuário Aluno (todos os campos obrigatórios)
- Nome (máximo 100 caracteres)
- Data de nascimento (não pode ser data futura)
- Gênero (valores aceitos: M, F, O)
- E-mail (máximo 100 caracteres)
- Senha (máximo 255 caracteres)
- Confirmação de senha (não é armazenada no banco, serve apenas para conferência no momento do cadastro)

### Formulário de cadastro do usuário Mentor (todos os campos obrigatórios, exceto Instituição de ensino / Escola)
- Nome (máximo 100 caracteres)
- Instituição de ensino / Escola (opcional)
- E-mail (máximo 100 caracteres)
- Senha (máximo 255 caracteres)
- Confirmação de senha (não é armazenada no banco, serve apenas para conferência no momento do cadastro)

### Se o usuário estiver na tela de login, o sistema irá receber o formulário de login para usuário contendo: 
- E-mail
- Senha

# 6. Regras de negócio

RN01: O cadastro não é efetuado se algum campo obrigatório do formulário estiver vazio.
RN02: O cadastro não é efetuado se o endereço de e-mail não estiver em formato válido ou se o domínio do e-mail não existir (validação de formato e de domínio/DNS).
RN03: A data de nascimento deve ser válida (não pode ser data futura).
RN04: O cadastro não deve permitir e-mails repetidos (unicidade de e-mail).
RN05: A senha deve possuir no mínimo 8 caracteres (não é exigido caracteres especiais, maiúsculas ou números além do tamanho mínimo).
RN06: A senha não deve permitir espaço em branco.
RN08: O login só será permitido se já tiver sido feito um cadastro anteriormente.
RN09: O login não funcionará se a senha estiver incorreta.
RN10: O login não funcionará se o e-mail não estiver cadastrado.
RN11: O acesso após o login será feito conforme o tipo de perfil escolhido no cadastro (Aluno/Mentor).
RN12: Para redefinição de senha ("Esqueci a senha"), o sistema deverá gerar um token enviado por e-mail para permitir a redefinição.

# 7. Fluxo principal
O usuário acessa a plataforma.
O usuário seleciona a opção de criar um cadastro.
O sistema apresenta o formulário de cadastro.
O usuário seleciona o tipo de perfil (Aluno ou Mentor).
O sistema apresenta o formulário específico do perfil selecionado (Aluno ou Mentor), com campos diferentes para cada um.
O usuário preenche os campos solicitados.
O usuário envia o formulário.
O sistema verifica se os campos obrigatórios foram preenchidos.
O sistema valida o formato do e-mail.
O sistema verifica se o domínio do e-mail existe.
O sistema verifica se o e-mail já está cadastrado.
O sistema verifica se a senha e a confirmação são iguais.
O sistema gera o identificador do usuário (id_user).
O sistema realiza o tratamento seguro da senha (hash).
O sistema registra a nova conta na tabela usuario.
O sistema registra o perfil associado na tabela aluno (se Aluno) ou mentor (se Mentor), utilizando o mesmo id_user como identificador.
O sistema apresenta uma mensagem de sucesso.
O usuário é direcionado para a tela de login.

**Nota sobre geração de IDs:** O banco de dados não utiliza `AUTO_INCREMENT`. Opções simples para gerar os identificadores:
- **UUID:** gerar um UUID para o `id_user` e reaproveitar o mesmo valor como `id_aluno` ou `id_mentor`.
- **Sequência de aplicação:** o backend mantém um contador em memória ou consulta `MAX(id) + 1` antes de inserir, garantindo que `id_aluno` e `id_mentor` recebam o mesmo valor de `id_user`.

# 8. Fluxos alternativos
## FA001 — Cancelar cadastro
Durante o preenchimento do formulário, o usuário seleciona a opção de cancelar.
O sistema interrompe a operação.
Os dados ainda não enviados são descartados.
O sistema retorna à tela anterior.

## FA002 — Esqueci a senha / Redefinição de senha
O usuário acessa a tela de login.
O usuário seleciona a opção "Esqueci a senha".
O sistema apresenta um formulário para informar o e-mail cadastrado.
O usuário informa o e-mail e envia a solicitação.
O sistema verifica se o e-mail está cadastrado.
O sistema gera um token de redefinição de senha associado ao e-mail.
O sistema envia o token por e-mail para o usuário.
O sistema apresenta uma mensagem informando que o token foi enviado.
O usuário acessa o link de redefinição recebido por e-mail.
O sistema apresenta um formulário para inserir a nova senha e confirmação.
O usuário preenche a nova senha e a confirmação e envia.
O sistema verifica se a nova senha atende aos critérios de segurança (mínimo 8 caracteres, sem espaço em branco).
O sistema verifica se a nova senha e a confirmação são iguais.
O sistema atualiza a senha_hash do usuário no banco de dados.
O sistema apresenta uma mensagem de sucesso e direciona o usuário para a tela de login.

# 9. Exceções

## EX001 — Campos obrigatórios não preenchidos
O usuário tenta enviar o formulário com campos obrigatórios vazios.
O sistema impede o cadastro.
O sistema sinaliza os campos que precisam ser preenchidos.

## EX002 — E-mail inválido
O usuário informa um e-mail em formato inválido.
O sistema impede o cadastro.
O sistema apresenta uma mensagem informando o problema.

## EX003 — E-mail já cadastrado
O usuário informa um e-mail que já está vinculado a outra conta.
O sistema impede a criação da conta.
O sistema informa que o e-mail já está em uso.

## EX004 — Senhas diferentes
O usuário informa valores diferentes nos campos de senha e confirmação.
O sistema impede o cadastro.
O sistema informa que as senhas não correspondem.

## EX005 — Falha ao salvar cadastro
Ocorre uma falha durante o armazenamento dos dados.
O sistema informa que não foi possível concluir o cadastro.
Nenhuma confirmação de sucesso deverá ser apresentada.

## EX006 — Domínio de e-mail inválido
O usuário informa um e-mail com formato válido, mas o domínio não existe.
O sistema impede o cadastro.
O sistema apresenta uma mensagem informando que o domínio do e-mail é inválido.

## EX007 — Data de nascimento inválida
O usuário informa uma data de nascimento futura ou inválida (apenas para Aluno).
O sistema impede o cadastro.
O sistema apresenta uma mensagem informando que a data de nascimento é inválida.

## EX008 — Senha menor que o mínimo exigido
O usuário informa uma senha com menos de 8 caracteres.
O sistema impede o cadastro.
O sistema apresenta uma mensagem informando que a senha deve possuir no mínimo 8 caracteres.

## EX009 — Senha com espaço em branco
O usuário informa uma senha contendo espaço em branco.
O sistema impede o cadastro.
O sistema apresenta uma mensagem informando que a senha não deve conter espaço em branco.

## EX010 — Login: e-mail não cadastrado
O usuário informa um e-mail que não está cadastrado no sistema ao tentar fazer login.
O sistema impede o login.
O sistema apresenta uma mensagem informando que o e-mail não está cadastrado.

## EX011 — Login: senha incorreta
O usuário informa uma senha incorreta para o e-mail informado ao tentar fazer login.
O sistema impede o login.
O sistema apresenta uma mensagem informando que a senha está incorreta.

# 10. Pós-condições

Em caso de sucesso (cadastro):
- A conta do usuário estará registrada na tabela `usuario`.
- O perfil associado estará registrado na tabela `aluno` (se Aluno) ou `mentor` (se Mentor), utilizando o mesmo `id_user`.
- Os dados necessários estarão armazenados.
- A senha estará protegida por hash.
- O usuário poderá prosseguir para o login.

Em caso de sucesso (login):
- O sistema autenticará o usuário e retornará um token ou sessão válida.
- O usuário será redirecionado conforme seu tipo de perfil (Aluno ou Mentor).

Em caso de falha:
- A conta não deverá ser criada parcialmente.
- O sistema deverá apresentar uma mensagem informando o problema.

# 11. Critérios de aceitação
A funcionalidade será considerada concluída quando:

- O usuário conseguir acessar o formulário de cadastro.
- O sistema permitir o preenchimento dos campos definidos para cada perfil (Aluno e Mentor).
- O sistema impedir o envio de campos obrigatórios vazios.
- O sistema validar o formato do e-mail.
- O sistema rejeitar e-mails com domínio inválido.
- O sistema impedir o cadastro de e-mails já utilizados.
- O sistema verificar se as senhas correspondem.
- O sistema armazenar a senha de maneira segura (hash).
- O sistema registrar corretamente uma nova conta na tabela `usuario`.
- O sistema registrar o perfil associado na tabela `aluno` ou `mentor`, conforme a escolha do usuário.
- O sistema apresentar uma mensagem de sucesso após o cadastro.
- O usuário conseguir fazer login com e-mail e senha válidos.
- O sistema redirecionar o usuário conforme seu tipo de perfil (Aluno ou Mentor) após o login.
- O sistema tratar adequadamente as situações de erro (e-mail não cadastrado, senha incorreta, etc.).

# 12. Impactos na arquitetura e no banco de dados
A implementação desta funcionalidade poderá envolver:

## Banco de dados:
- Banco utilizado: `granalabteste`.
- Necessidade de uma estrutura para armazenar os dados das contas (tabela `usuario`).
- Identificação única para cada usuário (`id_user`).
- Restrição de unicidade para o e-mail.
- Armazenamento do hash da senha (`senha_hash`).
- Criação de registros nas tabelas `aluno` ou `mentor`, utilizando o mesmo `id_user` como identificador (sem `AUTO_INCREMENT`).
- O banco de dados não utiliza `AUTO_INCREMENT`; os IDs devem ser gerados pela aplicação (ex: UUID ou sequência `MAX(id) + 1`).

## Backend:
- Endpoints REST necessários:
  - `POST /auth/cadastro` — recebe os dados de cadastro (Aluno ou Mentor), valida e registra o usuário e seu perfil.
  - `POST /auth/login` — recebe e-mail e senha, autentica o usuário e retorna token/sessão.
  - `POST /auth/redefinir-senha` — solicita redefinição de senha enviando token por e-mail.
- DTOs de entrada necessários (estruturas que recebem os dados do frontend):
  - `CadastroAlunoRequest`: nome, dataNascimento, genero, email, senha, confirmaSenha.
  - `CadastroMentorRequest`: nome, escolaAtua, email, senha, confirmaSenha.
  - `LoginRequest`: email, senha.
  - `RedefinicaoSenhaRequest`: email, novaSenha, confirmaSenha, token.
  *(DTO = Data Transfer Object: objeto que transporta dados entre camadas, evitando expor as entidades JPA diretamente.)*
- Validação das informações (Bean Validation).
- Verificação de e-mail existente e domínio de e-mail.
- Tratamento seguro da senha com hash (BCrypt).
- Criação e armazenamento da conta e do perfil associado.
- Geração e validação de token de redefinição de senha.
- Retorno de token ou sessão após login bem-sucedido.

## Frontend:
- Tela ou formulário de cadastro.
- Campos para preenchimento dos dados (diferenciados por perfil Aluno/Mentor).
- Mensagens de validação e retorno.
- Navegação para a tela de login após o sucesso.
- Tela de solicitação de redefinição de senha ("Esqueci a senha").
- Tela de redefinição de senha com formulário de nova senha e confirmação.

## Segurança:
- O `SecurityConfig` deverá liberar os endpoints `/auth/cadastro`, `/auth/login` e `/auth/redefinir-senha` para acesso sem autenticação prévia.
- `senha_hash` nunca deverá ser retornado nas respostas da API.

Atenção: os nomes das tabelas, atributos, rotas, tecnologias e organização interna deverão seguir a arquitetura efetivamente adotada pelo projeto.

# 13. Pontos pendentes de definição
Antes de aprovar esta SPEC, o grupo deverá decidir:
- Quais tipos de usuário poderão realizar cadastro? → **Decidido: Aluno e Mentor (as duas únicas opções; o termo "Professor" não é utilizado).**
- O tipo de perfil será escolhido durante o cadastro ou definido de outra maneira? → **Decidido: definido durante o cadastro. O usuário preenche os dados de usuário e depois escolhe uma das opções, aparecendo o formulário da opção escolhida.**
- Quais campos serão obrigatórios? → **Decidido: Aluno — todos os campos obrigatórios. Mentor — todos os campos obrigatórios, exceto "Instituição de ensino / Escola".**
- Quais serão os critérios mínimos de segurança da senha? → **Decidido: mínimo de 8 caracteres; não exige caracteres especiais, maiúsculas ou números além do tamanho mínimo; não permite espaço em branco.**
- Será necessário confirmar o e-mail para ativar a conta? → **Decidido: Não.**
- Após o cadastro, o usuário será direcionado ao login ou poderá entrar automaticamente? → **Decidido: será direcionado ao login.**
- O sistema permitirá que um mesmo e-mail esteja associado a mais de um perfil? → **Decidido: Não, o e-mail só pode estar associado a um perfil.**
- Qual banco de dados deve ser utilizado? → **Decidido: `granalabteste`.**
