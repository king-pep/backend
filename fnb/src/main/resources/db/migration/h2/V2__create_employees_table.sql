create sequence employee_id_seq start with 1 increment by 100;

create table employees (
    id bigint DEFAULT nextval('employee_id_seq') not null,
    text varchar(1024) not null,
    primary key (id)
);
