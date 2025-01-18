-- Drop database if it exists
DROP DATABASE IF EXISTS rsvpdb;

-- Create the database
CREATE DATABASE rsvpdb;

-- Select the database
USE rsvpdb;

-- Create the attendees table
CREATE TABLE rsvp (
    
    rsvp_id int auto_increment,
    email VARCHAR(128),
    phone char(8),
    confirmdate DATE,
    comments TEXT,
    constraint pk_rsvp_id primary key(rsvp_id)

);

-- Grant fred access to the database
GRANT ALL PRIVILEGES ON rsvpdb.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;
