# LoyaltyCard_Manager

A JavaFX-based desktop application that enables businesses to issue and manage loyalty cards (Silver, Gold, Platinum) to customers. It features real-time discount calculation, card expiry and renewal, transaction logging, and secure admin control.

---

## 📦 Features

- Customer registration and login
- Admin dashboard with:
  - Card assignment
  - Transaction monitoring
  - User management
- Loyalty card types with tiered discounts:
  - Silver (10%)
  - Gold (15%)
  - Platinum (20%)
- Card expiry: Valid for 1 year
- Auto-renewal of cards with balance update
- Full transaction history per user

---

## 🛠️ Technologies Used

- Java 17
- JavaFX 20
- FXML + Scene Builder
- MySQL 8
- JDBC (mysql-connector-j-9.3.0.jar)
- NetBeans IDE

---

## ⚙️ Project Folder Structure (Simplified)
src/
├── controller/ // JavaFX controllers
├── model/ // Business logic classes
├── loyaltycard_manager/ // FXML files + main class
└── resources/
└── database/
└── db_schema.sql // ✅ Ready-to-use SQL schema


To get started quickly:

1. Open **MySQL CLI or any GUI tool (e.g., phpMyAdmin, DBeaver)**  
2. Copy and run the entire contents of `db_schema.sql`  
3. This will:
   - Create the database `loyaltycard_manager`
   - Create all required tables
   - Preload card types
   - Insert a default admin user:  


4. Make sure your database user has privileges:

```sql
CREATE USER 'admin'@'%' IDENTIFIED BY '95Mu0_g,6Ps:';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
