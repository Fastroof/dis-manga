--
-- Name: books; Type: TABLE;
--

CREATE TABLE if not EXISTS books
(
    id            bigint NOT NULL,
    updated_at    date   NOT NULL,
    created_at    date   NOT NULL,
    tag_id        bigint,
    name          text   NOT NULL,
    owner_id      bigint NOT NULL,
    link_to_cover text
);

alter table books
    alter column id add generated by default as identity;

--
-- Name: book_files; Type: TABLE;
--

CREATE TABLE if not exists book_files
(
    id           bigint NOT NULL,
    name         text   NOT NULL,
    updated_at   date   NOT NULL,
    created_at   date   NOT NULL,
    book_id      bigint NOT NULL,
    link_to_file text   NOT NULL,
    owner_id     bigint NOT NULL
);

alter table book_files
    alter column id add generated by default as identity;

--
-- Name: request_statuses; Type: TABLE;
--

CREATE TABLE request_statuses
(
    id   bigint NOT NULL,
    name text   NOT NULL
);

alter table request_statuses
    alter column id add generated by default as identity;

--
-- Name: roles; Type: TABLE;
--

CREATE TABLE roles
(
    id   bigint NOT NULL,
    name text   NOT NULL
);

alter table roles
    alter column id add generated by default as identity;

--
-- Name: tags; Type: TABLE;
--

CREATE TABLE tags
(
    id       bigint NOT NULL,
    name     text   NOT NULL,
    main_tag bigint
);

alter table tags
    alter column id add generated by default as identity;

--
-- Name: users; Type: TABLE;
--

CREATE TABLE users
(
    id         bigint NOT NULL,
    role       bigint NOT NULL,
    first_name text   NOT NULL,
    last_name  text   NOT NULL,
    email      text   NOT NULL,
    password   text   NOT NULL,
    provider   text   NOT NULL
);

alter table users
    alter column id add generated by default as identity;

--
-- Name: reports; Type: TABLE;
--

CREATE TABLE if not exists reports
(
    id           bigint NOT NULL,
    created_at   date   NOT NULL,
    text         text   NOT NULL,
    user_id      bigint NOT NULL,
    status       bigint NOT NULL,
    moderator_id bigint,
    book_id      bigint NOT NULL
);

alter table reports
    alter column id add generated by default as identity;

--
-- Name: help_requests; Type: TABLE;
--

CREATE TABLE if not exists help_requests
(
    id           bigint NOT NULL,
    created_at   date   NOT NULL,
    text         text   NOT NULL,
    email        text   NOT NULL,
    status       bigint NOT NULL,
    moderator_id bigint
);

alter table help_requests
    alter column id add generated by default as identity;

--
-- Name: comments; Type: TABLE;
--

CREATE TABLE if not exists comments
(
    id         bigint NOT NULL,
    created_at date   NOT NULL,
    text       text   NOT NULL,
    user_id    bigint NOT NULL,
    book_id    bigint NOT NULL
);

alter table comments
    alter column id add generated by default as identity;

--
-- Name: personal_libraries; Type: TABLE;
--

CREATE TABLE if not exists personal_libraries
(
    user_id bigint NOT NULL,
    book_id bigint NOT NULL
);

--
-- Type: CONSTRAINTs;
--

ALTER TABLE ONLY book_files
    ADD CONSTRAINT book_files_pk PRIMARY KEY (id);

ALTER TABLE ONLY books
    ADD CONSTRAINT books_pk PRIMARY KEY (id);

ALTER TABLE ONLY request_statuses
    ADD CONSTRAINT request_statuses_pk PRIMARY KEY (id);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);

ALTER TABLE ONLY tags
    ADD CONSTRAINT tags_pk PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);

ALTER TABLE ONLY reports
    ADD CONSTRAINT reports_pk PRIMARY KEY (id);

ALTER TABLE ONLY help_requests
    ADD CONSTRAINT help_requests_pk PRIMARY KEY (id);

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pk PRIMARY KEY (id);

--
-- Type: FK CONSTRAINTs;
--

ALTER TABLE ONLY book_files
    ADD CONSTRAINT book_file_books_id_fk FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE ONLY book_files
    ADD CONSTRAINT book_files_fk_owner FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE ONLY books
    ADD CONSTRAINT books_fk_tag FOREIGN KEY (tag_id) REFERENCES tags (id);

ALTER TABLE ONLY books
    ADD CONSTRAINT books_fk_owner FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT user_fk_role FOREIGN KEY (role) REFERENCES roles (id);


ALTER TABLE ONLY reports
    ADD CONSTRAINT reports_fk_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE ONLY reports
    ADD CONSTRAINT reports_fk_status FOREIGN KEY (status) REFERENCES request_statuses (id);

ALTER TABLE ONLY reports
    ADD CONSTRAINT reports_fk_moderator FOREIGN KEY (moderator_id) REFERENCES users (id);

ALTER TABLE ONLY reports
    ADD CONSTRAINT reports_fk_book FOREIGN KEY (book_id) REFERENCES books (id);


ALTER TABLE ONLY help_requests
    ADD CONSTRAINT help_requests_fk_status FOREIGN KEY (status) REFERENCES request_statuses (id);

ALTER TABLE ONLY help_requests
    ADD CONSTRAINT help_requests_fk_moderator FOREIGN KEY (moderator_id) REFERENCES users (id);


ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_fk_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_fk_book FOREIGN KEY (book_id) REFERENCES books (id);


ALTER TABLE ONLY personal_libraries
    ADD CONSTRAINT personal_libraries_fk_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE ONLY personal_libraries
    ADD CONSTRAINT personal_libraries_fk_book FOREIGN KEY (book_id) REFERENCES books (id);

--
-- Type: TABLEs DATA;
--

insert into request_statuses (id, name)
values (default, 'Not processed');
insert into request_statuses (id, name)
values (default, 'Confirmed');
insert into request_statuses (id, name)
values (default, 'Declined');

insert into roles (id, name)
values (default, 'user');
insert into roles (id, name)
values (default, 'moderator');

insert into tags (id, name)
values (default, 'all');

insert into users (id, role, first_name, last_name, email, password, provider)
values (default, 2, 'admin', 'admin', 'admin@admin.com',
        '$2a$10$iWOIpv08YdHeiemEEOqm/O3QHvYtjdBn6azEIipixYe7hoA6rRhxa', 'local');

insert into books (id, updated_at, created_at, tag_id, name, owner_id)
values (default, now(), now(), 1, 'Init', 1);
