
    alter table funcao 
       drop index unique_funcao_nome;

    alter table funcao 
       add constraint unique_funcao_nome unique (nome);

    alter table usuario 
       add column created_by varchar(255);

    alter table usuario 
       add column created_date datetime(6);

    alter table usuario 
       add column last_modified_by varchar(255);

    alter table usuario 
       add column last_modified_date datetime(6);
