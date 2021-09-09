package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;

public final class WebsiteWhitelist extends JavaPlugin {

    Connection connection;
    @Override
    public void onEnable() {
        Config.setup();
        Config.save();
        // Plugin startup logic
        MySQL whitelist = new MySQL();
        whitelist.establishWhitelist();
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
