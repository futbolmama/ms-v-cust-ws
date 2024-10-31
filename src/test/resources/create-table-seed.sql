create table customer
(
    id          bigserial primary key,
    f_name      varchar(50)  not null,
    m_name      varchar(50),
    l_name      varchar(50)  not null,
    email       varchar(100) not null,
    phone       varchar(20),
    modify_date timestamp default CURRENT_TIMESTAMP,
    create_date timestamp default CURRENT_TIMESTAMP
);
commit;

insert into customer(f_name, m_name, l_name, email, phone)
values ('Melissa', 'Middle', 'Suarez', 'me@o.co', '333-333-4444');
commit;