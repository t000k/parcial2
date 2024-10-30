package org.example.parcial2.utils;

import org.example.parcial2.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.sql.Statement;



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

    // Métodos para manejo de usuarios
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
        String query = "SELECT idGenero, nombreGenero FROM Genero";
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
    //Métodos para manejar Albumes
   /* public boolean addAlbum(String nombreAlbum, String fechaLanzamiento) {
        String query = "INSERT INTO Album (nombreAlbum, fechaLanzamiento) VALUES (?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreAlbum);
            stmt.setString(2, fechaLanzamiento);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/
    public boolean addAlbum(String nombreAlbum, String fechaLanzamiento, File imageFile) {
        String query = "INSERT INTO Album (nombreAlbum, fechaLanzamiento, albumImage) VALUES (?, ?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreAlbum);
            stmt.setString(2, fechaLanzamiento);

            if (imageFile != null) {
                byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
                stmt.setBytes(3, imageBytes);
            } else {
                stmt.setNull(3, java.sql.Types.BLOB);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAlbumById(int id) {
        String query = "DELETE FROM Album WHERE idAlbum = ?";
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

    public byte[] getAlbumImageById(int albumId) {
        String query = "SELECT albumImage FROM Album WHERE idAlbum = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("albumImage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String[]> getAllAlbumsWithDetails() {
        List<String[]> albums = new ArrayList<>();
        String query = "SELECT idAlbum, nombreAlbum, fechaLanzamiento FROM Album";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                albums.add(new String[]{
                        String.valueOf(rs.getInt("idAlbum")),
                        rs.getString("nombreAlbum"),
                        rs.getString("fechaLanzamiento")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    public List<String[]> getSongsByAlbumId(int albumId) {
        List<String[]> songs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.titulo, c.duracion 
        FROM Cancion c
        JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
        WHERE ac.idAlbum = ?
    """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    // Métodos para manejar artistas
    public List<String[]> getAllArtists() {
        List<String[]> artists = new ArrayList<>();
        String query = "SELECT idArtista, nombreArtista FROM Artista";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                artists.add(new String[]{String.valueOf(rs.getInt("idArtista")), rs.getString("nombreArtista")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }

    public List<String[]> getAllArtistsWithDetails() {
        List<String[]> artists = new ArrayList<>();
        String query = "SELECT idArtista, nombreArtista, nacionalidad FROM Artista";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                artists.add(new String[]{
                        String.valueOf(rs.getInt("idArtista")),
                        rs.getString("nombreArtista"),
                        rs.getString("nacionalidad")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }


    public boolean addArtist(String nombreArtista, String nacionalidad) {
        String query = "INSERT INTO Artista (nombreArtista, nacionalidad) VALUES (?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreArtista);
            stmt.setString(2, nacionalidad);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtistById(int id) {
        String query = "DELETE FROM Artista WHERE idArtista = ?";
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

    // Métodos para manejar álbumes
    public List<String[]> getAllAlbums() {
        List<String[]> albums = new ArrayList<>();
        String query = "SELECT idAlbum, nombreAlbum FROM Album";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                albums.add(new String[]{String.valueOf(rs.getInt("idAlbum")), rs.getString("nombreAlbum")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    // Métodos para manejar canciones y relaciones usando Interpretacion y Album_Cancion
    public boolean addSong(String titulo, String duracion, int generoId, int artistaId, int albumId) {
        try (Connection conn = connectDB()) {
            String songQuery = "INSERT INTO Cancion (titulo, duracion, idGenero) VALUES (?, ?, ?)";
            try (PreparedStatement songStmt = conn.prepareStatement(songQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                songStmt.setString(1, titulo);
                songStmt.setString(2, duracion);  // duracion como String en formato "HH:MM:SS"
                songStmt.setInt(3, generoId);
                songStmt.executeUpdate();

                ResultSet generatedKeys = songStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int cancionId = generatedKeys.getInt(1);

                    // Relacionar canción con artista usando Interpretacion
                    String artistRelationQuery = "INSERT INTO Interpretacion (idCancion, idArtista) VALUES (?, ?)";
                    try (PreparedStatement artistStmt = conn.prepareStatement(artistRelationQuery)) {
                        artistStmt.setInt(1, cancionId);
                        artistStmt.setInt(2, artistaId);
                        artistStmt.executeUpdate();
                    }

                    // Relacionar canción con álbum usando Album_Cancion
                    String albumRelationQuery = "INSERT INTO Album_Cancion (idCancion, idAlbum) VALUES (?, ?)";
                    try (PreparedStatement albumStmt = conn.prepareStatement(albumRelationQuery)) {
                        albumStmt.setInt(1, cancionId);
                        albumStmt.setInt(2, albumId);
                        albumStmt.executeUpdate();
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteSongById(int id) {
        String query = "DELETE FROM Cancion WHERE idCancion = ?";
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


    public List<String[]> getAllSongsWithDetails() {
        List<String[]> songs = new ArrayList<>();
        String query = """
            SELECT c.idCancion, c.titulo, c.duracion, g.nombreGenero, a.nombreArtista, al.nombreAlbum
            FROM Cancion c
            JOIN Genero g ON c.idGenero = g.idGenero
            JOIN Interpretacion i ON c.idCancion = i.idCancion
            JOIN Artista a ON i.idArtista = a.idArtista
            JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
            JOIN Album al ON ac.idAlbum = al.idAlbum
            """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion"),
                        rs.getString("nombreGenero"),
                        rs.getString("nombreArtista"),
                        rs.getString("nombreAlbum")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }


    //***** seccion para Usuarios  *****

    //Comprar Canciones
    public List<String[]> getAvailableSongs() {
        List<String[]> songs = new ArrayList<>();
        String query = "SELECT c.idCancion, c.titulo, c.duracion, g.nombreGenero FROM Cancion c JOIN Genero g ON c.idGenero = g.idGenero";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion"),
                        rs.getString("nombreGenero")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public List<String[]> getAvailableSongsWithAlbum() {
        List<String[]> songs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.titulo, c.duracion, a.nombreAlbum
        FROM Cancion c
        JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
        JOIN Album a ON ac.idAlbum = a.idAlbum
    """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion"),
                        rs.getString("nombreAlbum")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public List<String[]> getAvailableAlbumsWithImages() {
        List<String[]> albums = new ArrayList<>();
        String query = "SELECT idAlbum, nombreAlbum, fechaLanzamiento FROM Album";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                albums.add(new String[]{
                        String.valueOf(rs.getInt("idAlbum")),
                        rs.getString("nombreAlbum"),
                        rs.getString("fechaLanzamiento")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    public List<String[]> getAvailableSongsWithAlbumAndArtist() {
        List<String[]> songs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.titulo, c.duracion, a.nombreAlbum, ar.nombreArtista
        FROM Cancion c
        JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
        JOIN Album a ON ac.idAlbum = a.idAlbum
        JOIN Interpretacion i ON c.idCancion = i.idCancion
        JOIN Artista ar ON i.idArtista = ar.idArtista
    """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion"),
                        rs.getString("nombreAlbum"),
                        rs.getString("nombreArtista")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    // manejar Albumes
    public byte[] getAlbumImage(int albumId) {
        String query = "SELECT albumImage FROM Album WHERE idAlbum = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("albumImage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean registerPurchase(List<String[]> carrito) {
        String ventaQuery = "INSERT INTO Venta (fechaVenta, totalVenta, idUser) VALUES (NOW(), ?, ?)";
        String detalleQuery = "INSERT INTO Detalle_Venta (idVenta, idCancion, precio) VALUES (?, ?, ?)";
        try (Connection conn = connectDB()) {
            conn.setAutoCommit(false);

            // Calcular total y registrar la venta
            double total = carrito.size() * 10.00; // Precio ficticio para cada canción
            PreparedStatement ventaStmt = conn.prepareStatement(ventaQuery, Statement.RETURN_GENERATED_KEYS);
            ventaStmt.setDouble(1, total);
            ventaStmt.setInt(2, 1); // ID de usuario temporal
            ventaStmt.executeUpdate();
            ResultSet ventaRs = ventaStmt.getGeneratedKeys();
            ventaRs.next();
            int idVenta = ventaRs.getInt(1);

            // Registrar detalles de la venta
            PreparedStatement detalleStmt = conn.prepareStatement(detalleQuery);
            for (String[] song : carrito) {
                detalleStmt.setInt(1, idVenta);
                detalleStmt.setInt(2, Integer.parseInt(song[0]));
                detalleStmt.setDouble(3, 10.00); // Precio ficticio
                detalleStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<String[]> getAvailableAlbums() {
        List<String[]> albums = new ArrayList<>();
        String query = "SELECT idAlbum, nombreAlbum, fechaLanzamiento FROM Album";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                albums.add(new String[]{
                        String.valueOf(rs.getInt("idAlbum")),
                        rs.getString("nombreAlbum"),
                        rs.getString("fechaLanzamiento")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    public boolean registerAlbumPurchase(List<String[]> carrito) {
        String ventaQuery = "INSERT INTO Venta (fechaVenta, totalVenta, idUser) VALUES (NOW(), ?, ?)";
        String detalleQuery = "INSERT INTO Detalle_Venta (idVenta, idCancion, precio) VALUES (?, ?, ?)";
        try (Connection conn = connectDB()) {
            conn.setAutoCommit(false);

            double total = carrito.size() * 50.00; // Precio ficticio para cada álbum
            PreparedStatement ventaStmt = conn.prepareStatement(ventaQuery, Statement.RETURN_GENERATED_KEYS);
            ventaStmt.setDouble(1, total);
            ventaStmt.setInt(2, 1); // ID de usuario temporal
            ventaStmt.executeUpdate();
            ResultSet ventaRs = ventaStmt.getGeneratedKeys();
            ventaRs.next();
            int idVenta = ventaRs.getInt(1);

            PreparedStatement detalleStmt = conn.prepareStatement(detalleQuery);
            for (String[] album : carrito) {
                List<String[]> albumSongs = getSongsByAlbumId(Integer.parseInt(album[0]));
                for (String[] song : albumSongs) {
                    detalleStmt.setInt(1, idVenta);
                    detalleStmt.setInt(2, Integer.parseInt(song[0]));
                    detalleStmt.setDouble(3, 10.00); // Precio ficticio por canción en el álbum
                    detalleStmt.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //se puede eliminar este:
    public List<String[]> getAlbumSongsWithArtist(int albumId) {
        List<String[]> songs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.titulo, ar.nombreArtista
        FROM Cancion c
        JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
        JOIN Interpretacion i ON c.idCancion = i.idCancion
        JOIN Artista ar ON i.idArtista = ar.idArtista
        WHERE ac.idAlbum = ?
    """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("nombreArtista")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }
    public List<String[]> getAlbumSongsWithDetails(int albumId) {
        List<String[]> songs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.titulo, c.duracion, ar.nombreArtista
        FROM Cancion c
        JOIN Album_Cancion ac ON c.idCancion = ac.idCancion
        JOIN Interpretacion i ON c.idCancion = i.idCancion
        JOIN Artista ar ON i.idArtista = ar.idArtista
        WHERE ac.idAlbum = ?
    """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(new String[]{
                        String.valueOf(rs.getInt("idCancion")),
                        rs.getString("titulo"),
                        rs.getString("duracion"),
                        rs.getString("nombreArtista")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    // manejar el historial y generar un reporte PDF

    public List<String[]> getHistorialCompras() {
        List<String[]> historial = new ArrayList<>();
        String query = """
            SELECT v.fechaVenta, v.totalVenta, 'Compra' AS tipo, v.idVenta 
            FROM Venta v 
            WHERE v.idUser = ? 
            ORDER BY v.fechaVenta DESC
        """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, 1); // ID de usuario temporal
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historial.add(new String[]{
                        rs.getString("fechaVenta"),
                        rs.getString("totalVenta"),
                        rs.getString("tipo"),
                        String.valueOf(rs.getInt("idVenta"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historial;
    }

   // manejar los detalles de compra
    public List<String[]> getDetalleCompra(int idCompra) {
        List<String[]> detalles = new ArrayList<>();
        String query = """
            SELECT c.titulo, dv.precio 
            FROM Detalle_Venta dv 
            JOIN Cancion c ON dv.idCancion = c.idCancion 
            WHERE dv.idVenta = ?
        """;
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCompra);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                detalles.add(new String[]{
                        rs.getString("titulo"),
                        String.valueOf(rs.getDouble("precio"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalles;
    }
/*
    public boolean generateHistorialPDF() {
        // Implementación usando PDFBox para generar el historial de compras en PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            contentStream.showText("Historial de Compras");
            contentStream.newLine();

            List<String[]> historial = getHistorialCompras();
            for (String[] compra : historial) {
                contentStream.showText("Fecha: " + compra[0] + ", Total: $" + compra[1] + ", Tipo: " + compra[2]);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save("Historial_Compras.pdf");
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    } */

    //Datos de usuario
    public String[] getUserDataById(int userId) {
        String[] userData = new String[3];
        String query = "SELECT nombre, telUser, emailUser FROM Usuarios WHERE idUser = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userData[0] = rs.getString("nombre");
                userData[1] = rs.getString("telUser");
                userData[2] = rs.getString("emailUser");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }

    public boolean updateUserData(int userId, String telUser, String emailUser) {
        String query = "UPDATE Usuarios SET telUser = ?, emailUser = ? WHERE idUser = ?";
        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, telUser);
            stmt.setString(2, emailUser);
            stmt.setInt(3, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
