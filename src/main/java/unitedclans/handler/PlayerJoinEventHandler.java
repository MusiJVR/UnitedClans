package unitedclans.handler;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.ShowClanUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class PlayerJoinEventHandler implements Listener {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public PlayerJoinEventHandler(JavaPlugin plugin, SqliteDriver sql) {
        this.plugin = plugin;
        this.sql = sql;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        UUID uuid = event.getPlayer().getUniqueId();
        try {
            List<Map<String, Object>> rsPlayer = sql.sqlSelectData("UUID", "PLAYERS", "UUID = '" + uuid + "'");
            if (rsPlayer.isEmpty()) {
                Map<String, Object> insertMap = new HashMap<>();
                insertMap.put("UUID", uuid);
                insertMap.put("PlayerName", event.getPlayer().getName());
                insertMap.put("ClanID", 0);
                insertMap.put("ClanRole", UnitedClans.getInstance().getConfig().getString("roles.no-clan"));
                insertMap.put("Kills", 0);
                insertMap.put("Donations", 0);
                insertMap.put("LetterRead", 0);
                sql.sqlInsertData("PLAYERS", insertMap);
            }

            List<Map<String, Object>> rsLetter = sql.sqlSelectData("LetterRead", "PLAYERS", "UUID = '" + uuid + "'");
            Integer valueLetterRead = (Integer) rsLetter.get(0).get("LetterRead");
            if (valueLetterRead == 1) {
                event.getPlayer().sendMessage(LocalizationUtils.langCheck(language, "UNREAD_LETTER_MESSAGE"));
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            ShowClanUtils.showClan(plugin, sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}