CREATE TABLE configuracao(
	configKey varchar(30) PRIMARY KEY,
	value varchar(30)
);

CREATE TABLE funcionario(
	funcionarioId SERIAL PRIMARY KEY NOT NULL,
	username varchar(15) UNIQUE,
	password varchar(30),
	nome varchar(50),
	garcom boolean,
	funcaoId int
);

CREATE TABLE configuracaoFuncionario(
	configKey varchar(30),
	funcionarioId SERIAL REFERENCES funcionario(funcionarioId) ON DELETE CASCADE ON UPDATE CASCADE,
	value varchar(30),
	PRIMARY KEY (configKey, funcionarioId)
);

CREATE TABLE comanda(
	comandaId SERIAL NOT NULL,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	mesa varchar(15) DEFAULT '-',
	funcionarioId SERIAL REFERENCES funcionario(funcionarioId) ON DELETE NO ACTION ON UPDATE CASCADE,
	pago boolean DEFAULT FALSE,
	primary key (comandaId, data)
);

CREATE TABLE produto(
	produtoId SERIAL PRIMARY KEY NOT NULL,
	nome varchar(30),
	valor numeric(8,2),
	status int DEFAULT 1
);

CREATE TABLE produtoAlterado(
	produtoId SERIAL REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	nome varchar(30),
	valor numeric(8,2)
);

CREATE OR REPLACE RULE updateProduto AS ON update TO produto DO (INSERT INTO produtoAlterado (produtoId, nome, valor) values (old.produtoId, old.nome, old.valor));

CREATE TABLE produtoComanda(
	produtoId int REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	comandaId int,
	dataComanda timestamp,
	quantidade int DEFAULT 1,
	valorPago numeric(8,2) DEFAULT 0,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(produtoId, comandaId),
    foreign key (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE pagamento(
	pagamentoId SERIAL PRIMARY KEY NOT NULL,
	valor int,
	data timestamp DEFAULT CURRENT_TIMESTAMP,
	funcionarioId int
);

CREATE TABLE pagamentoComanda(
	pagamentoId SERIAL REFERENCES pagamento(pagamentoId) ON DELETE CASCADE ON UPDATE CASCADE,
	comandaId int,
	dataComanda timestamp,
    FOREIGN KEY (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE,
	PRIMARY KEY (pagamentoId, comandaId, dataComanda)
);

CREATE TABLE pagamentoProduto(
	pagamentoId SERIAL REFERENCES pagamento(pagamentoId) ON DELETE CASCADE ON UPDATE CASCADE,
	comandaId int,
	dataComanda timestamp,
	produtoId SERIAL REFERENCES produto(produtoId) ON DELETE NO ACTION ON UPDATE CASCADE,
	quantidade int,
	FOREIGN KEY (comandaId, dataComanda) references comanda(comandaId, data) ON DELETE NO ACTION ON UPDATE CASCADE,
	PRIMARY KEY (pagamentoId, comandaId, dataComanda)
);