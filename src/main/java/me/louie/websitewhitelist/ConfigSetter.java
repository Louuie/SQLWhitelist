package me.louie.websitewhitelist;

import louie.websitewhitelist.MySQL;
import org.bukkit.Bukkit;

public class ConfigSetter {
    MySQL sql = new MySQL();
    String configName = Config.get().getString("Database Config");
    public void configSetup(){
        if(configName == null){
            Config.get().set("Database Config" + "." + "Username", null);
            Config.get().set("Database Config" + "." + "Password", null);
            Config.get().set("Database Config" + "." + "IP", null);
            Config.get().set("Database Config" + "." + "Table", null);
            Config.get().set("Database Config" + "." + "Column-Name", null);
            Config.get().set("Database Config" + "." + "URL", null);
        }  else if(!(configName == null)) {
            if(sql.getUsername() == null || sql.getPassword() == null || sql.getIp() == null || sql.getTable() == null || sql.getColumnName() == null || sql.getUrl() == null){
                Bukkit.getLogger().info("It seems like the config has been created but you are missing fields.");
            }
        }
    }
}
