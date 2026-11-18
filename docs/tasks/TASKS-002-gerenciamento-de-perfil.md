# TASKS-002 - gerenciamento-de-perfil
Responsabilidade do arquivo: representa as tarefas derivadas da SPEC-002 e acompanha o desenvolvimento ao longo das sprints

*SPEC relacionada: * SPEC-002

## Tarefas

1. Back-end

Persistência e segurança

### T001 - Adequar a persistência aos dados do perfil
- Camada: back-end / banco de dados
- Status: A fazer
- Dependências: Nenhuma.
- Critérios de conclusão:
  - Garantir a persistência dos campos do perfil definidos na SPEC-002: nome, e-mail, hash da senha, avatar, tipo de perfil, data de nascimento e gênero do Aluno e instituição/escola do Mentor.
  - Definir a restrição de unicidade do nome/nickname e aplicar os limites mínimo e máximo definidos para esse campo.
  - Permitir que instituição/escola do Mentor fique vazia.
  - Garantir um avatar válido para cada usuário, incluindo contas existentes sem avatar.
  - Preparar a migração para dados existentes sem renomear automaticamente nomes duplicados; identificar duplicidades antes de aplicar a restrição única.

### T002 - Disponibilizar o contexto autenticado do usuário
- Camada: back-end
- Status: A fazer
- Dependências: T014 (TASKS-001).
- Critérios de conclusão:
  - Permitir que as operações de perfil identifiquem a conta autenticada a partir do mecanismo de autenticação adotado.
  - Impedir que o cliente escolha outro usuário enviando um identificador arbitrário.
  - Exigir autenticação para consultar ou atualizar dados de perfil.

### T003 - Criar DTOs e consultas para dados de perfil
- Camada: back-end
- Status: A fazer
- Dependências: T001 e T002.
- Critérios de conclusão:
  - Criar DTO de resposta com os dados comuns da conta e os campos específicos do perfil Aluno ou Mentor.
  - Criar DTO de atualização parcial sem permitir alteração de e-mail ou tipo de perfil.
  - Criar DTO próprio para alteração de senha com senha atual, nova senha e confirmação.
  - Não incluir `senha_hash` nas respostas.
  - Implementar consultas para localizar a conta autenticada e carregar o perfil correspondente.

### T004 - Implementar consulta do próprio perfil
- Camada: back-end
- Status: A fazer
- Dependências: T003.
- Critérios de conclusão:
  - Disponibilizar `GET /usuarios/me` para retornar o perfil da conta autenticada.
  - Retornar data de nascimento e gênero para Aluno e instituição/escola para Mentor.
  - Retornar e-mail, avatar e tipo de perfil para apresentação em modo de visualização.
  - Não retornar a senha nem seu hash.

### T005 - Implementar atualização parcial do perfil
- Camada: back-end
- Status: A fazer
- Dependências: T003 e T004.
- Critérios de conclusão:
  - Disponibilizar `PATCH /usuarios/me` para atualizar somente os campos enviados e permitidos para o perfil autenticado.
  - Atualizar nome/nickname, avatar e os campos editáveis específicos de Aluno ou Mentor.
  - Não aceitar alteração de e-mail ou tipo de perfil.
  - Validar obrigatoriedade dos campos, limites do nome/nickname, data não futura e gênero `M`, `F` ou `O` para Aluno.
  - Permitir remover o valor de instituição/escola do Mentor.
  - Persistir a operação de forma transacional, preservando os dados anteriores se houver falha.

### T006 - Disponibilizar e validar o catálogo de avatares
- Camada: back-end
- Status: A fazer
- Dependências: T001.
- Critérios de conclusão:
  - Manter a lista oficial dos ícones definidos pela equipe com identificadores/caminhos estáveis.
  - Disponibilizar `GET /avatares` com as opções do catálogo para a interface.
  - Validar em `PATCH /usuarios/me` que o avatar informado pertence ao catálogo.
  - Rejeitar caminhos ou arquivos enviados pelo usuário; não implementar upload de imagens próprias.
  - Garantir que a atualização do avatar retornado pela API possa ser usada nas demais áreas da plataforma.

