create table users(
id serial,
name varchar(50),
password varchar,
email varchar(100),
primary key (id)
);

create table wallet(
id serial,
name varchar(50),
value numeric(10,2),
primary key (id)
);

create table users_wallet(
id serial,
users integer,
wallet integer,
primary key (id),
foreign key (users) references users(id),
foreign key (wallet) references wallet(id)
);

create table wallet_itens(
id serial,
wallet integer,
date date,
type varchar(2),
description varchar(500),
value numeric(10,2),
primary key (id),
foreign key (wallet) references wallet(id)
);


