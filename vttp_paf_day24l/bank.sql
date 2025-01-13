-- Drop database if it exists
DROP DATABASE IF EXISTS bank;

-- Create the database
CREATE DATABASE bank;

-- Select the database
USE bank;


create table BankAccount (
    id int not null auto_increment,
    fullName varchar(150) not null,
    isActive boolean,
    balance float default '100.0',
    constraint pb_bankaccount_id primary key (id)
);





-- Grant fred access to the database
GRANT ALL PRIVILEGES ON bank.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;

