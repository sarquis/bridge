
    alter table funcao 
       drop index unique_funcao_nome;

    alter table funcao 
       add constraint unique_funcao_nome unique (nome);
