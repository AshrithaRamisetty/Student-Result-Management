import java.sql.*;
import java.util.Scanner;

public class StudentJDBC {
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "ashu132"; // Replace with your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("===== Student Result Management System =====");
            System.out.print("1. Add New Student\n2. Search by Roll Number\nChoose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            if (choice == 1) {
                System.out.print("Enter Student Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Roll Number: ");
                int roll = sc.nextInt();

                System.out.println("Enter marks for 3 subjects:");
                System.out.print("Subject 1: ");
                int s1 = sc.nextInt();
                System.out.print("Subject 2: ");
                int s2 = sc.nextInt();
                System.out.print("Subject 3: ");
                int s3 = sc.nextInt();

                String query = "INSERT INTO students(name, roll, subject1, subject2, subject3) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setInt(2, roll);
                ps.setInt(3, s1);
                ps.setInt(4, s2);
                ps.setInt(5, s3);
                ps.executeUpdate();

                System.out.println("✅ Student record added successfully.");
            } else if (choice == 2) {
                System.out.print("Enter Roll Number to search: ");
                int roll = sc.nextInt();

                String query = "SELECT * FROM students WHERE roll = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, roll);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    int s1 = rs.getInt("subject1");
                    int s2 = rs.getInt("subject2");
                    int s3 = rs.getInt("subject3");
                    int total = s1 + s2 + s3;
                    double avg = total / 3.0;
                    String grade = (avg >= 90) ? "A+" : (avg >= 75) ? "A" : (avg >= 60) ? "B" : (avg >= 50) ? "C" : "Fail";

                    System.out.println("\n===== Result for Roll No: " + roll + " =====");
                    System.out.println("Name     : " + name);
                    System.out.println("Subject Marks: " + s1 + ", " + s2 + ", " + s3);
                    System.out.println("Total    : " + total);
                    System.out.println("Average  : " + avg);
                    System.out.println("Grade    : " + grade);
                } else {
                    System.out.println("❌ Student not found.");
                }
            } else {
                System.out.println("❌ Invalid choice.");
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Database Error: " + e.getMessage());
        }
    }
}
