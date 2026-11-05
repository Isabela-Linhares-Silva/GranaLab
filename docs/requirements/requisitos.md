Responsabilidade desse arquivo: registrar os requisitos selecionados para o MVP; funciona como uma referência 

# 1. Objetivo

O GranaLab é uma plataforma educacional que conecta alunos e mentores em torno de conteúdos, atividades, turmas e campanhas educativas.

A proposta é oferecer um ambiente no qual os usuários possam aprender, praticar e acompanhar resultados relacionados à educação financeira de forma interativa e rápida.

# 2. Escopo do MVP

Descrição das funcionalidades que estarão disponíveis na primeira versão
### Autenticação e Acesso
● [RF001] Fazer cadastro: O sistema deve permitir que novos usuários realizem seu cadastro na aplicação, possibilitando o acesso às funcionalidades do sistema.

● [RF002] Efetuar login: O sistema deve permitir que o usuário se autentique na aplicação para acessar suas funcionalidades.

### Funcionalidades do Usuário
● [RF003] Gerenciar perfil: O sistema deve permitir que o usuário gerencie seu perfil, podendo editar seu nome de usuário, senha e avatar.

● [RF004] Visualizar categorias de conteúdos: O sistema deve permitir que o aluno visualize as categorias de conteúdos disponíveis, como quizzes, vídeo-aulas, "Desmonte o Anúncio" e campanhas.

● [RF007] Visualizar conteúdos de uma categoria específica: O sistema deve permitir que o aluno selecione uma categoria e visualize a listagem de conteúdos disponíveis nela.

### Funcionalidades do Aluno
● [RF009] Iniciar atividade: O sistema deve permitir que o aluno inicie uma atividade por meio do botão de "iniciar". Para realizar essa ação, o aluno deve estar cadastrado e autenticado no sistema.

● [RF010] Realizar atividade: O sistema deve permitir que o aluno realize as atividades disponíveis, incluindo atividades nativas do sistema e atividades criadas pelo professor, possibilitando a leitura de conteúdos, resposta de questões, visualização de vídeos e avanço entre os tópicos.

● [RF011] Entrar em turma: O sistema deve permitir que o aluno entre em uma turma por meio da inserção do código da turma.

● [RF012] Visualizar atividades da turma: O sistema deve permitir que o aluno visualize as atividades disponíveis em uma turma na qual esteja inserido.

### Funcionalidades Exclusiva do Mentor
● [RF016] Atribuir atividade para uma turma: O sistema deve permitir que o professor atribua uma atividade a uma turma. Para realizar essa ação, deve existir uma turma.

● [RF018] Criar turma: O sistema deve permitir que o professor crie uma turma informando seu nome. Após a criação, o sistema deve gerar um código para a turma.

# 3. Requisitos Funcionais
### [RF001] Fazer Cadastro 
● Função/Descrição: O sistema deve permitir que novos usuários realizem seu cadastro na aplicação, fornecendo dados pessoais validados, para que possam acessar as funcionalidades do sistema. 
● Atores: Usuário visitante (futuro Aluno ou Professor). 
● Prioridade: Essencial. 
● Entradas: Nome completo, e-mail, senha (e confirmação de senha), e seleção do tipo de perfil (Aluno ou Professor). 
● Fonte: Usuário visitante. 
● Saídas: Mensagem de sucesso e redirecionamento para a tela de login, ou mensagem de erro indicando o campo inválido/duplicado. 
● Destino: Banco de dados (nova conta criada) e tela do usuário. 
● Ação: O sistema recebe os dados do formulário, valida o formato do e-mail e a força da senha, verifica se o e-mail já está cadastrado, criptografa a senha (hash) e registra o novo usuário no banco de dados. 
● Pré-condição: O e-mail informado não pode estar previamente vinculado a uma conta existente. 
● Pós-condição: Novo usuário registrado no sistema, apto a realizar login. 
● Efeitos colaterais: Nenhum.

Fluxos de Interação: 

