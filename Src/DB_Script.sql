CREATE TABLE funcionario (
	funcionarioId SERIAL PRIMARY KEY NOT NULL,
	username varchar(15) UNIQUE,
	password varchar(30),
	nome varchar(50),
	funcaoId int
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
	produtoId SERIAL PRIMARY KEY NOT NULL,
	nome varchar(30),
	valor numeric(5,2),
	status int default 1
);

CREATE TABLE produto_Alterado (
	produtoId SERIAL REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	data date,
	valor numeric(5,2),
	status int default 1
);

CREATE TABLE produtoComanda (
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	comandaId int REFERENCES comanda(comandaId) ON DELETE NO ACTION ON UPDATE CASCADE,
	valorPago numeric(5,2),
	PRIMARY KEY(produtoId, comandaId)
);