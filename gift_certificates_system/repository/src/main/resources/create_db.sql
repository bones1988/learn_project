drop schema if exists gift_shop;
create schema gift_shop;
use gift_shop;

create table shopUser (
id bigint unsigned auto_increment primary key,
login varchar(15) unique,
password varchar(65),
name varchar(15),
last_name varchar(15),
role enum('USER', 'ADMINISTRATOR'),
active boolean default true
);

create table tag (
id bigint unsigned auto_increment primary key,
name varchar(15) unique);

create table certificate (
id bigint unsigned auto_increment primary key,
name varchar(15),
description varchar(255),
price decimal(6,2),
create_date timestamp(3),
update_date timestamp(3),
duration int,
active boolean default true);

create table purchase (
id bigint unsigned auto_increment primary key,
user_id bigint unsigned,
certificate_id bigint unsigned,
price decimal(6,2),
buyTime timestamp(3),
foreign key(user_id) references shopUser(id),
foreign key(certificate_id) references certificate(id) on delete cascade
);

create table assign (
certificate_id bigint unsigned,
tag_id bigint unsigned,
foreign key (tag_id) references tag (id) on delete cascade,
foreign key (certificate_id) references certificate (id) on delete cascade,
unique key (tag_id, certificate_id) );
