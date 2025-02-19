CREATE SCHEMA IF NOT EXISTS ESIDEAL;

USE ESIDEAL;

CREATE TABLE IF NOT EXISTS ESIDEAL.Utilizador (
	idUtilizador VARCHAR(50) NOT NULL,
	Nome VARCHAR(50) NOT NULL,
	Password VARCHAR(50) NOT NULL,
	Tipo INT NOT NULL,
	PRIMARY KEY (idUtilizador));

CREATE TABLE IF NOT EXISTS ESIDEAL.Cliente (
	idCliente VARCHAR(50) NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES ESIDEAL.Utilizador(idUtilizador),
	PRIMARY KEY (idCliente));

CREATE TABLE IF NOT EXISTS ESIDEAL.TipoServico (
    Tipo INT NOT NULL,
    Designacao VARCHAR(45) NOT NULL,
    PRIMARY KEY (Tipo));

CREATE TABLE IF NOT EXISTS ESIDEAL.Mecanico (
	idMecanico VARCHAR(50) NOT NULL,
	TipoCompetencia INT NOT NULL,
    UltimoInicioTurno DATETIME NOT NULL,
    UltimoFimTurno DATETIME NOT NULL,
	FOREIGN KEY (idMecanico) REFERENCES ESIDEAL.Utilizador(idUtilizador),
	FOREIGN KEY (TipoCompetencia) REFERENCES ESIDEAL.TipoServico(Tipo),
	PRIMARY KEY (idMecanico));

CREATE TABLE IF NOT EXISTS ESIDEAL.Notificacao (
	idNotificacao INT NOT NULL,
	Info TEXT NOT NULL,
	Data DATE NOT NULL,
	idCliente VARCHAR(50) NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES ESIDEAL.Cliente(idCliente),
	PRIMARY KEY (idNotificacao));

CREATE TABLE IF NOT EXISTS ESIDEAL.Veiculo (
	Matricula VARCHAR(8) NOT NULL,
	Modelo VARCHAR(45) NOT NULL,
	Marca VARCHAR(45) NOT NULL,
	TipoMotor VARCHAR(15) NOT NULL,
	idCliente VARCHAR(50) NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES ESIDEAL.Cliente(idCliente),
	PRIMARY KEY (Matricula));

CREATE TABLE IF NOT EXISTS ESIDEAL.Informacao (
	idInfo INT NOT NULL,
	Informacao TEXT NOT NULL,
	DataHora DATETIME NOT NULL,
	idVeiculo VARCHAR(8) NOT NULL,
	FOREIGN KEY (idVeiculo) REFERENCES ESIDEAL.Veiculo(Matricula),
	PRIMARY KEY (IdInfo));

CREATE TABLE IF NOT EXISTS ESIDEAL.PostoAtendimento (
	idPostoAtendimento INT NOT NULL,
	TipoServico INT NOT NULL,
	FOREIGN KEY (TipoServico) REFERENCES ESIDEAL.TipoServico(Tipo),
	PRIMARY KEY (idPostoAtendimento));

CREATE TABLE IF NOT EXISTS ESIDEAL.Servico (
	idServico INT NOT NULL,
	Custo DECIMAL NOT NULL,
	TempoEstimado INT NOT NULL,
	Designacao VARCHAR(50) NOT NULL,
	TipoServico INT NOT NULL,
	FOREIGN KEY (TipoServico) REFERENCES ESIDEAL.TipoServico(Tipo),
	PRIMARY KEY (idServico));

CREATE TABLE IF NOT EXISTS ESIDEAL.Agendamento (
	idAgendamento INT NOT NULL,
	DataHora DATETIME NOT NULL,
	idServico INT NOT NULL,
	idPostoAtendimento INT NOT NULL,
	idVeiculo VARCHAR(8) NOT NULL,
	FOREIGN KEY (idServico) REFERENCES ESIDEAL.Servico(idServico),
	FOREIGN KEY (idPostoAtendimento) REFERENCES ESIDEAL.PostoAtendimento(idPostoAtendimento),
	FOREIGN KEY (idVeiculo) REFERENCES ESIDEAL.Veiculo(Matricula),
	PRIMARY KEY (idAgendamento));

CREATE TABLE IF NOT EXISTS ESIDEAL.Oficina (
    idOficina VARCHAR(20) NOT NULL,
    HoraAbertura TIME NOT NULL,
    HoraFecho TIME NOT NULL,
    PRIMARY KEY (idOficina)
);