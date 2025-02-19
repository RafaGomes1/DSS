USE ESIDEAL;

SET SQL_SAFE_UPDATES = 0;

INSERT INTO Utilizador(idUtilizador,Nome,Password,Tipo) Values
("user01","Ana Silva","ana123",1),
("user02","Carlos Pereira","carlos456",1),
("user03","Marta Oliveira","marta789",1),
("user04","João Santos","joao987",1),
("user05","Rita Costa","rita321",1),
("user06","Pedro Fernandes","pedro654",1),
("user07","Sofia Matos","sofia234",2),
("user08","Hugo Almeida","hugo567",2),
("user09","Rafael Gomes","1234",2),
("user10","Lucas Oliveira","1234",2),
("user11","Mike Pinto","1234",2);

INSERT INTO Cliente(idCliente) VALUES
                                   ("user01"),
                                   ("user02"),
                                   ("user03"),
                                   ("user04"),
                                   ("user05"),
                                   ("user06");

INSERT INTO TipoServico(Tipo,Designacao) Values
                                            (0,"Universal"),
                                            (1,"Combustão"),
                                            (2,"Gasoleo"),
                                            (3,"Gasolina"),
                                            (4,"Eletrico");


INSERT INTO Mecanico(idMecanico,TipoCompetencia,UltimoInicioTurno,UltimoFimTurno) Values
("user07",1,"2024-01-08 10:00:00","2024-01-08 20:00:00"),
("user08",4,"2024-01-08 12:00:00","2024-01-08 17:00:00"),
("user09",3,"2024-01-03 09:00:00","2024-01-06 18:00:00"),
("user10",2,"2024-01-03 09:00:00","2024-01-06 18:00:00"),
("user11",0,"2024-01-03 09:00:00","2024-01-06 18:00:00");

INSERT INTO Veiculo(Matricula,Modelo,Marca,TipoMotor,idCliente) Values
("AB-12-34","Civic","Honda","Eletrico","user01"),
("CD-56-78","Focus","Ford","Gasoleo","user03"),
("EF-90-12","Astra","Opel","Gasolina","user05"),
("GH-34-56","Clio","Renault","GasoleoH","user02"),
("IJ-78-90","Golf","Volkswagen","GasolinaH","user06"),
("KL-12-34","Corolla","Toyota","Eletrico","user04"),
("MN-56-78","Mazda3","Mazda","Gasoleo","user03"),
("OP-90-12","i20","Hyundai","Eletrico","user01");

INSERT INTO PostoAtendimento(idPostoAtendimento,TipoServico) Values
(1,0),
(2,3),
(3,1),
(4,2),
(5,0),
(6,4),
(7,0),
(8,1);

INSERT INTO Servico (idServico, custo, TempoEstimado, Designacao, TipoServico)
VALUES
    (1, 50.00, 1, 'Troca de Óleo', 1),
    (2, 30.00, 2, 'Lavagem', 0),
    (3, 80.00, 3, 'Reparo de Motor', 0),
    (4, 60.00, 4, 'Limpeza de Filtro de Combustível', 2),
    (5, 70.00, 2, 'Ajuste de Injeção Eletrônica', 3),
    (6, 100.00, 1, 'Verificação de Bateria', 4),
    (7, 40.00, 3, 'Calibração de Pneus', 0),
    (8, 90.00, 4, 'Reparo de Sistema Elétrico universal', 1),
    (9, 55.00, 2, 'Limpeza de Injetores', 2),
    (10, 75.00, 1, 'Inspeção de Freios', 3),
    (11, 120.00, 3, 'Substituição de Bateria Eletrônica', 4),
    (12, 150.00, 2, 'Calibração de Bateria', 4),
    (13, 0.00, 1, 'Check-up Geral', 0);

/*
INSERT INTO Agendamento(idAgendamento,DataHora,idServico,idPostoAtendimento,Matricula) Values
(14,2024 01 15 14:30:00,14,1,"OP-90-12"),
(15,2024 01 15 15:35:00,15,3,"CD-56-78"),
(16,2024 01 16 11:45:00,16,7,"MN-56-78"),
(17,2024 01 16 16:10:00,17,4,"EF-90-12"),
(18,2024 01 18 10:15:00,18,6,"AB-12-34"),
(19,2024 01 19 12:30:00,19,2,"KL-12-34"),
(20,2024 01 20 16:30:00,20,8,"IJ-78-90"),
(21,2024 01 21 18:00:00,21,5,"OP-90-12"),
(22,2024 01 25 14:00:00,22,2,"GH-34-56"),
(23,2024 02 01 11:30:00,23,4,"KL-12-34"),
(24,2024 02 01 14:00:00,24,6,"MN-56-78"),
(25,2024 02 02 15:45:00,25,8,"AB-12-34"),
(26,2024 02 03 09:30:00,26,1,"EF-90-12");
*/

