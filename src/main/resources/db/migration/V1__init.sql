-- 1. Tabela de Auditoria Customizada (O Envers exige esta primeiro)
-- Nota: Como você usa CustomRevisionEntity, o nome da tabela no seu @Table é 'revinfo_custom'
CREATE TABLE IF NOT EXISTS revinfo_custom (
                                              id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                              timestamp BIGINT NOT NULL,
                                              ip VARCHAR(255),
    login VARCHAR(255),
    user_agent VARCHAR(255)
    ) ENGINE=InnoDB;

-- 2. Tabela de Usuários (Auth)
CREATE TABLE IF NOT EXISTS users (
                                     id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE
    ) ENGINE=InnoDB;

-- 3. Tabela de Servidor (Gestão)
CREATE TABLE IF NOT EXISTS servidor (
                                        id VARCHAR(255) NOT NULL PRIMARY KEY,
    cpf VARCHAR(255) UNIQUE
    ) ENGINE=InnoDB;

-- 4. Tabelas de Relacionamento Crítico (As que costumam travar o boot)
CREATE TABLE IF NOT EXISTS documentos_pessoais (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS dados_bancarios (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS informacoes_assentamento (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS treinamentos (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS afastamentos (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS informacoes_escolares (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS informacoes_sensiveis (id VARCHAR(255) NOT NULL PRIMARY KEY, servidor_id VARCHAR(255)) ENGINE=InnoDB;

-- 5. Tabelas de Auditoria (_aud) - Mínimas para o Envers não dar erro de validação
CREATE TABLE IF NOT EXISTS users_aud (id VARCHAR(255) NOT NULL, rev INTEGER NOT NULL, revtype TINYINT, PRIMARY KEY (id, rev)) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS servidor_aud (id VARCHAR(255) NOT NULL, rev INTEGER NOT NULL, revtype TINYINT, PRIMARY KEY (id, rev)) ENGINE=InnoDB;