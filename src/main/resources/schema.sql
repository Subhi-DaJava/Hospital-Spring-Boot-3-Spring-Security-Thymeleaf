--spring.sql.init.mode=always, qui peut provoquer une initialisation forcée de la base de données à chaque démarrage de l'application, indépendamment de la propriété spring.jpa.hibernate.ddl-auto. Cette propriété est utilisée pour indiquer que le script de création de la base de données doit être exécuté à chaque démarrage. Cela pourrait expliquer pourquoi les tables sont recréées à chaque fois.
create table if not exists users (username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table if not exists authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
--create unique index if not exists ix_auth_username  on authorities (username,authority);