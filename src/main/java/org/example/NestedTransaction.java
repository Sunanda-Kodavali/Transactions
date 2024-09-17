package org.example;

import java.sql.*;

public class NestedTransaction
{
    public static void main(String[] args) {
        Connection connection = null;
        Savepoint outerSavepoint = null;

        try {
             connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/inventory",
                    "root",
                    "sunanda123"
            );

            connection.setAutoCommit(false);

                // Perform an update in the outer transaction
                String outerUpdateSQL = "UPDATE products SET quantity = ? WHERE id = ?";
                try (PreparedStatement outerStatement = connection.prepareStatement(outerUpdateSQL)) {
                    outerStatement.setInt(1, 100);
                    outerStatement.setInt(2, 1);
                    outerStatement.executeUpdate();
                }
                outerSavepoint = connection.setSavepoint("OuterSavepoint");

                try {
                    String nestedUpdateSQL = "UPDATE products SET price = ? WHERE id = ?";
                    try (PreparedStatement nestedStatement = connection.prepareStatement(nestedUpdateSQL)) {
                        nestedStatement.setDouble(1, 29.99); // Set new price
                        nestedStatement.setInt(2, 1); // ID of the product
                        nestedStatement.executeUpdate();
                    }

                    connection.commit();

                } catch (SQLException e) {
                    System.err.println("Nested transaction error: " + e.getMessage());
                    connection.rollback(outerSavepoint);
                }

                String additionalUpdateSQL = "UPDATE products SET description = ? WHERE id = ?";
                try (PreparedStatement additionalStatement = connection.prepareStatement(additionalUpdateSQL)) {
                    additionalStatement.setString(1, "Updated description");
                    additionalStatement.setInt(2, 1);
                    additionalStatement.executeUpdate();
                }

                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
    }
}