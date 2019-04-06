drop table if exists authorities;

create table authorities
(
  username varchar(45) not null,
  authority varchar(10) not null
);

drop table if exists users;

create table users
(
  username varchar(45) not null
    primary key,
  name varchar(16) not null,
  email varchar(255) not null,
  password varchar(64) not null,
  create_time timestamp default CURRENT_TIMESTAMP null
);

