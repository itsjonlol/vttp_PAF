-- Drop database if it exists
DROP DATABASE IF EXISTS rsvpdb;

-- Create the database
CREATE DATABASE rsvpdb;

-- Select the database
USE rsvpdb;

-- Create the attendees table
CREATE TABLE rsvp (
    
    
    
    email VARCHAR(128) PRIMARY KEY,
    phone char(8),
    confirmdate DATE,
    comments TEXT
    

);



-- Grant fred access to the database
GRANT ALL PRIVILEGES ON rsvpdb.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;