1. Fluxo Principal: 
1.1 O visitante acessa a tela de login e seleciona "Criar cadastro". 
1.2 O sistema exibe o formulário de registro. 
1.3 O usuário preenche nome, e-mail, senha e tipo de perfil (Aluno/Professor). 
1.4 O sistema valida os dados internamente. 
1.5 A conta é criada com sucesso e o usuário é redirecionado à tela de login. 
2. Fluxos Alternativos/ Extensões
[Alternativa] Cancelar cadastro: 
O usuário pode cancelar a qualquer momento antes de enviar, descartando os dados preenchidos.
- Exceções:
[Exceção] E-mail já cadastrado: 
O sistema bloqueia o cadastro e exibe aviso informando que o e-mail já está em uso. 
[Exceção] Campos em branco ou inválidos: 
O sistema impede o envio e sinaliza o campo com erro. 
### [RF002] Efetuar login 
● Função/Descrição: O sistema deve permitir que o usuário se autentique na aplicação para acessar suas funcionalidades, de acordo com seu perfil (Aluno , Professor ou Admin). 
● Atores: Usuário (Aluno, Professor ou Admin). 
● Prioridade: Essencial. 
● Entradas: E-mail e senha. 
● Fonte: Usuário.
● Saídas: Acesso liberado à tela inicial correspondente ao perfil, ou mensagem de erro. 
● Destino: interface principal da aplicação (tela do Aluno, tela do Professor ou painel administrativo do Admin).
● Ação:  O sistema recebe as credenciais, compara com os registros no banco de dados, valida a autenticação e identifica o tipo de perfil do usuário (Aluno, Professor ou Admin) para direcioná-lo à tela correta.
● Pré-condição: O usuário deve possuir um cadastro ativo no sistema. 
● Pós-condição: Usuário autenticado e sessão iniciada. 
● Efeitos colaterais: Nenhum.
Fluxos de Interação: 
1. Fluxo Principal: 
1.1 O usuário abre a aplicação e visualiza a tela de login. 
1.2 Insere e-mail e senha. 
1.3 Clica em "Entrar". 
1.4 O sistema valida as credenciais.
1.5 O acesso é liberado e o usuário é redirecionado à tela inicial de acordo com seu perfil. 
2. Fluxos Alternativos/ Extensões
[Extensão] Criar cadastro: 
O usuário sem conta seleciona "Criar cadastro" 
[Extensão] Redefinir senha: 
O usuário aciona a opção de recuperação de senha, caso implementada. 
- Exceções:
[Exceção] Credenciais inválidas:
 Se e-mail ou senha estiverem incorretos, o acesso é negado e um alerta é exibido. 
### [RF003] Gerenciar perfil
● Função/Descrição: O sistema deve permitir que o usuário gerencie seu perfil, podendo editar seu nome de usuário, senha e avatar. 
● Atores: Usuário (Aluno ou Professor) 
● Prioridade: Essencial.
● Entradas: Novo nome de usuário, nova senha e/ou nova imagem de avatar. 
● Fonte: Usuário. 
● Saídas: Perfil atualizado e mensagem de confirmação, ou mensagem de erro. 
● Destino: Banco de dados e tela do usuário. 
● Ação: O sistema recebe os dados alterados, valida o formato e a disponibilidade do novo nome de usuário (se for chave única), aplica as regras de força de senha quando aplicável, e atualiza as informações no banco de dados. 
● Pré-condição: O usuário deve estar autenticado e na tela de perfil. 
● Pós-condição: Dados do perfil atualizados permanentemente. 
● Efeitos colaterais: A alteração do nome de usuário pode refletir na exibição do ranking da turma. 
Fluxos de Interação: 
1. Fluxo Principal: 
1.1 Na tela de perfil, o usuário seleciona "Editar perfil".
1.2 O sistema libera os campos editáveis (nome de usuário, senha, avatar). 
1.3 O usuário altera os dados desejados e clica em "Salvar". 
1.4 O sistema valida as informações e verifica duplicidade, se aplicável. 
1.5 O sistema atualiza o banco de dados e exibe mensagem de sucesso. 
2. Fluxos Alternativos/ Extensões:
[Alternativa] Cancelar edição: 
O usuário pode descartar as alterações e manter os dados originais. 
- Exceções:
[Exceção] Nome de usuário já existente: 
O salvamento é bloqueado e um alerta é exibido. 
[Exceção] Campos inválidos: 
Campo vazio ou imagem em formato/tamanho incompatível gera bloqueio e mensagem de erro. 

