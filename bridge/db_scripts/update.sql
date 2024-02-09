
    alter table a_receber 
       modify column valor decimal(15,2) not null;

    alter table cliente 
       modify column saldo decimal(15,2) not null;

    alter table pagamento 
       modify column valor decimal(15,2) not null;

    create index idx_created_by 
       on a_receber (created_by);

    create index idx_created_by 
       on cliente (created_by);

    create index idx_created_by 
       on pagamento (created_by);
