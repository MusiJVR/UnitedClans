package unitedclans.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.langs.Langs.*;

import java.io.File;
import java.io.IOException;


public class LocalizationUtils {
    public static String langCheck(String cfgLang, String msgTitle) {
        String msg = null;
        if (cfgLang.equals("en_en")) {
            msg = LangEN.valueOf(msgTitle).toString();
        } else if (cfgLang.equals("ru_ru")) {
            msg = LangRU.valueOf(msgTitle).toString();
        } else {
            msg = LangEN.valueOf(msgTitle).toString();
        }
        return msg;
    }

    public static void loadLang(JavaPlugin plugin) {
        LocalizationUtils.createLangFile(plugin, "en_en.yml");
        LocalizationUtils.createLangFile(plugin, "ru_ru.yml");
    }

    static void createLangFile(JavaPlugin plugin, String langName) {
        File lang = new File(plugin.getDataFolder() + "/lang/", langName);
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(lang);
        if (!lang.exists()) {
            try {
                langConfig.save(lang);
            } catch (IOException e) {
                e.printStackTrace();
                plugin.getServer().getLogger().info("[UnitedClans] Could not create language file");
                plugin.getServer().getLogger().info("[UnitedClans] Disabling plugin");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
        if (langName.equals("en_en.yml")) {
            for (LangEN item : LangEN.values()) {
                if (langConfig.getString(item.getPath()) == null) {
                    langConfig.set(item.getPath(), item.getMsg());
                }
            }
            LangEN.setFile(langConfig);
        } else if (langName.equals("ru_ru.yml")) {
            for (LangRU item : LangRU.values()) {
                if (langConfig.getString(item.getPath()) == null) {
                    langConfig.set(item.getPath(), item.getMsg());
                }
            }
            LangRU.setFile(langConfig);
        }
        try {
            langConfig.save(lang);
        } catch (IOException e) {
            plugin.getServer().getLogger().info("[UnitedClans] Could not save language file");
            plugin.getServer().getLogger().info("[UnitedClans] Disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}