### [RF004] Visualizar categorias de conteúdos 
● Função/Descrição: O sistema deve permitir que o aluno visualize as categorias de conteúdos disponíveis, como quizzes, vídeo-aulas, "Desmonte o Anúncio" e campanhas. 
● Atores: Aluno.
● Prioridade: lmportante.
● Entradas: Acesso à tela principal (nenhuma entrada de dados específica). 
● Fonte: Aluno.
● Saídas: Lista/grade de categorias exibida na tela. 
● Destino: Tela do usuário. 
● Ação: O sistema consulta o banco de dados para obter as categorias de conteúdo disponíveis e as exibe organizadas na tela principal. 
● Pré-condição: O aluno deve estar autenticado. 
● Pós-condição: Categorias exibidas com sucesso, prontas para seleção. 
● Efeitos colaterais: Nenhum.
Fluxos de Interação: 
1. Fluxo Principal: 
1.1 O aluno acessa a tela principal após o login. 
1.2 O sistema consulta o banco de dados e carrega as categorias disponíveis. 
1.3 O sistema exibe as categorias (quizzes, vídeo-aulas, "Desmonte o Anúncio", campanhas) em formato de lista ou cards. 
1.4 O aluno seleciona uma categoria para prosseguir (ver RF007 - Visualizar conteúdos de uma categoria específica). 

2. Fluxos Alternativos/ Extensões
[Extensão] Buscar conteúdo: 
A partir da tela principal, o aluno pode optar por usar a barra de pesquisa em vez de navegar pelas categorias (ver RF005). 
- Exceções:
[Exceção] Falha ao carregar categorias: 
Caso ocorra erro de conexão, o sistema exibe mensagem informando indisponibilidade e opção de tentar novamente.

### [RF007]Visualizar conteúdos de uma categoria específica
● Função/Descrição:  O sistema deve permitir que o Aluno selecione uma categoria de conteúdo e visualize as atividades disponíveis pertencentes à categoria escolhida. As categorias podem representar tipos de atividade, como quizzes, conteúdos em vídeo ou outros conteúdos previamente cadastrados na plataforma.
● Atores: Aluno.
● Prioridade: Desejável.
● Entradas: Categoria selecionada pelo aluno, identificação da categoria, dados das atividades cadastradas e classificação de cada atividade por categoria.
● Fonte: Aluno, cadastro de atividades da plataforma, atividades nativas e atividades criadas pelo professor/mentor disponibilizadas ao aluno.
● Saídas: Listagem das atividades pertencentes à categoria selecionada, contendo as informações básicas necessárias para identificar cada atividade.
● Destino: Interface de navegação e listagem de conteúdos da plataforma.
● Ação: O sistema recebe a categoria selecionada pelo Aluno, identifica a categoria correspondente, consulta as atividades disponíveis associadas a ela e apresenta somente os conteúdos que pertencem à categoria escolhida.
● Pré-condição: A categoria selecionada deve estar cadastrada no sistema e deve existir acesso à área de conteúdos da plataforma. 
● Pós-condição:  O sistema exibe ao Aluno a listagem de atividades correspondentes à categoria selecionada. Nenhum cadastro ou progresso do Aluno é alterado pela consulta.
● Efeitos colaterais: A atividade apresentada na listagem poderá ser posteriormente selecionada para visualização de sua prévia, conforme o RF008. A partir da atividade selecionada, o aluno poderá iniciá-la ou cadastrar-se nela, conforme o RF009.
Fluxos de Interação: 
1. Fluxo Principal: 
1.1  O Aluno acessa a área de conteúdos da plataforma.
1.2 O sistema apresenta as categorias disponíveis.
1.3. O Aluno seleciona uma categoria específica.
1.4. O sistema recebe a identificação da categoria selecionada.
1.5. O sistema verifica se a categoria existe e está disponível.
1.6. O sistema consulta as atividades classificadas naquela categoria.
1.7. O sistema considera as atividades nativas e as atividades criadas pelo Professor/Mentor que estejam disponíveis ao Aluno.
1.8. O sistema organiza os resultados correspondentes à categoria selecionada.
1.9. O sistema exibe a listagem de atividades ao Aluno.
1.10. O Aluno pode selecionar uma das atividades apresentadas.
2. Fluxos Alternativos/ Extensões
[Extensão] Selecionar uma atividade:  
Ao selecionar uma atividade da listagem, o sistema poderá apresentar a prévia correspondente, conforme definido no [RF008].
[Alternativa] Alterar categoria:  
O Aluno seleciona outra categoria e o sistema realiza uma nova consulta, substituindo a listagem anterior pelos conteúdos da nova categoria.
[Alternativa] Categoria sem atividades disponíveis:  
O sistema informa que não existem atividades disponíveis naquela categoria no momento.
- Exceções:
[Exceção] Categoria inexistente ou indisponível:  
Caso a categoria solicitada não seja encontrada ou não esteja disponível, o sistema informa que não foi possível acessar os conteúdos solicitados e não apresenta uma listagem inválida.
[Exceção] Falha na recuperação dos conteúdos:  
O sistema informa que não foi possível carregar os conteúdos da categoria naquele momento.

