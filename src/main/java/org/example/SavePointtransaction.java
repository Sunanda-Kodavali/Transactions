package org.example;

import java.sql.*;

public class SavePointtransaction {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school",
                    "root",
                    "sunanda123"
            );

            connection.setAutoCommit(false);

            String sql = "UPDATE students SET name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Hari");
            preparedStatement.setInt(2, 1);
            int affected = preparedStatement.executeUpdate();

            Savepoint studentSavepoint = connection.setSavepoint("Student");

            String sql1 = "UPDATE courses SET name = ? WHERE id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, "Golang");
            preparedStatement1.setInt(2, 1);
            int affected1 = preparedStatement1.executeUpdate();

            Savepoint courseSavepoint = connection.setSavepoint("Course");

            String sql2 = "UPDATE enrollments SET grade = ? WHERE id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, "B");
            preparedStatement2.setInt(2, 1);
            int affected2 = preparedStatement2.executeUpdate();

            if(affected2 != 1){
                connection.rollback(courseSavepoint);
                connection.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
