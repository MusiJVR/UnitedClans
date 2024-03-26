package unitedclans.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.*;


public class ShowClanUtils {
    public static void showClan (JavaPlugin plugin, SqliteDriver sql) {
        try {
            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("ClanID, PlayerName", "PLAYERS");
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();

            for (Map<String, Object> i : rsPlayerClan) {
                Integer getClanID = (Integer) i.get("ClanID");
                String getPlayerName = (String) i.get("PlayerName");
                List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanName, ClanColor", "CLANS", "ClanID = " + getClanID);
                if (rsClan.isEmpty()) {
                    continue;
                }
                String clanName = (String) rsClan.get(0).get("ClanName");
                String clanColor = (String) rsClan.get(0).get("ClanColor");
                Player getPlayer = plugin.getServer().getPlayer(getPlayerName);

                if (getPlayer == null) {
                    continue;
                }

                if (getClanID == 0) {
                    getPlayer.setDisplayName(getPlayer.getName());
                    continue;
                }

                Team clan = board.getTeam(clanName);
                if (clan == null) {
                    clan = board.registerNewTeam(clanName);
                }
                clan.addPlayer(getPlayer);
                clan.setPrefix(ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName) + " ");
                getPlayer.setDisplayName(ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET) + " " + getPlayer.getName());
            }
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                onlinePlayer.setScoreboard(board);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}