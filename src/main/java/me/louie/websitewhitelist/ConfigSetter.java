package me.louie.websitewhitelist;

import louie.websitewhitelist.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigSetter {
    private MySQL sql;
    public MySQL getSql(){
        return sql;
    }
    String configName = Config.get().getString("Database Config");

    public void configSetup() {
        if (configName == null) {
            Config.get().set("Database Config" + "." + "Username", "");
            Config.get().set("Database Config" + "." + "Password", "");
            Config.get().set("Database Config" + "." + "IP", "");
            Config.get().set("Database Config" + "." + "Table", "");
            Config.get().set("Database Config" + "." + "Column-Name", "");
            Config.get().set("Database Config" + "." + "Refresh-Time", 0);
            Config.save();
        } else if (!(configName == null)) {
            if (sql.getUsername() == null || sql.getPassword() == null || sql.getIp() == null || sql.getTable() == null || sql.getColumnName() == null || sql.getUrl() == null || sql.getRefreshTime() == 0) {
                Bukkit.getLogger().info("It seems like the config has been created but you are missing fields.");
            }
        }
    }
}
//}