### [RF009] Iniciar atividade
● Função/Descrição: O sistema deve permitir que o Aluno inicie uma atividade disponível na plataforma por meio da opção “Iniciar”. Ao realizar essa ação, o sistema deve verificar se o Aluno possui cadastro e está autenticado. Estando autenticado, o sistema associa a atividade à conta do Aluno e registra seu início no histórico, permitindo o acompanhamento posterior de seu progresso.
● Atores:  Aluno.
● Prioridade:  Essencial.
● Entradas: 
- Solicitação para iniciar uma atividade.
- Identificação do Aluno.
- Identificação da atividade selecionada.
- Estado de autenticação do Aluno.
- Situação atual da atividade em relação à conta do Aluno.
● Fonte: 
- Aluno.
- Conta do Aluno.
- Cadastro da atividade selecionada
● Saídas: 
- Confirmação do início da atividade.
- Registro da atividade associada ao Aluno.
- Registro inicial no histórico.
- Disponibilização do conteúdo da atividade para realização.
● Destino:
- Histórico de atividades do Aluno.
- Registro de atividades pessoais do Aluno.
- Interface da atividade selecionada.
● Ação: O sistema recebe a solicitação para iniciar uma atividade, verifica se o usuário possui uma sessão autenticada, identifica a atividade escolhida e consulta se ela já está associada à conta do Aluno. Caso a atividade ainda não tenha sido iniciada, o sistema registra sua associação ao Aluno, cria o registro inicial correspondente no histórico e disponibiliza a atividade para realização.
● Pré-condição: 
- O usuário deve possuir cadastro na plataforma.
- O usuário deve estar autenticado como Aluno.
- A atividade deve existir e estar disponível.
- O Aluno deve ter acesso à atividade selecionada.
● Pós-condição:
- A atividade passa a estar associada à conta do Aluno.
- O início da atividade é registrado no histórico.
- A atividade passa a possuir dados de progresso vinculados ao Aluno.
- O Aluno pode acessar o conteúdo e realizar a atividade.
- O progresso da atividade poderá posteriormente ser consultado por meio do [RF006].
● Efeitos colaterais: 
- Criação de um registro de atividade pessoal para o Aluno.
- Alteração do histórico do Aluno.
- O [RF006] passa a considerar a atividade iniciada na visualização do progresso;
- A atividade não deve ser cadastrada repetidamente caso já exista associação com o mesmo Aluno.
Fluxos de Interação: 
1. Fluxo Principal: 
1.1. O Aluno acessa uma atividade disponível na plataforma.
1.2. O sistema apresenta as informações referentes à atividade.
1.3. O Aluno seleciona a opção “Iniciar”.
1.4. O sistema recebe a identificação da atividade selecionada e a solicitação de início.
1.5. O sistema verifica se existe um usuário autenticado.
1.6. O sistema identifica a conta do Aluno autenticado.
1.7. O sistema verifica se a atividade existe e está disponível para o Aluno.
1.8. O sistema consulta se a atividade já está associada à conta do Aluno.
1.9. O sistema constata que a atividade ainda não foi iniciada pelo Aluno.
1.10. O sistema associa a atividade à conta do Aluno.
1.11. O sistema cria o registro inicial da atividade no histórico do Aluno.
1.12. O sistema registra o estado inicial de progresso da atividade.
1.13. O sistema confirma que a atividade foi iniciada.
1.14. O sistema disponibiliza ao Aluno o conteúdo da atividade para realização.
2. Fluxos Alternativos/ Extensões
[Extensão] Consulta posterior do progresso:  
Após iniciar e realizar parte da atividade, os dados registrados passam a ser considerados na visualização de progresso definida no [RF006].
[Alternativa] Atividade já iniciada:  
Ao verificar o histórico, o sistema identifica que a atividade já está associada à conta do Aluno. O sistema não cria um novo cadastro da mesma atividade e permite que o Aluno acesse a atividade existente, preservando seu progresso anteriormente registrado.
[Alternativa] Início a partir da prévia:  
O Aluno acessa a prévia definida no [RF008] e seleciona a opção “Iniciar”. O fluxo segue a partir da etapa 1.4 do Fluxo Principal.
- Exceções:
[Exceção] Usuário não autenticado:  
Ao selecionar “Iniciar”, o sistema identifica que não existe sessão autenticada. A atividade não é cadastrada e o sistema solicita que o usuário realize login antes de prosseguir.
[Exceção] Usuário sem cadastro:  
O usuário não pode iniciar uma atividade sem possuir uma conta na plataforma. Nenhuma associação ou informação de progresso é criada até que exista cadastro e autenticação válidos.
[Exceção] Atividade inexistente ou indisponível:  
Caso a atividade não seja encontrada ou não esteja disponível, o sistema não realiza o cadastro e informa que a atividade não pode ser iniciada.
[Exceção] Falha ao registrar o início da atividade:  
Caso não seja possível concluir o registro da associação entre o Aluno e a atividade, o sistema informa que a operação não pôde ser concluída e não deve manter um registro parcial ou inconsistente no histórico.

