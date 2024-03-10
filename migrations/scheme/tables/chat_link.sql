create table if not exists chat_link
(
    chat_id int not null references chat(id),
    link_id int not null references link(id),
    primary key (chat_id, link_id)
);
