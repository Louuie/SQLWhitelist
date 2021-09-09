package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class MySQL {
    Connection connection;
    final String username = Config.get().getString("username");
    final String password = Config.get().getString("password");
    final String ip = Config.get().getString("url");
    final String table = Config.get().getString("table");
    final String columnName = Config.get().getString("column-name");
    final String url = "jdbc:mysql://" + ip;

    public void establishWhitelist(){
        // First we establish a connection with the SQL Server.
        try{
            java.security.Security.setProperty("jdk.tls.disabledAlgorithms","");
            Class.forName("com.mysql.jdbc.Driver");//Set driver
            connection = DriverManager.getConnection(url, username, password);
            Bukkit.getServer().getLogger().info("Connection Successful to whitelistwebsite.xyz");
        } catch (Exception e){
            Bukkit.getServer().getLogger().info("Connection Failed");
            e.printStackTrace();
        }

        // Reloads the database
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, () -> {
            String sql = "SELECT * FROM "+ table;
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    //UUID uuid = (UUID) rs.getObject("uuid");
                    String name = rs.getString(columnName);
                    OfflinePlayer p = Bukkit.getOfflinePlayer(name);
                    if(!(p.isWhitelisted())){
                        stmt.getConnection();
                        p.setWhitelisted(true);
                        Bukkit.getServer().getLogger().info("adding " + name + " to the whitelist");
                    } else {
                        Bukkit.getServer().getLogger().info(name + " is already whitelisted, ignoring");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }, 1,20 * 60 * Config.get().getInt("refresh-time"));
    }
}
