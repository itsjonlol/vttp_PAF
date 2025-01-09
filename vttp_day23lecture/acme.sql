drop database if exists acme;

create database acme;
use acme;

create table departments (
    dept_id char(3) not null,
    name varchar(64) not null,
    constraint pk_dept_id primary key(dept_id)


);

create table employees (
    emp_id char(8) not null,
    name varchar(64) not null,
    dept_id char(3) not null, -- foreign key

    constraint pk_emp_id primary key(emp_id),
    constraint fk_dept_id foreign key(dept_id) references departments(dept_id)

);