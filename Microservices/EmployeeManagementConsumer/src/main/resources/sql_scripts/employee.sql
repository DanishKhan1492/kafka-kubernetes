create table country
(
    country_id   int auto_increment
        primary key,
    country_name varchar(100) not null,
    constraint country_country_name_uindex
        unique (country_name)
);

create table city
(
    city_id    int auto_increment
        primary key,
    city_name  varchar(100) not null,
    country_id int          not null,
    constraint city_city_name_uindex
        unique (city_name),
    constraint city_country_fk
        foreign key (country_id) references country (country_id)
            on update cascade
);

create table addressDTO
(
    address_id int auto_increment
        primary key,
    address1   varchar(1000) not null,
    address2   varchar(1000) null,
    city       int           not null,
    constraint city_FK
        foreign key (city) references city (city_id)
);

create table contactDTO
(
    contact_id   int auto_increment
        primary key,
    contact_type varchar(50)  not null,
    contactDTO      varchar(200) not null
);

create table department
(
    department_id int auto_increment
        primary key,
    dep_name      varchar(200)  not null,
    description   varchar(1000) null,
    constraint department_dep_name_uindex
        unique (dep_name)
);

create table employee_type
(
    employee_type_id int auto_increment
        primary key,
    emp_type_name    varchar(100) not null,
    constraint employee_type_emp_type_name_uindex
        unique (emp_type_name)
);

create table organization
(
    org_id          int auto_increment
        primary key,
    org_name        int          not null,
    org_description varchar(500) null,
    constraint organization_org_name_uindex
        unique (org_name)
);

create table employeeDTO
(
    employee_id      int auto_increment
        primary key,
    first_name       varchar(100)       not null,
    last_name        varchar(100)       not null,
    date_of_birth    date               not null,
    salary           double             null,
    status           smallint default 0 not null,
    manager_id       int                null,
    org_id           int                not null,
    department_id    int                not null,
    employee_type_id int                not null,
    address_id       int                null,
    contact_id       int                not null,
    constraint address_FK
        foreign key (address_id) references addressDTO (address_id)
            on update cascade on delete cascade,
    constraint contact_FK
        foreign key (contact_id) references contactDTO (contact_id)
            on update cascade on delete cascade,
    constraint department_FK
        foreign key (department_id) references department (department_id),
    constraint employee_FK
        foreign key (manager_id) references employeeDTO (employee_id)
            on update cascade on delete set null,
    constraint employee_type_FK
        foreign key (employee_type_id) references employee_type (employee_type_id),
    constraint org_FK
        foreign key (org_id) references organization (org_id)
            on update cascade
);
