-- Creates the new database.
create database db_bridge; 

-- Creates the user.
create user 'bridgeuser'@'%' identified by 'password';

-- Gives all privileges to the new user on the newly created database. 
grant all on db_bridge.* to 'bridgeuser'@'%'; 

-- ! ATENÇÃO ! Lembrar de executar antes de ir para produção !
-- Removing all privileges. To make changes to only the data of the database and not the structure (schema).
grant select, insert, delete, update on db_example.* to 'bridgeuser'@'%';

-- ! ATENÇÃO ! Usar "ci" (case insensitive) no collation do MySql.
-- Isso vai garantir que o UNIQUE constraint não considere caixa alta ou baixa.  
-- SELECT @@character_set_database, @@collation_database;
-- @@character_set_database = utf8mb4
-- @@collation_database     = utf8mb4_0900_ai_ci

-- Controle de acesso inicial (admin/admin):
INSERT INTO `funcao` (`nome`) VALUES ('ROLE_ADMIN');
INSERT INTO `funcao` (`nome`) VALUES ('ROLE_USUARIO');
INSERT INTO `usuario` (`ativo`, `senha`, `email`) VALUES (true, '$2a$10$160JKWobNRJsP2/KSWNanOCErLZgv.pbTDlZaILW6Bne6LY4dzMUa', 'admin');
INSERT INTO `usuario_funcao` (`funcao_id`, `usuario_id`) VALUES (1, 1);
INSERT INTO `usuario_funcao` (`funcao_id`, `usuario_id`) VALUES (2, 1);