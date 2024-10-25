package org.example.parcial2.utils;

import org.example.parcial2.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            return rs.next();
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
        String query = "SELECT * FROM Usuarios WHERE User = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(String name, String phone, String email, String username, String password, String role) {
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nombre"),
                        rs.getString("telUser"),
                        rs.getString("emailUser"),
                        rs.getString("User"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUserById(int id) {
        String query = "DELETE FROM Usuarios WHERE idUser = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos para manejar géneros
    public List<String[]> getAllGenres() {
        List<String[]> genres = new ArrayList<>();
        String query = "SELECT * FROM Genero";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(new String[]{String.valueOf(rs.getInt("idGenero")), rs.getString("nombreGenero")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;
    }

    public boolean addGenre(String nombreGenero) {
        String query = "INSERT INTO Genero (nombreGenero) VALUES (?)";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreGenero);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGenreById(int id) {
        String query = "DELETE FROM Genero WHERE idGenero = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
