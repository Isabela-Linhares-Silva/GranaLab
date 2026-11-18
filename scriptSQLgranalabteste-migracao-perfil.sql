-- Migração manual para bancos existentes antes da SPEC-002.
-- Execute uma única vez sobre `granalabteste` e faça backup antes de iniciar.
-- Não converta `aluno.idade` em data de nascimento: obtenha a data real de uma fonte confiável.

USE granalabteste;

-- Mostra e interrompe a migração se houver nomes duplicados. A correção é manual.
SELECT nome, COUNT(*) AS quantidade
FROM usuario
GROUP BY nome
HAVING COUNT(*) > 1;

-- Exibe nomes que não atenderão ao novo limite de 5 a 100 caracteres.
SELECT id_user, nome
FROM usuario
WHERE nome IS NULL
   OR CHAR_LENGTH(TRIM(nome)) NOT BETWEEN 5 AND 100;

DELIMITER //
DROP PROCEDURE IF EXISTS validar_nomes_perfil_migracao//
CREATE PROCEDURE validar_nomes_perfil_migracao()
BEGIN
    IF EXISTS (
        SELECT 1
        FROM usuario
        GROUP BY nome
        HAVING COUNT(*) > 1
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Resolva manualmente os nomes duplicados antes da migração';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM usuario
        WHERE nome IS NULL
           OR CHAR_LENGTH(TRIM(nome)) NOT BETWEEN 5 AND 100
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Corrija manualmente nomes fora do limite de 5 a 100 caracteres';
    END IF;
END//
DELIMITER ;

CALL validar_nomes_perfil_migracao();
DROP PROCEDURE validar_nomes_perfil_migracao;

-- Remove restrições de perfil eventualmente adicionadas por versões anteriores
-- para que caminhos e comprimentos sejam atualizados antes de reinstalá-las.
SET @drop_nome_unique = IF(
    EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_schema = DATABASE()
          AND table_name = 'usuario'
          AND constraint_name = 'uq_usuario_nome'
    ),
    'ALTER TABLE usuario DROP INDEX uq_usuario_nome',
    'SELECT 1'
);
PREPARE stmt_drop_nome_unique FROM @drop_nome_unique;
EXECUTE stmt_drop_nome_unique;
DEALLOCATE PREPARE stmt_drop_nome_unique;

SET @drop_nome_check = IF(
    EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_schema = DATABASE()
          AND table_name = 'usuario'
          AND constraint_name = 'chk_usuario_nome_tamanho'
    ),
    'ALTER TABLE usuario DROP CHECK chk_usuario_nome_tamanho',
    'SELECT 1'
);
PREPARE stmt_drop_nome_check FROM @drop_nome_check;
EXECUTE stmt_drop_nome_check;
DEALLOCATE PREPARE stmt_drop_nome_check;

SET @drop_avatar_check = IF(
    EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_schema = DATABASE()
          AND table_name = 'usuario'
          AND constraint_name = 'chk_usuario_avatar_catalogo'
    ),
    'ALTER TABLE usuario DROP CHECK chk_usuario_avatar_catalogo',
    'SELECT 1'
);
PREPARE stmt_drop_avatar_check FROM @drop_avatar_check;
EXECUTE stmt_drop_avatar_check;
DEALLOCATE PREPARE stmt_drop_avatar_check;

-- Atribui um caminho válido aos avatares ausentes ou não pertencentes ao catálogo.
UPDATE usuario
SET url_avatar = ELT(
    1 + FLOOR(RAND() * 6),
    '/resources/img/Onca.jpeg',
    '/resources/img/Arara.jpeg',
    '/resources/img/Lobo-guara.jpeg',
    '/resources/img/Garca.jpeg',
    '/resources/img/Porco.jpeg',
    '/resources/img/Tartaruga.jpeg'
)
WHERE url_avatar IS NULL
   OR url_avatar NOT IN (
       '/resources/img/Onca.jpeg',
       '/resources/img/Arara.jpeg',
       '/resources/img/Lobo-guara.jpeg',
       '/resources/img/Garca.jpeg',
       '/resources/img/Porco.jpeg',
       '/resources/img/Tartaruga.jpeg'
   );

-- A unicidade falhará se a pré-verificação ainda retornar duplicidades.
ALTER TABLE usuario
    MODIFY COLUMN nome VARCHAR(100) NOT NULL,
    ADD CONSTRAINT uq_usuario_nome UNIQUE (nome),
    ADD CONSTRAINT chk_usuario_nome_tamanho
        CHECK (CHAR_LENGTH(TRIM(nome)) BETWEEN 5 AND 100),
    MODIFY COLUMN url_avatar VARCHAR(200) NOT NULL,
    ADD CONSTRAINT chk_usuario_avatar_catalogo CHECK (url_avatar IN (
        '/resources/img/Onca.jpeg',
        '/resources/img/Arara.jpeg',
        '/resources/img/Lobo-guara.jpeg',
        '/resources/img/Garca.jpeg',
        '/resources/img/Porco.jpeg',
        '/resources/img/Tartaruga.jpeg'
    ));

-- Mantém o valor legado de idade apenas durante o preenchimento das datas reais.
ALTER TABLE aluno ADD COLUMN data_nascimento DATE NULL;

-- Preencha data_nascimento para cada aluno usando uma fonte confiável.
-- Não derive a data a partir de idade. Confira a consulta abaixo até retornar zero linhas:
SELECT id_aluno
FROM aluno
WHERE data_nascimento IS NULL;

-- Somente após preencher todas as datas, execute a etapa final:
-- ALTER TABLE aluno
--     MODIFY COLUMN data_nascimento DATE NOT NULL,
--     MODIFY COLUMN genero ENUM('M', 'F', 'O') NOT NULL,
--     DROP COLUMN idade;

-- A instituição/escola do Mentor é opcional no modelo novo.
ALTER TABLE mentor MODIFY COLUMN escola_atua VARCHAR(100) NULL;
