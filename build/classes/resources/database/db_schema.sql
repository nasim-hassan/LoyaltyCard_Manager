-- Create the user
CREATE USER 'admin'@'%' IDENTIFIED BY '95Mu0_g,6Ps:';

-- Grant all privileges on all databases and tables
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;

-- Apply the changes (optional, in modern MySQL this happens automatically)
FLUSH PRIVILEGES;

-- 🎯 Create database
CREATE DATABASE IF NOT EXISTS loyaltycard_manager;
USE loyaltycard_manager;

-- 🧍 Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT=1000;

-- 🧑‍💼 Admins Table
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 💠 Card Types Table
CREATE TABLE IF NOT EXISTS card_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('Silver', 'Gold', 'Platinum') UNIQUE NOT NULL,
    discount_percentage DECIMAL(5,2) NOT NULL,
    min_initial_payment DECIMAL(10,2) NOT NULL
);

-- 💳 Cards Table
CREATE TABLE IF NOT EXISTS cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number CHAR(16) NOT NULL UNIQUE,
    card_type_id INT NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    issued_by_admin_id INT,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date DATE,
    CHECK (card_number LIKE '4504%'),
    FOREIGN KEY (card_type_id) REFERENCES card_types(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (issued_by_admin_id) REFERENCES admins(id)
);


-- 💸 Transactions Table
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_id INT NOT NULL,
    txn_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    discount_applied DECIMAL(10,2),
    final_amount DECIMAL(10,2),
    balance_after_txn DECIMAL(10,2),
    txn_type ENUM('Purchase', 'Top-up', 'Refund') DEFAULT 'Purchase',
    remarks VARCHAR(255),
    FOREIGN KEY (card_id) REFERENCES cards(id)
);

-- 🚀 Preload Card Types
INSERT INTO card_types (name, discount_percentage, min_initial_payment)
VALUES 
('Silver', 10.00, 5000.00),
('Gold', 15.00, 10000.00),
('Platinum', 20.00, 15000.00);


-- Create Default Admin
INSERT INTO admins (username, password) VALUES ('admin', 'admin');