CREATE TABLE funcionario (
	funcionarioId SERIAL PRIMARY KEY NOT NULL,
	username varchar(15) UNIQUE,
	password varchar(30),
	nome varchar(50),
	funcaoId int
);

CREATE TABLE comanda(
	comandaId SERIAL NOT NULL,
	data date DEFAULT CURRENT_DATE,
	mesa varchar(15),
	valorPago numeric(8,2) DEFAULT 0,
	status int DEFAULT 0,
	primary key (comandaId, data)
);

CREATE TABLE pagamento (
	pagamentoId SERIAL PRIMARY KEY NOT NULL,
	valor int,
	data date DEFAULT CURRENT_DATE,
	funcionarioId int,
	comandaId int,
	dataComanda date,
    foreign key (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE produto (
	produtoId SERIAL PRIMARY KEY NOT NULL,
	nome varchar(30),
	valor numeric(8,2),
	status int DEFAULT 1
);

CREATE TABLE produto_Alterado (
	produtoId SERIAL REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	data date,
	valor numeric(8,2),
	status int DEFAULT 1
);

CREATE TABLE produtoComanda (
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	comandaId int,
	dataComanda date,
	quantidade int DEFAULT 1,
	valorPago numeric(8,2) DEFAULT 0,
	PRIMARY KEY(produtoId, comandaId),
    foreign key (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE
);