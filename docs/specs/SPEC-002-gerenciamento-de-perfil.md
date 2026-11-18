# SPEC-002 - Gerenciamento de Perfil

ID: 002  
Funcionalidade: Gerenciamento de perfil de Aluno e Mentor  
Status: A fazer  
Requisitos relacionados: RF003 - Gerenciar perfil

# 1. Apresentação do sistema

O GranaLab é uma plataforma educacional que conecta alunos e mentores em torno de conteúdos, atividades, turmas e campanhas educativas. A proposta é oferecer um ambiente no qual os usuários possam aprender, praticar e acompanhar resultados relacionados à educação financeira de forma interativa e rápida.

# 2. Objetivo

Permitir que um usuário autenticado consulte e atualize os dados permitidos de seu próprio perfil, altere seu avatar a partir de um catálogo fixo e solicite a redefinição de sua senha por token.

# 3. Escopo

## Dentro do escopo

- Exibir os dados de perfil do Aluno ou Mentor autenticado.
- Alternar a mesma tela entre modo de visualização e modo de edição.
- Atualizar nome/nickname, avatar e os campos editáveis específicos do perfil.
- Redefinir a senha por token para Aluno ou Mentor, inclusive quando o usuário já estiver autenticado.
- Atribuir aleatoriamente um avatar do catálogo durante a criação da conta. O usuário não escolhe avatar durante o cadastro; poderá trocá-lo após entrar na conta.

## Fora do escopo

- Cadastro inicial, exceto pela atribuição automática do avatar inicial definida nesta SPEC.
- Login do usuário.
- Upload de imagens próprias para avatar.
- Alteração de e-mail ou tipo de perfil.

# 4. Atores envolvidos

- Usuário autenticado com perfil de Aluno.
- Usuário autenticado com perfil de Mentor.

O usuário só pode consultar e alterar os dados associados à própria conta. O backend deve obter sua identidade a partir do contexto autenticado, sem confiar em um identificador de usuário enviado pelo frontend.

# 5. Dados de entrada e apresentação

A tela apresenta dados já salvos e alterna entre modo de visualização e modo de edição. E-mail e tipo de perfil são somente leitura. O sistema nunca deve exibir a senha armazenada ou seu hash; a senha é alterada pelo fluxo de redefinição por token.

## Perfil do Aluno

| Dado | Apresentação | Edição |
|---|---|---|
| Nome/nickname | Exibido | Editável; único; mínimo de 5 e máximo de 100 caracteres |
| Data de nascimento | Exibida | Editável; não pode ser futura |
| Gênero | Exibido | Editável; valores aceitos: `M`, `F` e `O` |
| E-mail | Exibido | Somente leitura |
| Avatar | Exibido | Editável somente pela seleção no catálogo |
| Tipo de perfil | Exibido | Somente leitura |
| Senha | Não exibir valor | Ação para solicitar redefinição por token |

## Perfil do Mentor

| Dado | Apresentação | Edição |
|---|---|---|
| Nome/nickname | Exibido | Editável; único; mínimo de 5 e máximo de 100 caracteres |
| Instituição de ensino/escola | Exibida | Editável e opcional; pode ser removida |
| E-mail | Exibido | Somente leitura |
| Avatar | Exibido | Editável somente pela seleção no catálogo |
| Tipo de perfil | Exibido | Somente leitura |
| Senha | Não exibir valor | Ação para solicitar redefinição por token |

## Catálogo de avatares

Os avatares são ícones definidos pela equipe e disponibilizados como recursos da aplicação. Os arquivos atuais estão em `frontend/resources/img/avatars/` e seus nomes são os identificadores estáveis do catálogo:

- `onca.png`
- `garca.png`
- `arara.png`
- `loboguara.png`
- `porquinho.png`
- `tartaruga.png`

O caminho relativo estável do arquivo identifica cada opção, por exemplo, `/resources/img/avatars/tartaruga.png`.

