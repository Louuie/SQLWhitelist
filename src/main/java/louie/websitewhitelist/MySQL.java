package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import me.louie.websitewhitelist.ConfigSetter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.UUID;

public class MySQL{
    private final JavaPlugin plugin;

    Connection connection;
    private String username;
    private String password;
    private String ip;
    private String table;
    private String columnName;
    private String url;
    private int refreshtime;
    private OfflinePlayer p;
    ConfigSetter cs = new ConfigSetter();
    public MySQL(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public String getUsername(){
        return this.username = Config.get().getString("Database Config" + "." + "Username");
    }
    public String getPassword(){
        return this.password = Config.get().getString("Database Config" + "." + "Password");
    }
    public String getIp(){
        return this.ip = Config.get().getString("Database Config" + "." + "IP");
    }
    public String getTable(){
        return this.table = Config.get().getString("Database Config" + "." + "Table");
    }
    public String getColumnName(){
        return this.columnName = Config.get().getString("Database Config" + "." + "Column-Name");
    }
    public String getUrl(){
        return this.url = "jdbc:mysql://" + this.getIp() + "?useSSL=false";
    }
    public int getRefreshTime(){
        return this.refreshtime = Config.get().getInt( "Database Config" + "." + "Refresh-Time");
    }




    public void establishConnection(){
        // First we establish a connection with the SQL Server.
        try{
            java.security.Security.setProperty("jdk.tls.disabledAlgorithms","");
            Class.forName("com.mysql.jdbc.Driver");//Set driver
            connection = DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword());
            Bukkit.getServer().getLogger().info("Connection Successful to database...");
            this.queryDatabase();
        } catch (Exception e){
            Bukkit.getServer().getLogger().info("Connection Failed");
            e.printStackTrace();
        }
    }


    public void queryDatabase() {
        // Reloads the database
        new BukkitRunnable() {

            @Override
            public void run() {
                String sql = "SELECT * FROM " + getTable();
                try {
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        //UUID uuid = (UUID) rs.getObject("uuid");
                        String name = rs.getString(getColumnName());
                        getPlayer(name);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20 * 60 * this.getRefreshTime());
    }

    public void getPlayer(String pName){
        OfflinePlayer p = Bukkit.getOfflinePlayer(pName);
        setWhitelist(p);
    }

    public void setWhitelist(OfflinePlayer p){
        if(!p.isWhitelisted()){
            p.setWhitelisted(true);
            plugin.getLogger().info("adding " + p.getName() + " to the whitelist");
        } else {
            plugin.getLogger().info(p.getName() + " is already whitelisted ignoring!");
        }
    }
}