### [RF010] Realizar atividade 
● Função/Descrição: O sistema deve permitir que o Aluno realize e conclua atividades de educação financeira (nativas da plataforma ou criadas pelo mentor), considerando a leitura de textos, visualização de vídeos, navegação sequencial entre tópicos e resposta a questões, com o devido registro de progresso e computação de término.
● Atores:  Aluno, Sistema.
● Prioridade: Essencial.
● Entradas: Seleção da atividade, comandos de navegação entre conteúdos, seleção ou preenchimento de respostas de questões e comando de finalização.
● Fonte: Aluno.
● Saídas: Exibição de textos, mídias e questões, feedback visual imediato para respostas submetidas, indicação percentual, passo a passo do progresso e mensagem de conclusão de atividade.
● Destino:  Tela do aluno e banco de dados
● Ação: O sistema recupera a estrutura da atividade (seja nativa ou criada pelo mentor), gerencia o estado da sessão de realização, valida respostas enviadas, atualiza continuamente o progresso do aluno e registra o encerramento da atividade ao término.
● Pré-condição: O aluno deve estar cadastrado e ter acesso à atividade. 
● Pós-condição: Progresso e respostas registrados, atividade marcada como concluída para o aluno e adição automática do registro no histórico do aluno.  
● Efeitos colaterais: 
Fluxos de Interação: 
1. Fluxo Principal: 
1.1. O aluno seleciona a atividade desejada (nativa ou criada pelo mentor) para iniciar.
1.2. O sistema verifica o estado prévio da atividade para o aluno e exibe a primeira etapa do conteúdo.
1.3. O aluno acessa o conteúdo e realiza a interação exigida.
1.4. O Aluno clica no botão "Avançar" .
1.5. O sistema valida as entradas enviadas, salva o progresso parcial no banco de dados e atualiza a barra de progresso visual.
1.6. O sistema exibe o próximo tópico ou a próxima questão da atividade.
1.7. Os passos 1.3 a 1.6 se repetem até que o último tópico ou questão da atividade seja respondido/visualizado.
1.8. Na última etapa, o aluno clica em "Finalizar atividade".
1.9. O sistema calcula o desempenho geral (se houver questões pontuáveis), grava o registro de conclusão no banco de dados e insere a atividade concluída no histórico do aluno.
1.10. O sistema exibe a tela de conclusão com a mensagem de sucesso e o resumo do desempenho obtido.

