insert into tag(name) values ('first'), ('second');
insert into certificate(name, description, price, create_date, update_date, duration)
   values ('first', 'description first certificate', 1, '2001-01-01 01:01:01', '2001-01-01 01:01:01', 11),
          ('second', 'description second certificate', 10, '2002-02-02 02:02:02', '2002-02-02 02:02:02', 22);
insert into assign(tag_id, certificate_id) values(1,1),(1,2),(2,2);
insert into shopUser (id, login, password, name, role, active) values (1, 'user', 'user', 'user',
'USER', true);
