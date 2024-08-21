
    create table a_receber (
        cliente_id integer not null,
        valor decimal(15,2) not null,
        created_date datetime(6),
        id bigint unsigned not null auto_increment,
        last_modified_date datetime(6),
        observacoes varchar(1000),
        created_by varchar(255),
        last_modified_by varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table cliente (
        id integer not null auto_increment,
        saldo decimal(15,2) not null,
        ultima_diferenca_saldo decimal(15,2),
        created_date datetime(6),
        last_modified_date datetime(6),
        ultima_verificacao_saldo datetime(6),
        nome varchar(100) not null,
        observacoes varchar(1000),
        created_by varchar(255),
        last_modified_by varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table funcao (
        id integer not null auto_increment,
        nome varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamento (
        cliente_id integer not null,
        valor decimal(15,2) not null,
        created_date datetime(6),
        id bigint unsigned not null auto_increment,
        last_modified_date datetime(6),
        observacoes varchar(1000),
        created_by varchar(255),
        last_modified_by varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuario (
        ativo BOOLEAN not null,
        id integer not null auto_increment,
        created_date datetime(6),
        last_modified_date datetime(6),
        senha varchar(68) not null,
        email varchar(100) not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuario_funcao (
        funcao_id integer not null,
        usuario_id integer not null
    ) engine=InnoDB;

    create index idx_created_by 
       on a_receber (created_by);

    create index idx_created_by 
       on cliente (created_by);

    alter table cliente 
       add constraint unique_cliente_nome unique (nome, created_by);

    alter table funcao 
       add constraint unique_funcao_nome unique (nome);

    create index idx_created_by 
       on pagamento (created_by);

    alter table usuario 
       add constraint unique_usuario_email unique (email);

    alter table a_receber 
       add constraint fk_a_receber_cliente 
       foreign key (cliente_id) 
       references cliente (id);

    alter table pagamento 
       add constraint fk_pagamento_cliente 
       foreign key (cliente_id) 
       references cliente (id);

    alter table usuario_funcao 
       add constraint fk_funcao_usuario 
       foreign key (funcao_id) 
       references funcao (id);

    alter table usuario_funcao 
       add constraint fk_usuario_funcao 
       foreign key (usuario_id) 
       references usuario (id);
