# Library-Management-System
A Java-based Library Management System with MySQL integration to handle book and member records. Features include adding, removing, searching books, registering members, issuing, and returning books.

## 🚀 Features
- ✅ Add Book  
- ❌ Remove Book  
- 🔍 Search Book  
- 👥 Register Member  
- 📥 Issue Book  
- 📤 Return Book

## 🛠️ Tech Stack

- **Programming Language:** Java  
- **Database:** MySQL  
- **IDE Used:** IntelliJ IDEA

## 📦 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/anjali2740/library-management-system.git
   cd library-management-system

2. **Set up the MySQL database**
   - Login to MySQL using:
Username: admin
Password: admin123
   - Execute the following SQL commands:
CREATE DATABASE library;
USE library;

CREATE TABLE members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150),
    author VARCHAR(100)
);

CREATE TABLE issues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    book_id INT,
    issue_date DATE,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

3. Configure Database Connection in Java:
String url = "jdbc:mysql://localhost:3306/library";
String user = "admin";
String password = "admin123";

4. Run the application
- Open the project in IntelliJ IDEA.
- download mysql connector file 
- Compile and run the Main.java file.

👩‍💻 Author
Angadi Anjali
📧 2210030411cse@gmail.com
🔗 LinkedIn : www.linkedin.com/in/angadi-anjali

