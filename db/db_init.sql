DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS reaction;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS image_in_folder;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS publication;
DROP TABLE IF EXISTS folder;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS account_identifier;

DROP SEQUENCE IF EXISTS account_sequence;
DROP SEQUENCE IF EXISTS publication_sequence;
DROP SEQUENCE IF EXISTS folder_sequence;
DROP SEQUENCE IF EXISTS image_sequence;
DROP SEQUENCE IF EXISTS comment_sequence;
DROP SEQUENCE IF EXISTS reaction_sequence;


create sequence account_sequence
    start with 1000
    increment 1;

create table account(
    id bigint not null default nextval('account_sequence'),
    name varchar(255) unique not null ,
    email varchar(255) unique not null,
    password varchar(255),
    phone_number varchar(255) unique,
    status text,
    birthday date,
    salt varchar(255) not null,

    constraint account_id_pk primary key (id)
);

create table account_identifier(
    id bigserial primary key,
    account_id bigint,
    identifier varchar(255)
);

create sequence publication_sequence
    start with 1000
    increment 1;

create table publication(
    id bigint not null default nextval('publication_sequence'),
    date date,
    content text,
    comments_amount int,
    likes_amount int,
    account_id bigint not null,

    constraint publication_id_pk primary key (id),
    constraint account_id_fk foreign key (account_id) references account(id)
);

create sequence folder_sequence
    start with 1000
    increment 1;

create table folder(
    id bigint not null default nextval('folder_sequence'),
    name varchar(255),
    description text,
    image_amount int,
    date_created date,
    account_id bigint not null,

    constraint folder_id_pk primary key (id),
    constraint account_id_fk foreign key (account_id) references account(id)
);

create sequence image_sequence
    start with 1000
    increment 1;

create table image(
    id bigint not null default nextval('image_sequence'),
    path text,
    name varchar(255),
    description text,
    publication_id bigint not null,
    date_created date,

    constraint image_id_pk primary key (id),
    constraint publication_id_fk foreign key (publication_id) references publication(id)
);

create table image_in_folder(
    image_id bigint not null,
    folder_id bigint not null,

    constraint image_in_folder_id_pk primary key (image_id, folder_id),
    constraint image_id_fk foreign key (image_id) references image(id),
    constraint folder_id_fk foreign key (folder_id) references folder(id)
);

create table favorite(
    account_id bigint not null,
    publication_id bigint not null,

    constraint favorite_id_pk primary key (account_id, publication_id),
    constraint account_id_fk foreign key (account_id) references account(id),
    constraint publication_id_fk foreign key (publication_id) references publication(id)
);

create sequence comment_sequence
    start with 1000
    increment 1
    cache 50;

create table comment(
    id bigint not null default nextval('comment_sequence'),
    content text not null,
    date date,
    likes_amount int,
    dislikes_amount int,
    changed boolean,
    publication_id bigint not null,
    account_id bigint not null,
    creator_name varchar(60),

    constraint comment_id_pk primary key (id),
    constraint publication_id_fk foreign key (publication_id) references publication(id),
    constraint account_id_fk foreign key (account_id) references account(id)
);

create table notification (
      id bigserial primary key,
      account_id bigint not null,
      type varchar(50) not null,
      content text not null,
      is_read boolean default false,
      created_at timestamp default current_timestamp,
      foreign key (account_id) references account(id) on delete cascade
);

create sequence reaction_sequence
    start with 1000
    increment 1;

create table reaction (
      id bigint not null default nextval('reaction_sequence'),
      type varchar(50) not null,
      account_id bigint not null,
      publication_id bigint,
      comment_id bigint,
      date timestamp default current_timestamp,

      constraint reaction_id_pk primary key (id),
      constraint account_id_fk foreign key (account_id) references account(id),
      constraint publication_id_fk foreign key (publication_id) references publication(id),
      constraint comment_id_fk foreign key (comment_id) references comment(id)
);
