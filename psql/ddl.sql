create table if not exists the_intro
(
    paragraph varchar default ''::character varying not null
);

alter table the_intro
    owner to postgres;

create table if not exists unit
(
    id    serial
        constraint unit_pk
            primary key,
    index integer default 0 not null
);

alter table unit
    owner to postgres;

create table if not exists root
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

create table if not exists quiz
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

create table if not exists word
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

create table if not exists sentence
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

create table if not exists special_section_word
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

create table if not exists simple_quiz_page
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

create table if not exists simple_quiz_page_answer
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

create table if not exists special_section_sentence
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