2. Fluxos Alternativos/ Extensões
[Alternativa] Interrupção da Atividade:
Durante qualquer ponto da realização (antes do passo 1.8), caso o Aluno feche a aplicação, saia da tela ou sofra uma queda de conexão, o sistema mantém o progresso salvo até o último ponto confirmado no banco de dados.
[Alternativa] Retomar Atividade em Andamento:
Ao selecionar uma atividade que já possui progresso parcial salvo, no passo 1.2 o sistema identifica o estado e direciona o Aluno diretamente para o tópico/questão onde parou.
- Exceções:
[Exceção] Questão Obrigatória Sem Resposta:
No passo 1.4, se a etapa atual contiver uma questão obrigatória e o Aluno tentar avançar sem selecionar ou preencher uma resposta, o sistema bloqueia o avanço e exibe o alerta "Responda à questão antes de prosseguir"
### [RF011] Entrar em turma
● Função/Descrição: O sistema deve permitir que o Aluno solicite o ingresso em uma turma específica mediante a inserção de um código da turma exclusivo gerado e fornecido previamente pelo mentor.
● Atores:  Aluno, Sistema.
● Prioridade: Essencial.
● Entradas: Código da turma 
● Fonte: Aluno.
● Saídas: Confirmação de ingresso na turma com exibição dos dados da turma ou mensagem de erro em caso de código inválido/inexistente.
● Destino: Tela do Aluno e Banco de Dados (tabela de associação entre aluno e turma).
● Ação: O sistema recebe o código digitado, realiza busca no banco de dados para validar a existência e a situação da turma vinculada ao código, verifica se o aluno já pertence à turma e cria o vínculo entre o ID do aluno e o ID da turma no banco de dados.
● Pré-condição:  O aluno deve estar devidamente autenticado e possuir um código da turma válido fornecido por um mentor.
● Pós-condição: O aluno é vinculado com sucesso à turma no banco de dados e passa a ter acesso às atividades disponibilizadas para ela.
● Efeitos colaterais: Permite a exibição das atividades dessa turma no Visualizar atividades de uma turma em que está inserido e inclui o aluno nas listagens da turma para o mentor.

