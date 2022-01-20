create sequence roleMaster_id_seq start with 1 increment by 100;

create table role masters (
    id bigint DEFAULT nextval('roleMaster_id_seq') not null,
    text varchar(1024) not null,
    primary key (id)
);
