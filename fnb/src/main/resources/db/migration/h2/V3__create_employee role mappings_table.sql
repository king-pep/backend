create sequence employeeRoleMapping_id_seq start with 1 increment by 100;

create table employee role mappings (
    id bigint DEFAULT nextval('employeeRoleMapping_id_seq') not null,
    text varchar(1024) not null,
    primary key (id)
);
