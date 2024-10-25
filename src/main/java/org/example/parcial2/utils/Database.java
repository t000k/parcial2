package org.example.parcial2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

    private Connection connectDB() {
        String url = "jdbc:mysql://localhost:3306/spotify";
        String user = "root";
        String password = "sistemaPlan";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Usuarios WHERE User = ? AND password = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si encuentra un registro, el usuario existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(String username) {
        String query = "SELECT role FROM Usuarios WHERE User = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isUserExists(String username) {
        // Verificar si el nombre de usuario ya existe en la base de datos
        String query = "SELECT * FROM Usuarios WHERE User = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si encuentra un registro, el usuario ya existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(String name, String phone, String email, String username, String password, String role) {
        // Antes de crear el usuario, verifica si el nombre de usuario ya existe
        if (isUserExists(username)) {
            System.out.println("El usuario ya existe.");
            return;
        }

        String query = "INSERT INTO Usuarios (nombre, telUser, emailUser, User, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setString(6, role);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
