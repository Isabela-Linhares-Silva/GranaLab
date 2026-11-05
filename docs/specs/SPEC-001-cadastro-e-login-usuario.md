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

### Formulário de cadastro do usuário Aluno
- Nome / Nickname
- Data de nascimento
- Gênero
- E-mail
- Senha

### Formulário de cadastro do usuário Mentor
- Nome / Nickname
- Instituição de ensino / Escola
- E-mail
- Senha

### Se o usuário estiver na tela de login, o sistema irá receber o formulário de login para usuário contendo: 
- E-mail
- Senha

# 6. Regras de negócio

RN01: O cadastro não é efetuado se algum campo do formulário estiver vazio.
RN02: O cadastro não é efetuado se o endereço de e-mail não existir ou o domínio não estiver correto.
RN03: A data de nascimento deve ser válida (sem data futura).
RN04: O cadastro não deve permitir emails repetidos
RN05: A senha deve possuir no mínimo 8 caracteres(letras, números, e caracteres)
RN06: A senha não deve permitir espaço em branco
RN07: O nickname deverá ser único(verifica se já não existe algum com nome igual)
RN08: O login só será permitido se já tiver sido feito um cadastro anteriormente
RN09:  O login não funcionará se a senha estiver incorreta
RN10: O login não funcionará se o e-mail não estiver cadastrado
RN11: O acesso após o login será feito conforme o tipo de perfil escolhido no cadastro(Aluno/Mentor)

# 7. Fluxo principal
O usuário acessa a plataforma.
O usuário seleciona a opção de criar um cadastro.
O sistema apresenta o formulário de cadastro.
O usuário preenche os campos solicitados.
O usuário envia o formulário.
O sistema verifica se os campos obrigatórios foram preenchidos.
O sistema valida o formato do e-mail.
O sistema verifica se o e-mail já está cadastrado.
O sistema verifica se a senha e a confirmação são iguais.
O sistema realiza o tratamento seguro da senha.
O sistema registra a nova conta no banco de dados.
O sistema apresenta uma mensagem de sucesso.
O usuário é direcionado para a tela de login.

# 8. Fluxos alternativos
## FA001 — Cancelar cadastro
Durante o preenchimento do formulário, o usuário seleciona a opção de cancelar.
O sistema interrompe a operação.
Os dados ainda não enviados são descartados.
O sistema retorna à tela anterior.

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

# 10. Pós-condições

Em caso de sucesso:
- A conta do usuário estará registrada no sistema.
- Os dados necessários estarão armazenados.
- A senha estará protegida por hash.
- O usuário poderá prosseguir para o login.

Em caso de falha:
- A conta não deverá ser criada parcialmente.
- O sistema deverá apresentar uma mensagem informando o problema.

# 11. Critérios de aceitação
A funcionalidade será considerada concluída quando:

- O usuário conseguir acessar o formulário de cadastro.
- O sistema permitir o preenchimento dos campos definidos.
- O sistema impedir o envio de campos obrigatórios vazios.
- O sistema validar o formato do e-mail.
- O sistema impedir o cadastro de e-mails já utilizados.
- O sistema verificar se as senhas correspondem.
- O sistema armazenar a senha de maneira segura.
- O sistema registrar corretamente uma nova conta.
- O sistema apresentar uma mensagem de sucesso após o cadastro.
- O sistema tratar adequadamente as situações de erro.

# 12. Impactos na arquitetura e no banco de dados
A implementação desta funcionalidade poderá envolver:

## Banco de dados:
- Necessidade de uma estrutura para armazenar os dados das contas.
- Identificação única para cada usuário.
- Restrição de unicidade para o e-mail.
- Armazenamento do hash da senha.

## Backend:
- Recebimento dos dados enviados pelo formulário.
- Validação das informações.
- Verificação de e-mail existente.
- Criação e armazenamento da conta.

## Frontend:
- Tela ou formulário de cadastro.
- Campos para preenchimento dos dados.
- Mensagens de validação e retorno.
- Navegação para a tela de login após o sucesso.

Atenção: os nomes das tabelas, atributos, rotas, tecnologias e organização interna deverão seguir a arquitetura efetivamente adotada pelo projeto.

# 13. Pontos pendentes de definição
Antes de aprovar esta SPEC, o grupo deverá decidir:
- Quais tipos de usuário poderão realizar cadastro?
- O tipo de perfil será escolhido durante o cadastro ou definido de outra maneira?
- Quais campos serão obrigatórios?
- Quais serão os critérios mínimos de segurança da senha?
- Será necessário confirmar o e-mail para ativar a conta?
- Após o cadastro, o usuário será direcionado ao login ou poderá entrar automaticamente?
- O sistema permitirá que um mesmo e-mail esteja associado a mais de um perfil?
