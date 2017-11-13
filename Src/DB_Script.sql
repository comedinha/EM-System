CREATE TABLE funcao (
	funcaoId int PRIMARY KEY NOT NULL,
	funcao_desc varchar(20) NOT NULL
);

CREATE TABLE funcionario (
	funcionarioId int PRIMARY KEY NOT NULL,
	username varchar(15) UNIQUE,
	password varchar(30),
	nome varchar(50),
	funcaoId int REFERENCES funcao(funcaoId) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE comanda(
	comandaId int PRIMARY KEY NOT NULL,
	data date,
	mesa int,
	valorPago numeric(5,2)
);

CREATE TABLE pagamento (
	pagamentoId int PRIMARY KEY NOT NULL,
	valor int,
	data date,
	funcionarioId int,
	comandaId int REFERENCES comanda(comandaId) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE produto (
	produtoId int PRIMARY KEY NOT NULL,
	nome varchar(30),
	valor numeric(5,2)
);

CREATE TABLE produto_Alterado (
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	data date,
	valor numeric(5,2)
);

CREATE TABLE produtoComanda (
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	comandaId int REFERENCES comanda(comandaId) ON DELETE NO ACTION ON UPDATE CASCADE,
	valorPago numeric(5,2),
	PRIMARY KEY(produtoId, comandaId)
);