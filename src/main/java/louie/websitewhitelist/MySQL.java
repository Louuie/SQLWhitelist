package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class MySQL {
    Connection connection;
    private String username;
    private String password;
    private String ip;
    private String table;
    private String columnName;
    private String url;
    private OfflinePlayer p;

    public String getUsername(){
       return this.username = Config.get().getString("username");
    }
    public String getPassword(){
        return this.password = Config.get().getString("password");
    }
    public String getIp(){
        return this.ip = Config.get().getString("ip");
    }
    public String getTable(){
        return this.table = Config.get().getString("table");

    }
    public String getColumnName(){
        return this.columnName = Config.get().getString("column-name");
    }
    public String getUrl(){
        return this.url = "jdbc:mysql://" + ip;
    }
    public void establishConnection(){
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
        this.getPlayer();
    }
    public OfflinePlayer getPlayer(){

        // Reloads the database
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, () -> {
            String sql = "SELECT * FROM "+ table;
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    //UUID uuid = (UUID) rs.getObject("uuid");
                    String name = rs.getString(columnName);
                    this.p = Bukkit.getOfflinePlayer(name);
                    this.setWhitelist();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }, 1,20 * 60 * Config.get().getInt("refresh-time"));
        return p;
    }
    public void setWhitelist(){
        if(!getPlayer().isWhitelisted()){
            getPlayer().setWhitelisted(true);
            Bukkit.getLogger().info("adding " + getPlayer().getName() + " to the whitelist");
        } else {
            Bukkit.getLogger().info(getPlayer().getName() + " is already whitelisted ignoring!");
        }
    }
}
