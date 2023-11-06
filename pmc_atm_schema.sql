CREATE DATABASE  IF NOT EXISTS pmc_atm_schema;
USE pmc_atm_schema;

--Table for Bank
DROP TABLE IF EXISTS `Bank`;
CREATE TABLE Bank (
    bank_id int(11) AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
     PRIMARY KEY (`bank_id`),
     UNIQUE (name)
);

--Values
INSERT INTO Bank (name, location) VALUES ('HDFC', 'Vadodara');
INSERT INTO Bank (name, location) VALUES ('AXIS', 'Vadodara');


-- Table for ATM
DROP TABLE IF EXISTS `ATM`;
CREATE TABLE ATM (
    atm_id INT PRIMARY KEY AUTO_INCREMENT,
    bank_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance float NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES Bank(bank_id)
);

--Values
INSERT INTO ATM (bank_id, location, balance) VALUES (1, 'Maneja', 10000.00);
INSERT INTO ATM (bank_id, location, balance) VALUES (2, 'Makarpura', 15000.00);

-- Table for Customer
DROP TABLE IF EXISTS `Customer`;
CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    bank_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    address VARCHAR(255),
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bank_id) REFERENCES Bank(bank_id)
);

--Values
INSERT INTO Customer (bank_id, first_name, last_name, email, phone_number, address, date_of_birth)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', '1234567890', '123 Main St', '1990-01-15');
INSERT INTO Customer (bank_id, first_name, last_name, email, phone_number, address, date_of_birth)
VALUES (1, 'Jane', 'Smith', 'jane.smith@example.com', '9876543210', '456 MS St', '1985-03-20');
INSERT INTO Customer (bank_id, first_name, last_name, email, phone_number, address, date_of_birth)
VALUES (2, 'Oliver', 'D', 'oliver.d@example.com', '9433232103', '45 las St', '1999-03-20');
INSERT INTO Customer (bank_id, first_name, last_name, email, phone_number, address, date_of_birth)
VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com', '7834657284', '6 round St', '2004-03-02');

-- Table for table Accounts
DROP TABLE IF EXISTS `Accounts`;
CREATE TABLE Accounts(
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type ENUM('Savings', 'Checking') NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
     password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

--Values
INSERT INTO Accounts (customer_id, account_number, account_type, balance)
VALUES (1, '1234567890', 'Savings', 5000.00);
INSERT INTO Accounts (customer_id, account_number, account_type, balance)
VALUES (2, '9876543210', 'Checking', 2500.00);


-- Table for Transactions
DROP TABLE IF EXISTS `Transactions`;
CREATE TABLE Transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    transaction_type ENUM('Deposit', 'Withdrawal', 'Transfer') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);

--Values
INSERT INTO Transactions (account_id, transaction_type, amount)
VALUES (1, 'Deposit', 1000.00);
INSERT INTO Transactions (account_id, transaction_type, amount)
VALUES (2, 'Withdrawal', 500.00);


-- Dump completed
