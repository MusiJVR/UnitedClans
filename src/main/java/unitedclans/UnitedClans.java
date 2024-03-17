package unitedclans;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.command.*;
import unitedclans.handler.*;
import unitedclans.langs.DefaultConfig;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;


public final class UnitedClans extends JavaPlugin implements Listener {
    private static UnitedClans instance;
    private SqliteDriver sql;
    @Override
    public void onEnable() {
        instance = this;
        DefaultConfig.createDefaultConfigFile(this);
        LocalizationUtils.loadLang(this);

        try {
            sql = new SqliteDriver(getDataFolder() + "/ucdatabase.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        getServer().getLogger().info("[UnitedClans] UnitedClans is enabled");
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(this, sql), this);
        getServer().getPluginManager().registerEvents(new ClanMenuInventoryHandler(this, sql), this);
        getServer().getPluginCommand("createclan").setExecutor(new CreateClanCommand(this, sql));
        getServer().getPluginCommand("createclan").setTabCompleter(new CreateClanTabCompleter());
        getServer().getPluginCommand("deleteclan").setExecutor(new DeleteClanCommand(this, sql));
        getServer().getPluginCommand("deleteclan").setTabCompleter(new DeleteClanTabCompleter());
        getServer().getPluginCommand("inviteclan").setExecutor(new InviteClanCommand(this, sql));
        getServer().getPluginCommand("inviteclan").setTabCompleter(new InviteClanTabCompleter());
        getServer().getPluginCommand("acceptclan").setExecutor(new AcceptClanCommand(this, sql));
        getServer().getPluginCommand("acceptclan").setTabCompleter(new AcceptClanTabCompleter());
        getServer().getPluginCommand("kickclan").setExecutor(new KickClanCommand(this, sql));
        getServer().getPluginCommand("kickclan").setTabCompleter(new KickClanTabCompleter(sql));
        getServer().getPluginCommand("leaveclan").setExecutor(new LeaveClanCommand(this, sql));
        getServer().getPluginCommand("leaveclan").setTabCompleter(new LeaveClanTabCompleter());
        getServer().getPluginCommand("setroleclan").setExecutor(new SetRoleClanCommand(this, sql));
        getServer().getPluginCommand("setroleclan").setTabCompleter(new SetRoleClanTabCompleter(sql));
        getServer().getPluginCommand("menuclan").setExecutor(new MenuClanCommand(sql));
        getServer().getPluginCommand("menuclan").setTabCompleter(new MenuClanTabCompleter());
        getServer().getPluginCommand("chatclan").setExecutor(new ChatClanCommand(this, sql));
        getServer().getPluginCommand("chatclan").setTabCompleter(new ChatClanTabCompleter());
        getServer().getPluginCommand("changeleaderclan").setExecutor(new ChangeLeaderClanCommand(this, sql));
        getServer().getPluginCommand("changeleaderclan").setTabCompleter(new ChangeLeaderClanTabCompleter(sql));
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("[UnitedClans] UnitedClans is disabled");
        try {
            sql.connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static UnitedClans getInstance() {return instance;}
}