### T007 - Implementar alteração segura de senha no perfil
- Camada: back-end
- Status: A fazer
- Dependências: T002 e T003.
- Critérios de conclusão:
  - Disponibilizar operação autenticada para alteração de senha sem incluir a senha em `PATCH /usuarios/me`.
  - Receber senha atual, nova senha e confirmação.
  - Validar a senha atual usando o hash armazenado e rejeitar a operação quando estiver incorreta.
  - Aplicar as regras de segurança de senha da SPEC-001.
  - Rejeitar nova senha igual à senha atual ou diferente da confirmação.
  - Armazenar somente o hash da nova senha e manter o hash anterior se houver falha.

### T008 - Padronizar erros das operações de perfil
- Camada: back-end
- Status: A fazer
- Dependências: T004, T005, T006 e T007.
- Critérios de conclusão:
  - Tratar nome/nickname duplicado, campos inválidos, avatar fora do catálogo e senha atual incorreta.
  - Tratar falhas de persistência sem confirmar sucesso.
  - Retornar códigos HTTP e mensagens que permitam à interface identificar e apresentar os erros.

2. Front-end

Perfil

### T009 - Criar a tela de visualização do perfil
- Camada: front-end
- Status: A fazer
- Dependências: T004.
- Critérios de conclusão:
  - Permitir acesso ao perfil a partir do avatar/área de conta do usuário autenticado.
  - Apresentar os dados comuns da conta e identificar o tipo de perfil.
  - Apresentar data de nascimento e gênero para Aluno e instituição/escola para Mentor.
  - Exibir o e-mail e o avatar sem disponibilizar edição do e-mail.
  - Não exibir a senha ou seu hash.

### T010 - Implementar edição dos campos do perfil
- Camada: front-end
- Status: A fazer
- Dependências: T009.
- Critérios de conclusão:
  - Permitir alternar entre visualização e edição do perfil conforme RN01.
  - Aluno poderá editar nome/nickname, data de nascimento, gênero e avatar.
  - Mentor poderá editar nome/nickname, instituição/escola e avatar.
  - Manter e-mail e tipo de perfil somente para leitura.
  - Disponibilizar ações para salvar alterações ou cancelar e descartar os valores não salvos.

### T011 - Integrar consulta e atualização do perfil com a API
- Camada: front-end
- Status: A fazer
- Dependências: T004, T005, T009 e T010.
- Critérios de conclusão:
  - Carregar os dados usando `GET /usuarios/me`.
  - Enviar somente os campos alterados para `PATCH /usuarios/me`.
  - Tratar respostas de sucesso e erro, mantendo os dados anteriores em caso de falha.
  - Exibir mensagem temporária após salvar com sucesso.

### T012 - Implementar seleção e atualização do avatar
- Camada: front-end
- Status: A fazer
- Dependências: T006, T009 e T010.
- Critérios de conclusão:
  - Apresentar os ícones oficiais como opções de seleção no modo de edição.
  - Enviar à API somente o identificador/caminho de uma opção do catálogo.
  - Não disponibilizar envio de imagem própria.
  - Atualizar o avatar exibido no perfil e nas demais áreas da interface após a confirmação da API.

Senha

### T013 - Implementar formulário de alteração de senha
- Camada: front-end
- Status: A fazer
- Dependências: T007 e T008.
- Critérios de conclusão:
  - Disponibilizar campos para senha atual, nova senha e confirmação da nova senha.
  - Validar no frontend os campos obrigatórios, as regras da SPEC-001 e a correspondência entre nova senha e confirmação.
  - Enviar os dados à operação autenticada de alteração de senha.
  - Apresentar mensagens para senha atual incorreta, nova senha inválida ou reutilizada e falha de comunicação.
  - Apresentar confirmação somente após a API informar sucesso.
