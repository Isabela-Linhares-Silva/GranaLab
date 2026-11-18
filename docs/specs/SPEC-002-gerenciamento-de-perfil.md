# SPEC-002 - Gerenciamento de Perfil 

ID: 002
Funcionalidade: Gerenciamento de perfil do usuário aluno e mentor 
Status: A fazer
Requisitos relacionados: RF003 - Gerenciar perfil

# 1. Apresentação do sistema
O GranaLab é uma plataforma educacional que conecta alunos e mentores em torno de conteúdos, atividades, turmas e campanhas educativas.
A proposta é oferecer um ambiente no qual os usuários possam aprender, praticar e acompanhar resultados relacionados à educação financeira de forma interativa e rápida.

# 2. Objetivo
O sistema deve permitir que o usuário gerencie seu perfil, podendo editar seu nome de usuário, senha, avatar e, no caso do mentor, a instituição de ensino também.

# 3. Escopo
## Dentro do escopo: 
- Listagem de dados já salvos para os dois tipos de perfil, Aluno e Mentor, visualizar e editar o que desejar.
- Opção de Redefinição de senha para qualquer tipo de usuário

## Fora do escopo: 
- Cadastro inicial
- Login do usuário

# 4. Atores envolvidos
- Usuário com cadastro de aluno
- Usuário com cadastro de mentor

Observação: as permissões específicas de cada ator serão detalhadas.

# 5. Dados de entrada
O sistema deve retornar na tela de perfil do usuário as informações de cadastro dele.

## Tela de perfil do aluno: 
- Nome EDITÁVEL
- Data de nascimento EDITÁVEL
- Gênero EDITÁVEL
- E-mail 
- Senha EDITÁVEL
- avatar EDITÁVEL

## Tela de perfil do mentor:
- Nome EDITÁVEL
- Instituição de ensino / Escola EDITÁVEL
- E-mail 
- Senha EDITÁVEL
- avatar EDITÁVEL

O usuário deve clicar no botão “Editar” para habilitar a edição de alguns dados, como está especificado quais dados são editáveis na lista acima. Se o usuário alterar algum campo que estava editável e clicar em “Salvar”, o sistema deve atualizar o(s) dado(s) atualizado(s) para o banco de dados.

# 6. Regras de negócio

RN01: O usuário deverá selecionar “Editar perfil” para habilitar as edições;
RN02: Todos os campos podem ser alterados, menos o e-mail;
RN03: O sistema deverá permitir que o usuário edite as informações de seu perfil quantas vezes desejar, sem limites;
RN04: Somente usuários que fizeram o cadastro e login poderão gerenciar os seus perfis;
RN05: A senha só poderá ser modificada após o preenchimento da senha atual, nova senha e confirmação da senha nova;
RN06: Não poderá ser permitido a modificação de senha caso a senha atual esteja errada;
RN07: O sistema não deverá permitir salvar alterações caso os campos obrigatórios estejam vazios;
RN08: A nova senha deverá manter os padrões de segurança pedidos na hora do cadastro;
RN09: As novas informações serão salvas após o usuário clicar em “Salvar alterações”;
RN10: Caso o usuário selecione a opção "Cancelar", as alterações não salvas deverão ser descartadas, mantendo os dados anteriormente registrados;
RN11: O nickname deverá respeitar o limite mínimo e máximo de caracteres permitidos pelo sistema;
RN12: A foto de perfil só poderá ser alterada com os ícones disponíveis, sem fotos próprias;
RN13: O sistema deverá atualizar a imagem de perfil exibida nas demais áreas da plataforma após a alteração do avatar;
RN14: O sistema deverá informar ao usuário quando as alterações do perfil forem salvas com sucesso(pop-up);
RN15: O sistema não deve permitir um nova senha igual a senha atual;
RN16: O sistema deverá manter o tipo de perfil do usuário (Aluno ou Mentor) inalterado durante o gerenciamento do perfil.

