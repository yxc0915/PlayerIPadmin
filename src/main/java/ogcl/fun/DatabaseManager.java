package ogcl.fun;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private JavaPlugin plugin;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/players.db");
            try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_locations (uuid TEXT PRIMARY KEY, last_location TEXT)")) {
                stmt.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Failed to initialize database: " + e.getMessage());
        }
    }

    public void updateLoginLocation(String uuid, String location) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO player_locations (uuid, last_location) VALUES (?, ?) ON CONFLICT(uuid) DO UPDATE SET last_location = ?")) {
            stmt.setString(1, uuid);
            stmt.setString(2, location);
            stmt.setString(3, location);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLastLoginLocation(String uuid) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT last_location FROM player_locations WHERE uuid = ?")) {
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("last_location");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
