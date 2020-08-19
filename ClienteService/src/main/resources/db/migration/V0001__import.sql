--------------------------------------------------------
--  PADRÃO PARA FLYWAY
--------------------------------------------------------
INSERT INTO TIMES (NOME) VALUES ('America');

INSERT INTO CLIENTES (DATA_NASCIMENTO,EMAIL,TIME_ID,NOME) VALUES ('1990-01-01','alberto@abc.com',1,'Alberto Andrade');
INSERT INTO CLIENTES (DATA_NASCIMENTO,EMAIL,TIME_ID,NOME) VALUES ('1990-02-01','barbara@abc.com',1,'Barbara Barros');
INSERT INTO CLIENTES (DATA_NASCIMENTO,EMAIL,TIME_ID,NOME) VALUES ('1990-03-01','carlos@abc.com',1,'Carlos Camargo');

INSERT INTO CAMPANHAS (NOME,DATA_INICIAL,DATA_FINAL,TIME_ID) VALUES ('Campanha de lançamento.','2020-10-01','2020-10-03',1);