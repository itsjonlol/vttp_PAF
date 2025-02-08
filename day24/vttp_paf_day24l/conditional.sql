USE bank;

CREATE TABLE accounts (
    acct_id VARCHAR(8) PRIMARY KEY,
    balance DECIMAL(10,2),
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO accounts (acct_id, balance) VALUES ('abc123', 500.00);
INSERT INTO accounts (acct_id, balance) VALUES ('xyz789', 1000.00);

-- Grant fred access to the database
GRANT ALL PRIVILEGES ON bank.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;