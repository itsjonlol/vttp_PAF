-- Write your Task 1 answers in this file

-- Drop database if it exists
DROP DATABASE IF EXISTS bedandbreakfast;

-- Create the database
CREATE DATABASE bedandbreakfast;

-- Select the database
USE bedandbreakfast;

-- Create the attendees table
SELECT "CREATING USERS";
CREATE TABLE users (
    
    email VARCHAR(128),
    name VARCHAR(128),
    constraint pk_email primary key(email)

);
SELECT "CREATING BOOKINGS";
CREATE TABLE bookings (
    
    booking_id char(8),
    listing_id varchar(20),
    duration int,
    email varchar(128),
    constraint pk_booking_id primary key(booking_id),
    constraint fk_email foreign key(email) references users(email)

);
SELECT "CREATING REVIEWS";
CREATE TABLE reviews (
    
    id int auto_increment,
    date timestamp DEFAULT CURRENT_TIMESTAMP,
    listing_id varchar(20),
    reviewer_name varchar(64),
    comments text,
  
    constraint pk_id primary key(id)


);
SELECT "INSERTING INTO USERS";
INSERT INTO users()
VALUES ("fred@gmail.com", "Fred Flintstone"),("barney@gmail.com", "Barney Rubble"),
("fry@planetexpress.com", "Philip J Fry"),("hlmer@gmail.com", "Homer Simpson");


-- Grant fred access to the database
GRANT ALL PRIVILEGES ON bedandbreakfast.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;