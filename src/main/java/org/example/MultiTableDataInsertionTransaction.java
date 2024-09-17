package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MultiTableDataInsertionTransaction {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school",
                    "root",
                    "sunanda123"
            );

            connection.setAutoCommit(false);

            String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Sunanda");
            preparedStatement.setInt(3, 31);
            int affected = preparedStatement.executeUpdate();

            String sql1 = "INSERT INTO courses (id, name, credits) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, 1);
            preparedStatement1.setString(2, "Java");
            preparedStatement1.setInt(3, 10);
            int affected1 = preparedStatement1.executeUpdate();

            String sql2 = "INSERT INTO enrollments (id, student_id, course_id, grade) VALUES (NULL, ?, ?, ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, 1);
            preparedStatement2.setInt(2, 1);
            preparedStatement2.setString(3, "A");
            int affected2 = preparedStatement2.executeUpdate();
            System.out.println(affected + " and " + affected1+ " and "+affected2);

            if (affected == 1 && affected1 == 1 && affected2 == 1) {
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
