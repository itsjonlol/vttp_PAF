-- Drop database if it exists
DROP DATABASE IF EXISTS acme;

-- Create the database
CREATE DATABASE acme;

-- Select the database
USE acme;

-- Create the employees table
CREATE TABLE employees (
    emp_id CHAR(8) NOT NULL, -- This is going to be the primary key
    name_emp VARCHAR(128) NOT NULL,
    salary DECIMAL(10,2) DEFAULT 4500.00,
    dob DATE NOT NULL,
    dept_id INT,
    CONSTRAINT pk_emp_id PRIMARY KEY (emp_id),
    CONSTRAINT chk_salary CHECK (salary >= 1500)
);

-- Create the dept table
CREATE TABLE dept (
    dept_id INT AUTO_INCREMENT,
    name_dept VARCHAR(64) NOT NULL,
    CONSTRAINT pk_dept_id PRIMARY KEY (dept_id)
);

-- Grant fred access to the database
GRANT ALL PRIVILEGES ON acme.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;

