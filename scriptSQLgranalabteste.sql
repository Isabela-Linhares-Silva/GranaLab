CREATE DATABASE granalabteste;

USE granalabteste;

create table usuario(
	id_user int primary key,
	nome varchar(100) not null,
    email varchar(100) not null UNIQUE,
    senha_hash varchar(255),
    url_avatar varchar(200),
    tipo_perfil ENUM('Mentor', 'Aluno') not null
);

create table aluno(
	id_aluno INT PRIMARY KEY, 
	nivel_perfil varchar(100) not null,
    idade int not null,
    genero ENUM('M', 'F', 'O'),
    FOREIGN KEY (id_aluno) REFERENCES usuario(id_user)
);

create table mentor(
	id_mentor INT PRIMARY KEY,
    escola_atua varchar(100) not null,
    FOREIGN KEY (id_mentor) REFERENCES usuario(id_user)
);

create table turma(
	id_turma INT PRIMARY KEY,
    codigo VARCHAR(100) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    progresso_medio INT,
    quant_alunos INT,
    id_mentor INT NOT NULL,
    FOREIGN KEY (id_mentor) REFERENCES mentor(id_mentor)
);

create table aluno_turma(
	id_aluno_turma INT PRIMARY KEY,
    id_turma int,
    id_aluno int,
    porc_desempenho int,
    FOREIGN KEY (id_turma) REFERENCES turma(id_turma),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno)
);

create table categoria(
	id_categoria INT PRIMARY KEY,
    titulo VARCHAR(100),
    descricao VARCHAR(1000)
);

create table atividade(
	id_atividade INT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(1000),
    data_criacao DATE,
    id_criador INT,
    FOREIGN KEY (id_criador) REFERENCES mentor(id_mentor),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

create table aluno_atividade(
	id_aluno_atv INT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_atv INT NOT NULL,
    UNIQUE (id_aluno, id_atv),
    data_inicio DATE,
    data_conclusao DATE,
    porc_progresso INT,
    pontuacao INT,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_atv) REFERENCES atividade(id_atividade)
);

create table turma_atividade(
	id_turma_atv INT PRIMARY KEY,
    id_turma INT NOT NULL,
    id_atv INT NOT NULL,
    UNIQUE (id_turma, id_atv),
	data_atribuicao DATE,
    descricao_mentor VARCHAR(1000),
    pontuacao_max_atribuida INT,
    quant_conclusao INT,
    FOREIGN KEY (id_turma) REFERENCES turma(id_turma),
    FOREIGN KEY (id_atv) REFERENCES atividade(id_atividade)
);

create table conteudo(
	id_conteudo INT PRIMARY KEY,
    titulo VARCHAR(100),
    tipo VARCHAR(100),
    texto_paragrafado VARCHAR(3000),
    url_imagem VARCHAR(200),
    url_video VARCHAR(200), 
    id_atv INT,
    FOREIGN KEY (id_atv) REFERENCES atividade(id_atividade)
);

create table questao(
	id_questao INT PRIMARY KEY,
    enunciado VARCHAR(100),
    pontuacao INT,
    id_atv INT,
    FOREIGN KEY (id_atv) REFERENCES atividade(id_atividade)
);

create table alternativa(
	id_alternativa INT PRIMARY KEY,
    texto VARCHAR(300),
    correta BOOLEAN,
    id_questao INT,
    FOREIGN KEY (id_questao) REFERENCES questao(id_questao)
);

INSERT INTO usuario
(id_user, nome, email, senha_hash, url_avatar, tipo_perfil)
VALUES
(1, 'Usuario Teste', 'teste@granalab.com', NULL, NULL, 'Aluno');