O valor associado ao avatar deve ser persistido no campo existente `usuario.url_avatar` e retornado ao frontend. O backend deve escolher aleatoriamente uma opção válida do catálogo na criação da conta, persistir a escolha e retorná-la; o cadastro não recebe uma escolha de avatar do usuário. Na tela de perfil, o frontend apresenta as opções do catálogo e envia a opção escolhida. O backend valida o valor recebido contra o catálogo e rejeita caminhos ou identificadores não autorizados.

Os nomes/caminhos dos arquivos devem permanecer estáveis depois de associados a contas. Se um arquivo for renomeado ou removido, os valores persistidos e a lista oficial do catálogo devem ser atualizados de forma consistente.

# 6. Regras de negócio

**RN01 — Modo de edição:** O perfil é inicialmente exibido em modo de visualização. O usuário deve selecionar “Editar perfil” para habilitar os campos editáveis na mesma tela.

**RN02 — Campos protegidos:** E-mail e tipo de perfil não podem ser alterados por esta funcionalidade.

**RN03 — Alterações parciais:** O sistema deve permitir salvar somente os campos alterados. A ausência de um campo no pedido de atualização significa que ele deve permanecer inalterado. A instituição/escola do Mentor pode ser informada vazia para remover o valor existente.

**RN04 — Autorização:** Somente usuários autenticados podem consultar ou alterar o próprio perfil. O backend deve identificar o usuário autenticado e impedir que ele acesse ou modifique o perfil de outra conta.

**RN05 — Nome/nickname único:** O campo Nome, também referido como nickname nesta SPEC, deve possuir no mínimo 5 e no máximo 100 caracteres e ser único entre os usuários. O valor atual do próprio usuário não deve ser tratado como duplicidade ao salvar sem alteração.

**RN06 — Validação dos campos:** Campos obrigatórios do perfil não podem ser esvaziados. A data de nascimento do Aluno não pode ser futura. O gênero do Aluno deve ser `M`, `F` ou `O`. A instituição/escola do Mentor é opcional e limitada a 100 caracteres.

**RN07 — Salvamento:** As alterações são persistidas somente após o usuário selecionar “Salvar alterações”. O backend deve aplicar as alterações da operação de forma consistente; em caso de falha, os dados anteriormente salvos devem permanecer intactos.

**RN08 — Cancelamento:** Ao selecionar “Cancelar”, o sistema descarta todas as alterações ainda não salvas e retorna ao modo de visualização com os últimos dados persistidos.

**RN09 — Redefinição da senha:** A senha é alterada exclusivamente pelo fluxo de redefinição por token descrito na SPEC-001, tanto para usuários autenticados quanto para usuários deslogados. Não é solicitada a senha atual. O novo valor deve seguir as regras de senha da SPEC-001: mínimo de 8 caracteres e sem espaços em branco.

**RN10 — Não reutilização:** A nova senha não pode ser igual à senha atual. O backend deve comparar a nova senha com o hash armazenado sem expor ou retornar o hash.

**RN11 — Catálogo de avatar:** O usuário só pode escolher um avatar presente no catálogo oficial. Não é permitido enviar imagem própria ou escolher um caminho arbitrário.

**RN12 — Avatar inicial:** Na criação da conta, o backend deve selecionar aleatoriamente um avatar entre as opções oficiais e salvar a escolha. O formulário de cadastro não solicita avatar. A atribuição aleatória deve resultar em uma escolha persistente, que não muda a cada carregamento da tela.

**RN13 — Propagação do avatar:** Depois de alterado, o avatar atualizado deve ser usado nas demais áreas da plataforma que exibem o perfil do usuário.

**RN14 — Confirmação:** Após salvar alterações do perfil com sucesso, o sistema apresenta uma mensagem temporária na tela. Nenhuma mensagem de sucesso deve aparecer se a operação falhar.

# 7. Fluxo principal

