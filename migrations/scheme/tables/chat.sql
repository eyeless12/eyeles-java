create table if not exists chat
(
    id         int primary key generated always as identity,
    created_at timestamp with time zone not null default CURRENT_TIMESTAMP
);
