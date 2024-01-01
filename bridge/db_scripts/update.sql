
    create table a_receber (
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

    create table cliente (
        id integer not null auto_increment,
        ativo BOOLEAN not null,
        created_by varchar(255),
        created_date datetime(6),
        last_modified_by varchar(255),
        last_modified_date datetime(6),
        nome varchar(100) not null,
        observacoes varchar(1000),
        saldo decimal(15,2) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table cliente 
       drop index unique_cliente_nome;

    alter table cliente 
       add constraint unique_cliente_nome unique (nome);

    alter table a_receber 
       add constraint fk_a_receber_cliente 
       foreign key (cliente_id) 
       references cliente (id);