1. O usuário acessa a plataforma e autentica-se.
2. A partir da tela inicial, seleciona o avatar ou a opção de perfil.
3. O sistema consulta e apresenta o perfil correspondente à conta autenticada.
4. O usuário seleciona “Editar perfil”.
5. O sistema habilita, na mesma tela, somente os campos editáveis para aquele tipo de perfil.
6. O usuário altera um ou mais campos e seleciona “Salvar alterações”.
7. O frontend envia somente os campos alterados.
8. O backend identifica o usuário autenticado, valida os campos e persiste as alterações.
9. O sistema atualiza a apresentação do perfil e do avatar e exibe uma mensagem temporária de sucesso.

# 8. Fluxos alternativos

## FA001 — Cancelar edição

1. No modo de edição, o usuário seleciona “Cancelar”.
2. O sistema descarta as alterações locais não salvas.
3. O sistema retorna ao modo de visualização com os dados anteriormente persistidos.

## FA002 — Redefinir senha por token

1. No perfil, o usuário seleciona a opção de redefinir senha.
2. O sistema inicia o fluxo por token da SPEC-001. Para o usuário autenticado, o e-mail da conta pode ser apresentado como destino, sem permitir sua alteração neste fluxo.
3. O sistema solicita o envio do token e apresenta o formulário para informar o token, a nova senha e sua confirmação.
4. O backend valida o token, as regras da senha e se a nova senha difere da atual.
5. Em caso de sucesso, o backend atualiza o hash da senha e invalida o token.
6. O sistema apresenta confirmação e retorna o usuário à tela de perfil.

# 9. Exceções

## EX001 — Nome/nickname já em uso

O usuário tenta salvar um nome/nickname já associado a outra conta. O sistema rejeita a alteração e informa que o nome já está em uso.

## EX002 — Dados inválidos

O sistema rejeita campos obrigatórios vazios, nome/nickname fora do limite de 5 a 100 caracteres, data de nascimento futura, gênero fora de `M`, `F` ou `O`, ou instituição/escola acima do limite. O sistema também rejeita um avatar que não pertença ao catálogo oficial.

## EX003 — Falha ao salvar alterações

Ocorre uma falha durante a persistência. O sistema informa que não foi possível salvar as alterações, mantém os dados anteriores e não apresenta confirmação de sucesso.

## EX004 — Token inválido ou expirado

O usuário informa um token inválido, expirado ou já utilizado. O sistema rejeita a redefinição e informa que é necessário solicitar um novo token. Nenhuma alteração de senha é persistida.

## EX005 — Senha inválida ou reutilizada

O sistema rejeita uma senha que não atende às regras da SPEC-001, não corresponde à confirmação ou é igual à senha atual. A senha armazenada permanece inalterada.

# 10. Pós-condições

## Em caso de sucesso no gerenciamento do perfil

- Os campos alterados são persistidos e apresentados com os novos valores.
- O usuário continua associado ao tipo de perfil original.
- O avatar atualizado é apresentado nas demais áreas que exibem o perfil.
- O sistema apresenta uma mensagem temporária de sucesso.

## Em caso de sucesso na redefinição da senha

- A nova senha é armazenada como hash, seguindo a arquitetura de segurança do projeto.
- A senha anterior deixa de ser válida.
- O token utilizado é invalidado.
- O usuário retorna à tela de perfil.

## Em caso de falha

- Os dados anteriormente salvos são preservados.
- Nenhuma alteração incompleta é persistida.
- O sistema informa o problema sem apresentar confirmação de sucesso.

# 11. Critérios de aceitação

A funcionalidade será considerada concluída quando:

