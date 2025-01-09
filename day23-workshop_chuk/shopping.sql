drop database if exists shopping;

create database shopping;
use shopping;



create table purchaseorders (
    purchase_id int auto_increment,
    username varchar(64),
    address varchar(256),
    delivery_date date,
    constraint pk_purchase_id primary key(purchase_id)


);

create table lineitems (
    line_id int auto_increment,
    name varchar(64),
    quantity int,
    unit_price decimal(5,2),
    purchase_id int,


    constraint pk_line_id primary key(line_id),
    constraint fk_purchase_id foreign key(purchase_id) references purchaseorders(purchase_id)

);