Fluxos de Interação: 
1. Fluxo Principal: 
1.1. O aluno acessa a opção "Entrar em uma Turma" na interface da plataforma.
1.2. O sistema exibe a interface com o campo de texto dedicado para inserção do código.
1.3. O Aluno digita o código alfanumérico fornecido pelo mentor e clica em "Entrar na turma".
1.4. O sistema consulta o banco de dados e valida o código informado.
1.5. O sistema vincula a conta do aluno à respectiva turma no banco de dados.
1.6. O sistema exibe uma mensagem de sucesso ("Você entrou na turma [Nome da Turma] com sucesso!") e redireciona o aluno para o painel principal da referida turma.
2. Fluxos Alternativos/ Extensões
- Exceções:
[Exceção] Código Inexistente ou Inválido:
No passo 1.4, se o código informado não corresponder a nenhuma turma cadastrada no banco de dados, o sistema recusa o ingresso e exibe a mensagem de erro "Código de turma inválido ou inexistente". Verifique o código e tente novamente."
[Exceção] Aluno Já Inserido na Turma:
No passo 1.4, se o sistema verificar que o aluno já está previamente vinculado à turma correspondente àquele código, o sistema impede a duplicação do vínculo e exibe a mensagem "Você já faz parte desta turma."
[Exceção] Campo do Código em Branco:
No passo 1.3, se o Aluno submeter o formulário sem preencher o campo de entrada, o sistema bloqueia o envio e exibe um alerta indicando "Por favor, digite o código da turma."
### [RF012] Visualizar atividades da turma
● Função/Descrição: O sistema deve permitir que o Aluno acesse a área dedicada de uma turma da qual já faz parte e visualize a listagem completa das atividades cadastradas e disponibilizadas nela pelo mentor.
● Atores: Aluno.
● Prioridade: Essencial.
● Entradas: Seleção de uma turma específica na qual o aluno está matriculado.
● Fonte: Aluno.
● Saídas: Exibição da lista de atividades associadas à turma selecionada, com seus respectivos nomes, prazos e status de conclusão.
● Destino:Tela do aluno
● Ação: : O sistema verifica se o aluno possui vínculo ativo com a turma solicitada, consulta no banco de dados todas as atividades vinculadas àquela turma e exibe as informações detalhadas na interface do aluno.
● Pré-condição:  O aluno deve estar autenticado e previamente vinculado à turma escolhida 
● Pós-condição: Lista de atividades da turma exibida na tela do Aluno em modo de seleção/leitura.
● Efeitos colaterais: Lista de atividades da turma exibida na tela do Aluno em modo de seleção/leitura.

Fluxos de Interação: 
1. Fluxo Principal: 
1.1. O aluno navega até a área "Minhas turmas" na plataforma.
1.2. O sistema exibe a lista de turmas nas quais o aluno está inserido.
1.3. O aluno clica sobre a turma desejada.
1.4. O sistema valida o vínculo do aluno com a turma e consulta no banco de dados a lista de atividades atribuídas a ela.
1.5. O sistema exibe a interface da turma contendo o rol de atividades disponibilizadas, destacando o status de cada uma (ex.: "Pendente", "Em Andamento" ou "Concluída").

2. Fluxos Alternativos/ Extensões
[Alternativa] Nenhuma atividade cadastrada na turma:
No passo 1.5, se a consulta ao banco de dados não retornar nenhuma atividade cadastrada para aquela turma pelo mentor, o sistema exibe uma mensagem informativa no centro da tela: "Nenhuma atividade foi disponibilizada nesta turma até o momento."
- Exceções:
[Exceção] Aluno não pertence à turma:
No passo 1.4, se por falha de integridade ou tentativa de acesso direto via URL o sistema detectar que o Aluno não possui vínculo ativo com a turma solicitada, o acesso é negado, e o sistema exibe a mensagem "Acesso negado: Você não está matriculado nesta turma" e redireciona o Aluno para a tela inicial.

### [RF016] Atribuir atividade para uma turma
● Função/Descrição: Permitir que o professor atribua uma atividade disponível no sistema a uma turma específica para que seus alunos possam realizá-la.
● Atores: Usuário Professor e Sistema.
● Prioridade: Essencial.
● Entradas: Seleção da atividade, seleção da turma e confirmação da atribuição.
● Fonte: Usuário Professor.
● Saídas: Mensagem de confirmação e atividade disponibilizada para a turma.
● Destino: Banco de dados e interface da turma.
● Ação: O sistema verifica se a atividade já está associada à turma selecionada. Caso não esteja, registra a associação entre a atividade e a turma no banco de dados e disponibiliza a atividade para os alunos.
● Pré-condição: O professor deve estar autenticado, a turma deve existir e pertencer ao professor, e a atividade deve estar disponível para atribuição.
● Pós-condição: A atividade fica associada à turma passa a estar disponível para os alunos pertencentes a ela.
● Efeitos colaterais: A atividade passa a aparecer no mural da turma e na lista de atividades dos alunos da turma como não concluída. 