- O usuário autenticado conseguir consultar somente o próprio perfil.
- O sistema apresentar os dados correspondentes ao perfil de Aluno ou Mentor.
- O e-mail e o tipo de perfil forem exibidos sem possibilidade de edição.
- O usuário alternar entre visualização e edição na mesma tela.
- Aluno puder editar nome/nickname, data de nascimento, gênero e avatar.
- Mentor puder editar nome/nickname, instituição/escola e avatar; a instituição/escola puder ficar vazia.
- O nome/nickname respeitar o limite de 5 a 100 caracteres e não puder duplicar o de outra conta.
- O sistema aceitar para gênero somente `M`, `F` ou `O` e rejeitar data de nascimento futura.
- A tela permitir salvar somente os campos alterados.
- Cancelar descartar alterações não salvas.
- Uma falha preservar os dados previamente salvos e não exibir mensagem de sucesso.
- O avatar inicial ser selecionado aleatoriamente do catálogo durante a criação da conta, persistido e mantido entre carregamentos.
- O usuário poder trocar o avatar apenas por uma opção do catálogo.
- A atualização de avatar refletir nas demais áreas da plataforma.
- A redefinição por token aplicar as regras de senha da SPEC-001 e impedir reutilização da senha atual.
- O sistema apresentar mensagem temporária após salvar com sucesso e retornar ao perfil após redefinir a senha.

# 12. Impactos na arquitetura e no banco de dados

## Banco de dados

- O campo `usuario.nome` deve aceitar de 5 a 100 caracteres e possuir restrição de unicidade.
- Antes de aplicar a restrição única em uma base existente, deve-se verificar e tratar possíveis nomes duplicados.
- `aluno.data_nascimento`, `aluno.genero` e `mentor.escola_atua` já correspondem aos dados específicos desses perfis. Data de nascimento e gênero serão editáveis para Aluno; instituição/escola será editável e opcional para Mentor.
- O avatar pode usar o campo existente `usuario.url_avatar`, limitado a 200 caracteres, para armazenar o caminho relativo estável do recurso do catálogo. O backend deve validar o valor contra as opções oficiais.
- A senha continua armazenada somente em hash. Nenhum endpoint ou resposta deve expor o hash.

## Backend e API

- `GET /usuarios/me`: retorna os dados do perfil correspondente ao usuário autenticado, incluindo os campos específicos do tipo de perfil.
- `PATCH /usuarios/me`: atualiza parcialmente o próprio perfil. A requisição aceita apenas os campos permitidos para o perfil autenticado; e-mail, tipo de perfil e senha não podem ser atualizados por esta rota.
- `GET /avatares`: disponibiliza a lista oficial de identificadores/caminhos do catálogo. Como o catálogo não contém dados privados, sua política de acesso deve ser definida na configuração de segurança.
- `POST /auth/solicitar-redefinicao-senha`: recebe o e-mail da conta e solicita o envio do token.
- `POST /auth/redefinir-senha`: recebe e-mail, token, nova senha e confirmação, valida o token e as regras de senha, atualiza o hash e invalida o token após uso.
- Todas as rotas de perfil devem exigir autenticação e operar sobre o usuário identificado pelo contexto autenticado, não por um ID fornecido pelo cliente.
- O endpoint de atualização deve validar unicidade do nome/nickname, os campos específicos do perfil e os identificadores de avatar permitidos.
- A criação da conta deve atribuir e persistir aleatoriamente um avatar do catálogo. Essa regra é registrada nesta SPEC e não altera a SPEC-001.
- Respostas devem retornar os dados públicos necessários à interface, nunca a senha ou seu hash.

## Frontend

- Uma tela de perfil com modos de visualização e edição.
- Formulários distintos conforme Aluno ou Mentor, respeitando campos editáveis e somente leitura.
- Seleção visual de avatar baseada no catálogo retornado pela API.
- Validações, mensagens de erro e confirmação temporária de sucesso.
- Fluxo de redefinição por token integrado aos endpoints de solicitação e redefinição.
- Atualização das demais áreas que exibem o avatar do usuário.

Esta SPEC define os contratos funcionais das rotas. A implementação deve seguir a arquitetura cliente-servidor, a API REST e a separação Controller, Service, Repository, Model e DTO já documentadas pelo projeto.
