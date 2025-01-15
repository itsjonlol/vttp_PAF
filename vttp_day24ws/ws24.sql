-- Drop database if it exists
DROP DATABASE IF EXISTS ws24;

-- Create the database
CREATE DATABASE ws24;

-- Select the database
USE ws24;

-- Create the order table
CREATE TABLE orders (
    
    order_id int auto_increment,
    order_date date default (current_date),
    customer_name varchar(128),
    ship_address varchar(128),
    notes text,
    tax decimal(2,2) default 0.05,
    constraint pk_order_id primary key(order_id)

);

CREATE TABLE orderdetails(
    
    details_id int auto_increment,
    product varchar(64),
    unit_price decimal(3,2),
    discount decimal(2,2) default 0.99,
    quantity int,
    order_id int,
    constraint pk_details_id primary key(details_id),
    constraint fk_order_id foreign key(order_id) references orders(order_id)
    

);

-- Grant fred access to the database
GRANT ALL PRIVILEGES ON ws24.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;