# 7. Fluxo principal
O usuário acessa a plataforma por meio do login.
A partir da tela inicial, o usuário clica no avatar do perfil
O sistema direciona para a tela de perfil
O usuário clica no botão “Editar perfil”
O sistema direciona para uma tela de edição de perfil com os campos editáveis
O usuário clica no botão “Salvar”, e o sistema atualiza os dados editados no banco de dados

# 8. Fluxos alternativos
## FA001 — Cancelar edição
Na tela de edição de perfil com campos editáveis, o usuário clica em “Cancelar”
O sistema mantém os dados originais

## FA002 — Redefinição de senha
Na tela de edição de perfil com campos editáveis, o usuário clica em “Redefinição de senha”.
O sistema direciona para a tela de redefinição de senha
O usuário passa pelo processo de redefinição, e o sistema salva a nova senha
O sistema redireciona o usuário para a tela de perfil

# 9. Exceções
## EX001 — Nome de usuário já existente
O usuário tenta enviar o campo “Nome” alterado e o sistema identifica que o nome já existe
O sistema mostra uma mensagem de erro no nome do usuário novo.

## EX002 — Campos inválidos
O usuário tenta enviar algum campo vazio ou com caracteres inválidos, ou imagem em formato/tamanho incompatível gera bloqueio e mensagem de erro. 642.78

## EX003 — Falha ao salvar alterações
Ocorre uma falha durante o armazenamento dos dados.
O sistema informa que não foi possível salvar a alteração.
Nenhuma confirmação de sucesso deverá ser apresentada.

# 10. Pós-condições
Em caso de sucesso(gerenciamento de perfil):
- As informações alteradas do perfil estarão atualizadas no sistema.
- Os dados modificados estarão armazenados no banco de dados.
- O usuário receberá uma mensagem informando que as alterações foram realizadas com sucesso.
- O usuário continuará associado ao seu tipo de perfil original (Aluno ou Mentor).

Em caso de sucesso(alteração de senha):
- A nova senha estará protegida por hash, caso tenha sido alterada.
- A senha anterior deixará de ser válida para autentificação

Em caso de falha:
- Os dados salvos anteriores deverão ser preservados.
- As alterações não concluídas não deverão ser registradas no banco de dados.
- O sistema deverá apresentar uma mensagem informando o problema ocorrido.

# 11. Critérios de aceitação
A funcionalidade será considerada concluída quando:

- O usuário autenticado conseguir acessar a tela de gerenciamento.
- O sistema apresentar os dados cadastrados do usuário.
- O sistema deverá identificar corretamente o tipo de perfil(Aluno ou Mentor).
- O usuário conseguir acessar a opção de edição de perfil.
- O sistema impedir a alteração do e-mail.
- O sistema rejeitar a alteração quando a senha atual estiver incorreta.
- O sistema verificar se a nova senha corresponde à sua confirmação.
- O sistema descartar as alterações quando o usuário selecionar a opção "Cancelar".
- O sistema apresentar uma mensagem de sucesso após salvar as alterações.

# 12. Impactos na arquitetura e no banco de dados
A implementação desta funcionalidade poderá envolver:

## Banco de dados:
- Necessidade de uma estrutura para armazenar e alterar os dados do perfil
- Restrição de unicidade para o atributo nome
- Permissão para editar apenas os campos mencionados como “editável” no tópico 5
- Armazenamento do hash da senha em caso de redefinição de senha

## Backend:
- Recebimento dos dados alterados e salvos pelo usuário
- Validação das informações alteradas
- Armazenamento de dados alterados

## Frontend:
- Tela de exibição do perfil com botão de “Editar Perfil”
- Campos para preenchimento dos dados no modo de editar
- Mensagens de validação e retorno.

Atenção: os nomes das tabelas, atributos, rotas, tecnologias e organização interna deverão seguir a arquitetura efetivamente adotada pelo projeto. Esta SPEC não determina tecnologias ou estruturas ainda não confirmadas.