INSERT INTO Informacao(idInfo,Informacao,DataHora,idVeiculo) Values
(1,"Identificou Pequenos Problemas. Motor com ligeira anomalia, suspensão com sinais de desgaste, transmissão requer ajustes. Pequenas áreas com ferrugem no corpo, pintura a precisar de cuidados. Interior com desgaste leve, eletrónicos instáveis. Recomenda-se intervenção para manter o Civic em forma.","2023-12-27 15:40:30","AB-12-34"),
(2,"Motor apresenta pequenas anomalias, suspensão com desgaste moderado, transmissão requer atenção. Algumas áreas com sinais de ferrugem, pintura a precisar de retoques. Interior com desgaste visível, eletrónicos intermitentes. Recomenda-se intervenção para otimizar o desempenho do Focus.","2024-01-02 14:35:25","CD-56-78"),
(3,"Detetou Alguns Defeitos. Motor precisa de atenção, suspensão com pequenas falhas, transmissão a necessitar de ajustes. Ferrugem visível no corpo, pintura desgastada. Interior com sinais de desgaste, eletrónicos instáveis. Recomenda-se intervenção urgente para devolver o Astra ao seu melhor desempenho.","2024-01-04 11:20:45","EF-90-12"),
(4,"Danos Extensos. Motor com avarias graves, suspensão em mau estado, transmissão requer reconstrução. Corpo danificado, com sinais significativos de ferrugem, pintura severamente afetada. Interior em estado crítico, eletrónicos não operacionais. Recomenda-se intervenção imediata para avaliação de viabilidade e possível recuperação do Clio.","2024-01-06 10:46:23","GH-34-56"),
(5,"Motor apresenta pequena anomalia, suspensão em bom estado, transmissão requer ajustes mínimos. Corpo sem sinais de ferrugem significativos, pintura em boas condições. Interior bem conservado, eletrónicos operacionais. Recomenda-se intervenção para corrigir a pequena anomalia no motor.","2024-01-03 16:45:34","IJ-78-90"),
(6,"Motor com avarias sérias, suspensão comprometida, transmissão requer reconstrução extensa. Corpo danificado com sinais de ferrugem pronunciada, pintura severamente afetada. Interior em estado crítico, eletrónicos não funcionam adequadamente. Recomenda-se avaliação profunda para determinar viabilidade de reparação no Corolla.","2024-01-08 13:00:01","KL-12-34"),
(7,"Motor em boa condição, suspensão e transmissão operacionais. Corpo sem ferrugem, pintura em bom estado. Interior bem conservado, eletrónicos funcionais, embora com um pequeno defeito a nível dos comandos do ar condicionado. Recomenda-se intervenção mínima para manter o Mazda3 na sua melhor forma.","2024-01-04 19:27:34","MN-56-78"),
(8,"Motor em bom estado, suspensão e transmissão operacionais. Corpo sem ferrugem, pintura em bom estado. Interior conservado, eletrónicos funcionais, embora com um pequeno defeito no sistema de travagem. Recomenda-se intervenção para garantir o ótimo desempenho do Hyundai i20.","2024-01-07 12:35:35","OP-90-12");

-- INSERT INTO Notificacao(idNotificacao,Info,Data,idCliente) Values
-- (1,"Serviço aceito!","2024-01-07","user03"),
-- (2,"Serviço realizado","2024-01-06","user05"),
-- (3,"Serviço rejeitado","2024-01-03","user06"),
-- (4,"Pode vir buscar o carro","2024-01-01","user01"),
-- (5,"Serviço agendado","2024-01-04","user06"),
-- (6,"Pode vir buscar o carro","2024-01-01","user03"),
-- (7,"Pode vir buscar o carro","2023-12-23","user04"),
-- (8,"Serviço realizado","2024-01-03","user02"),
-- (9,"Serviço aceito!","2024-01-01","user06");

INSERT INTO Oficina(idOficina,HoraAbertura,HoraFecho) Values
("Gualtar",'08:00:00','19:00:00'),
("Frossos",'09:00:00','20:00:00'),
("SVictor",'08:30:00','19:00:00');


