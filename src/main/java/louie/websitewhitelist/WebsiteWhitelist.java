package louie.websitewhitelist;

import me.louie.websitewhitelist.Config;
import me.louie.websitewhitelist.ConfigSetter;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;

public final class WebsiteWhitelist extends JavaPlugin {
    MySQL whitelist = new MySQL();
    ConfigSetter cs = new ConfigSetter();
    Connection connection;

    @Override
    public void onEnable() {
        Config.setup();
        Config.save();
        // Plugin startup logic
        cs.configSetup();
        whitelist.establishConnection();
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
