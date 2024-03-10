create table if not exists link
(
    id              int primary key generated always as identity,
    url             varchar(255) unique not null,
    last_check_time timestamp with time zone not null,
    created_at      timestamp with time zone not null default CURRENT_TIMESTAMP
);
