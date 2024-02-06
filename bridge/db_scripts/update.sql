
    alter table a_receber 
       modify column valor decimal(15,2) not null;

    alter table cliente 
       modify column saldo decimal(15,2) not null;

    create table pagamento (
        id bigint unsigned not null auto_increment,
        ativo BOOLEAN not null,
        created_by varchar(255),
        created_date datetime(6),
        last_modified_by varchar(255),
        last_modified_date datetime(6),
        observacoes varchar(1000),
        valor decimal(15,2) not null,
        cliente_id integer not null,
        primary key (id)
    ) engine=InnoDB;

    alter table pagamento 
       add constraint fk_pagamento_cliente 
       foreign key (cliente_id) 
       references cliente (id);
