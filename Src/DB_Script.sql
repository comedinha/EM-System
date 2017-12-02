CREATE TABLE funcionario (
	funcionarioId SERIAL PRIMARY KEY NOT NULL,
	username varchar(15) UNIQUE,
	password varchar(30),
	nome varchar(50),
	garcom int2,
	funcaoId int
);

CREATE TABLE comanda(
	comandaId SERIAL NOT NULL,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	mesa varchar(15),
	valorPago numeric(8,2) DEFAULT 0,
	status int DEFAULT 0,
	primary key (comandaId, data)
);

CREATE TABLE pagamento (
	pagamentoId SERIAL PRIMARY KEY NOT NULL,
	valor int,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	funcionarioId int,
	comandaId int,
	dataComanda timestamp,
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
	data timestamp,
	valor numeric(8,2),
	status int DEFAULT 1
);

CREATE TABLE produtoComanda (
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	comandaId int,
	dataComanda timestamp,
	quantidade int DEFAULT 1,
	valorPago numeric(8,2) DEFAULT 0,
	PRIMARY KEY(produtoId, comandaId),
    foreign key (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE
);