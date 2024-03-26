package unitedclans.langs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public enum DefaultConfig {
    LANG("lang", "en_en"),
    CLAN_MSG_PATTERN("clan-msg-pattern", "%clan% [%sender%]:%message%"),
    CLAN_MAX_PLAYER("clan-max-player", "25"),
    SERVER_CURRENCY("server-currency", "DIAMOND"),
    CLAN_CREATION_PRICE("clan-creation-price", "32"),
    LEADER("roles.leader", "Leader"),
    ELDER("roles.elder", "Elder"),
    MEMBER("roles.member", "Member"),
    NO_CLAN("roles.no-clan", "NoClan");

    private String path;
    private String value;
    private static YamlConfiguration CONFIG;

    DefaultConfig(String path, String value) {
        this.path = path;
        this.value = value;
    }

    public static void setFile(YamlConfiguration config) {
        CONFIG = config;
    }

    @Override
    public String toString() {
        return CONFIG.getString(path, value);
    }

    public String getValue() {
        return this.value;
    }
    public String getPath() {
        return this.path;
    }

    public static void createDefaultConfigFile(JavaPlugin plugin) {
        File config = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration configFile = YamlConfiguration.loadConfiguration(config);
        if (!config.exists()) {
            try {
                configFile.save(config);
            } catch (IOException e) {
                e.printStackTrace();
                plugin.getServer().getLogger().info("[UnitedClans] Could not create config file");
                plugin.getServer().getLogger().info("[UnitedClans] Disabling plugin");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
        for (DefaultConfig item : DefaultConfig.values()) {
            if (configFile.getString(item.getPath()) == null) {
                configFile.set(item.getPath(), item.getValue());
            }
        }
        DefaultConfig.setFile(configFile);
        try {
            configFile.save(config);
        } catch (IOException e) {
            plugin.getServer().getLogger().info("[UnitedClans] Could not save config file");
            plugin.getServer().getLogger().info("[UnitedClans] Disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}