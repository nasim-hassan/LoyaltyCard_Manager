-- loyalty_db schema

CREATE DATABASE IF NOT EXISTS loyaltycard_manager;
USE loyaltycard_manager;

-- 🧍 User Table (customers)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT=1000;


-- 🧑‍💼 Admin Table
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 💳 Card Table
CREATE TABLE IF NOT EXISTS cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(50) NOT NULL UNIQUE,
    card_type ENUM('Silver', 'Gold', 'Platinum') NOT NULL,
    discount_percentage DECIMAL(5,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    issued_by_admin_id INT,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
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
    FOREIGN KEY (card_id) REFERENCES cards(id)
);
