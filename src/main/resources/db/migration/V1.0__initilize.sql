create table conta (
   id varchar(255) not null,
    cnpj varchar(255),
    funcionarios integer not null,
    razao_social varchar(255),
    cpf varchar(255),
    nome varchar(255),
    valor_mercado_moeda varchar(255),
    valor_mercado decimal(19,2),
    limite_moeda varchar(255),
    limite decimal(19,2),
    saldo_moeda varchar(255),
    saldo decimal(19,2),
    primary key (id)
);

create table emprestimo (
   id varchar(255) not null,
    liberado_em timestamp,
    observacao varchar(255),
    quitado_em timestamp,
    situacao varchar(255),
    solicitado_em timestamp,
    ultima_movimentacao_em timestamp,
    moeda varchar(255),
    valor decimal(19,2),
    conta_id varchar(255) not null,
    primary key (id)
);

alter table conta 
   add constraint UK_a1qbp4t2lyoij3qyfjr1at32d unique (cnpj, funcionarios, razao_social, cpf, nome, valor_mercado_moeda, valor_mercado);

alter table emprestimo 
   add constraint FKfr15xahd586maerwk1eridk7d 
   foreign key (conta_id) 
   references conta;
