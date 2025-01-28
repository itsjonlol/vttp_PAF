-- Write your Task 1 answers in this file

-- Drop database if it exists
DROP DATABASE IF EXISTS mybnb;

-- Create the database
CREATE DATABASE mybnb;

-- Select the database
USE mybnb;

-- Create the attendees table
SELECT "CREATING ACC_OCCUPANCY_TABLE";
CREATE TABLE acc_occupancy (
    
    acc_id VARCHAR(10),
    vacancy INT,
    constraint pk_acc_id primary key(acc_id),
    CONSTRAINT chk_vacancy CHECK (vacancy >= 0)

);
SELECT "CREATING RESERVATIONS_TABLE";
CREATE TABLE reservations (
    
    resv_id char(8),
    name varchar(128),
    email varchar(128),
    acc_id VARCHAR(10),
    arrival_date date,
    duration int,
    constraint pk_resv_id primary key(resv_id),
    constraint fk_acc_id foreign key(acc_id) references acc_occupancy(acc_id)

);

-- Grant fred access to the database
GRANT ALL PRIVILEGES ON mybnb.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;