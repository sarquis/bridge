-- Creates the new database.
create database db_bridge; 

-- Creates the user.
create user 'bridgeuser'@'%' identified by 'H*wF1=53szsMCBd?.FC&';

-- Gives all privileges to the new user on the newly created database. 
grant all on db_bridge.* to 'bridgeuser'@'%'; 

-- ! ATENÇÃO - Lembrar de executar antes de ir para produção !
-- Removing all privileges. To make changes to only the data of the database and not the structure (schema).
grant select, insert, delete, update on db_example.* to 'bridgeuser'@'%';