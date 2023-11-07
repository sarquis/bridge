
    create table funcao (
        id integer not null auto_increment,
        nome varchar(50) not null,
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

    alter table funcao 
       add constraint unique_funcao_nome unique (nome);

    alter table usuario 
       add constraint unique_usuario_email unique (email);

    alter table usuario_funcao 
       add constraint fk_funcao_usuario 
       foreign key (funcao_id) 
       references funcao (id);

    alter table usuario_funcao 
       add constraint fk_usuario_funcao 
       foreign key (usuario_id) 
       references usuario (id);
