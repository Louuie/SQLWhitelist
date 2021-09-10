package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import me.louie.websitewhitelist.ConfigSetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;

public final class WebsiteWhitelist extends JavaPlugin {
    Connection connection;

    private MySQL sql;
    public MySQL getMySQL(){
        return sql;
    }


    @Override
    public void onEnable() {
        Config.setup();
        Config.save();
        // Plugin startup logic
        sql = new MySQL(this);
        sql.establishConnection();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try{
            if(connection!=null && !connection.isClosed()){
                connection.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
