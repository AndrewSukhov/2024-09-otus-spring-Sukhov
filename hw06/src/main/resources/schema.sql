create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255) unique,
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table comments (
    id          bigserial,
    text        varchar(100),
    created_at  timestamp,
    book_id     bigint references books (id) on delete cascade,
    primary key (id)
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(20)           NOT NULL UNIQUE,
    password   VARCHAR               NOT NULL,
    firstname  VARCHAR(50)           NOT NULL,
    lastname   VARCHAR(50)           NOT NULL UNIQUE,
    email      VARCHAR(50)           NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE  NOT NULL,
    is_locked  BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- содержит пользователей и роли, которым будут даваться разрешения
-- sid содержит либо имя пользователя, либо название роли
CREATE TABLE acl_sid
(
    id        BIGSERIAL PRIMARY KEY,
    principal TINYINT      NOT NULL,
    sid       VARCHAR(100) NOT NULL,
    CONSTRAINT uk_acl_sid UNIQUE (sid, principal)
);

CREATE TABLE acl_class
(
    id    BIGSERIAL PRIMARY KEY,
    class VARCHAR(255) NOT NULL,
    CONSTRAINT uk_acl_class UNIQUE (class)
);

CREATE TABLE acl_object_identity
(
    id                 BIGSERIAL PRIMARY KEY,
    object_id_class    BIGINT  NOT NULL,
    object_id_identity BIGINT  NOT NULL,
    parent_object      BIGINT DEFAULT NULL,
    owner_sid          BIGINT DEFAULT NULL,
    entries_inheriting TINYINT NOT NULL,
    CONSTRAINT uk_acl_object_identity UNIQUE (object_id_class, object_id_identity),
    CONSTRAINT fk_parent_object FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_object_id_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_owner_sid FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
);

-- acl_sid - кому дается разрешение
-- acl_object_identity — на какой объект оно дается
-- mask — какое именно разрешение
CREATE TABLE acl_entry
(
    id                  BIGSERIAL PRIMARY KEY,
    acl_object_identity BIGINT  NOT NULL,
    ace_order           INT     NOT NULL,
    sid                 BIGINT  NOT NULL,
    mask                INT     NOT NULL,
    granting            TINYINT NOT NULL,
    audit_success       TINYINT NOT NULL,
    audit_failure       TINYINT NOT NULL,
    CONSTRAINT uk_acl_entry UNIQUE (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_object_identity FOREIGN KEY (acl_object_identity)
        REFERENCES acl_object_identity (id),
    CONSTRAINT fk_sid FOREIGN KEY (sid)
        REFERENCES acl_sid (id)
);