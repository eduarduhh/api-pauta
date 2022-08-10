insert into pauta (id, descricao, indic_exclusao) values (1, 'Contratação 01',0);


insert into sessao (id, descricao, pauta_id, inicio, fim, indic_exclusao) values (1, 'Contratro Eduardo ', 1, '2022-07-02 00:00:00', '2022-07-02 23:59:00',  0);


insert into associado (id, nome, matricula, cpf) values (1, 'Funcionario 1',  2020001, '10407611002' );
insert into associado (id, nome, matricula, cpf) values (2, 'Funcionario 2',  2020002, '10407611002' );
insert into associado (id, nome, matricula, cpf) values (3, 'Funcionario 3',  2020003, '10407611002' );
insert into associado (id, nome, matricula, cpf) values (4, 'Funcionario 4',  2020004, '10407611002' );

insert into votacao (id, voto, associado_id, sessao_id) values (1, true, 1,  1);
insert into votacao (id, voto, associado_id, sessao_id) values (2, true, 2, 1);
insert into votacao (id, voto, associado_id, sessao_id) values (3, true,  3, 1);
insert into votacao (id, voto, associado_id, sessao_id) values (4, true, 4, 1);







