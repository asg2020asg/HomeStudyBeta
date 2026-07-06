CREATE DATABASE IF NOT EXISTS home_study;
USE home_study;

-- Tabela Usuario (central para dados comuns)
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(100),
    senha VARCHAR(100) NOT NULL,
    data_nascimento DATE
);

-- Tabela Aluno (com chave estrangeira para Usuario)
CREATE TABLE IF NOT EXISTS aluno (
    usuario_id INT PRIMARY KEY,
    curso VARCHAR(100),
    periodo VARCHAR(100),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela Proprietario (com chave estrangeira para Usuario)
CREATE TABLE IF NOT EXISTS proprietario (
    usuario_id INT PRIMARY KEY,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela Imovel (com chave estrangeira para Proprietario)
CREATE TABLE IF NOT EXISTS imovel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proprietario_id INT NOT NULL,
    nome_imovel VARCHAR(100) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    informacao_imovel TEXT,
    valor_imovel DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (proprietario_id) REFERENCES proprietario(usuario_id) ON DELETE CASCADE
);
