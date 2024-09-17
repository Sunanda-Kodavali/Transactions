package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank",
                    "root",
                    "sunanda123"
            );
            connection.setAutoCommit(false);

            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 500);
            preparedStatement.setInt(2, 1);
            int affected = preparedStatement.executeUpdate();

            String sql1 = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, 500);
            preparedStatement1.setInt(2, 2);
            int affected1 = preparedStatement1.executeUpdate();

            System.out.println(affected + " and " + affected1);

            if (affected == 1 && affected1 == 1) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}