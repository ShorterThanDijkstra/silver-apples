create table the_intro
(
    paragraph varchar default ''::character varying not null
);

alter table the_intro
    owner to postgres;

create table unit
(
    id    serial
        constraint unit_pk
            primary key,
    index integer default 0 not null
);

alter table unit
    owner to postgres;

create table root
(
    id          serial
        constraint root_pk
            primary key,
    name        varchar default ''::character varying not null,
    description varchar default ''::character varying not null,
    unit_id     serial
        constraint root_unit_id_fk
            references unit
);

alter table root
    owner to postgres;

create table quiz
(
    id      serial
        constraint quiz_pk
            primary key,
    unit_id serial
        constraint quiz_unit_id_fk
            references unit
);

alter table quiz
    owner to postgres;

create table word
(
    id      serial
        constraint word_pk
            primary key,
    spell   varchar default ''::character varying not null,
    explain varchar default ''::character varying not null,
    detail  varchar default ''::character varying,
    root_id serial
        constraint word_root_id_fk
            references root
);

alter table word
    owner to postgres;

create table sentence
(
    id      serial
        constraint sentence_pk
            primary key,
    text    varchar default ''::character varying not null,
    word_id integer                               not null
        constraint sentence_word_id_fk
            references word
);

alter table sentence
    owner to postgres;

create table special_section_word
(
    id      serial
        constraint special_section_word_pk
            primary key,
    spell   varchar default ''::character varying not null,
    explain varchar default ''::character varying not null,
    detail  varchar default ''::character varying not null,
    unit_id serial
        constraint special_section_word_unit_id_fk
            references unit
);

alter table special_section_word
    owner to postgres;

create table simple_quiz_page
(
    id      serial
        constraint simple_quiz_page_pk
            primary key,
    content varchar default ''::character varying not null,
    quiz_id serial
        constraint simple_quiz_page_quiz_id_fk
            references quiz
);

alter table simple_quiz_page
    owner to postgres;

create table simple_quiz_page_answer
(
    id      serial
        constraint simple_quiz_page_answer_pk
            primary key,
    answer  varchar default ''::character varying not null,
    page_id serial
        constraint simple_quiz_page_answer_simple_quiz_page_id_fk
            references simple_quiz_page
);

alter table simple_quiz_page_answer
    owner to postgres;

create table special_section_sentence
(
    id                      serial
        constraint special_section_sentence_pk
            primary key,
    text                    varchar default ''::character varying not null,
    special_section_word_id serial
        constraint special_section_sentence_special_section_word_id_fk
            references special_section_word
);

alter table special_section_sentence
    owner to postgres;

create table sys_user
(
    id          serial
        constraint sys_user_pk
            primary key,
    username    varchar(32)              not null,
    passwd      varchar(128)             not null,
    email       varchar(64)              not null,
    create_time timestamp with time zone not null
);

alter table sys_user
    owner to postgres;

create unique index sys_user_id_uindex
    on sys_user (id);

create unique index sys_user_name_uindex
    on sys_user (username);

create unique index sys_user_email_uindex
    on sys_user (email);

create table user_word_practice
(
    id          serial
        constraint user_word_practice_pk
            primary key,
    user_id     serial
        constraint user_word_practice_sys_user_id_fk
            references sys_user,
    word_id     serial
        constraint user_word_practice_word_id_fk
            references word,
    sentence    text                     not null,
    create_time timestamp with time zone not null
);

alter table user_word_practice
    owner to postgres;