Fluxos de Interação: 
1. Fluxo Principal: 
1.1. O professor acessa uma atividade disponível.
1.2. O professor seleciona a opção "Atribuir".
1.3. O sistema apresenta a lista de turmas pertencentes ao professor.
1.4. O professor seleciona a turma desejada.
1.5. O sistema apresenta uma opção para confirmar a atribuição.
1.6. O professor confirma a ação.
1.7. O sistema verifica se a atividade já está associada à turma.
1.8. O sistema registra a associação da atividade com a turma.
1.9. O sistema apresenta uma mensagem de confirmação.
2. Fluxos Alternativos/ Extensões
[Extensão] Cancelar atribuição:  
No passo 1.5, o professor cancela a operação e retorna à tela anterior sem realizar alterações.
[Extensão] Selecionar outra turma:  
No passo 1.4, o professor retorna à lista de turmas e seleciona uma turma diferente.
- Exceções:
[Exceção] Atividade já atribuída:  
No passo 1.7, caso a atividade já esteja associada à turma, o sistema informa que ela já foi atribuída e não realiza uma nova associação.
[Exceção] Falha no registro:  
No passo 1.8, caso ocorra uma falha ao salvar a associação, o sistema informa que não foi possível realizar a atribuição.

### [RF018] Criar turma 
● Função/Descrição: Permitir que o professor crie uma nova turma para organizar alunos e disponibilizar atividades educacionais.
● Atores: Usuário Professor e Sistema.
● Prioridade: Essencial.
● Entradas: Nome da turma e demais informações solicitadas no formulário de criação.
● Fonte: Usuário Professor.
● Saídas: Turma criada, código de acesso gerado e mensagem de confirmação.
● Destino: Banco de dados e interface da aplicação.
● Ação: O sistema valida os dados fornecidos, cria um identificador para a turma, gera um código de acesso e registra a nova turma associada ao professor no banco de dados.
● Pré-condição: O professor deve estar autenticado no sistema.
● Pós-condição: A turma é registrada no sistema, possui um código de acesso e passa a aparecer na lista de turmas do professor.
● Efeitos colaterais: A nova turma passa a estar disponível para gerenciamento e poderá posteriormente receber alunos e atividades.

Fluxos de Interação: 

1. Fluxo Principal: 
2. O professor acessa a tela principal.
3. O professor seleciona a opção "Criar Turma".
4. O sistema apresenta o formulário de criação da turma.
5. O professor insere o nome da turma.
6. O professor confirma a criação.
7. O sistema valida as informações fornecidas.
8. O sistema gera um código único para a turma.
9. O sistema registra a turma associada ao professor no banco de dados.
10. O sistema apresenta uma mensagem de confirmação da criação.
11. O sistema retorna à tela principal e exibe a nova turma na lista.
12. Fluxos Alternativos/ Extensões

[Extensão] Cancelar criação:
No passo 1.3 ou 1.4, o professor seleciona a opção de voltar/cancelar. O sistema abandona a operação sem criar a turma e retorna à tela anterior.

● Exceções:
[Exceção] Campo obrigatório não preenchido:  
No passo 1.6, caso o nome da turma não tenha sido informado, o sistema solicita o preenchimento do campo.
[Exceção] Falha no cadastro:  
No passo 1.8, caso ocorra uma falha ao registrar a turma, o sistema informa que não foi possível criar a turma e mantém o professor no formulário.
[Exceção] Falha na geração do código:  
No passo 1.7, caso o sistema não consiga gerar um código válido, a turma não é criada e o sistema informa o problema ao professor.

# 4. Requisitos Não Funcionais


# 5.  Funcionalidades fora do MVP
Funcionalidades que não serão implementadas nesta primeira versão