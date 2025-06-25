import java.sql.*;
import java.util.*;

public class LibraryManagementSystem {
    static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    static final String USER = "root";
    static final String PASS = "root";

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database.");
            adminLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void adminLogin() throws SQLException {
        System.out.print("Enter admin username: ");
        String username = sc.nextLine();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Login successful.");
            showMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void showMenu() throws SQLException {
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book\n2. Remove Book\n3. Search Book\n4. Register Member\n5. Issue Book\n6. Return Book\n7. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addBook(); break;
                case 2: removeBook(); break;
                case 3: searchBook(); break;
                case 4: registerMember(); break;
                case 5: issueBook(); break;
                case 6: returnBook(); break;
                case 7: conn.close(); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void addBook() throws SQLException {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        System.out.print("Enter author: ");
        String author = sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO books(title, author) VALUES (?, ?)");
        stmt.setString(1, title);
        stmt.setString(2, author);
        stmt.executeUpdate();
        System.out.println("Book added successfully.");
    }

    static void removeBook() throws SQLException {
        System.out.print("Enter book ID to remove: ");
        int bookId = sc.nextInt();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
        stmt.setInt(1, bookId);
        int rows = stmt.executeUpdate();
        if (rows > 0) System.out.println("Book removed.");
        else System.out.println("Book not found.");
    }

    static void searchBook() throws SQLException {
        System.out.print("Enter book title or author to search: ");
        String keyword = sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE title LIKE ? OR author LIKE ?");
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") + ", Author: " + rs.getString("author"));
        }
    }

    static void registerMember() throws SQLException {
        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO members(name) VALUES (?)");
        stmt.setString(1, name);
        stmt.executeUpdate();
        System.out.println("Member registered.");
    }

    static void issueBook() throws SQLException {
        System.out.print("Enter member ID: ");
        int memberId = sc.nextInt();
        System.out.print("Enter book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO issues(member_id, book_id, issue_date) VALUES (?, ?, CURDATE())");
        stmt.setInt(1, memberId);
        stmt.setInt(2, bookId);
        stmt.executeUpdate();
        System.out.println("Book issued.");
    }

    static void returnBook() throws SQLException {
        System.out.print("Enter issue ID: ");
        int issueId = sc.nextInt();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT issue_date FROM issues WHERE id = " + issueId);
        if (rs.next()) {
            java.sql.Date issueDate = rs.getDate("issue_date");  // JDBC returns java.sql.Date
            long diff = (new java.util.Date().getTime() - issueDate.getTime()) / (1000 * 60 * 60 * 24);
            int fine = (diff > 14) ? (int)(diff - 14) * 5 : 0;

            PreparedStatement update = conn.prepareStatement("UPDATE issues SET return_date = CURDATE(), fine = ? WHERE id = ?");
            update.setInt(1, fine);
            update.setInt(2, issueId);
            update.executeUpdate();
            System.out.println("Book returned. Fine: Rs." + fine);
        } else {
            System.out.println("Issue ID not found.");
        }
    }
}