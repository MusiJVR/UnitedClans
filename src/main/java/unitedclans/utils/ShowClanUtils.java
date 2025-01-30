package unitedclans.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.*;

public class ShowClanUtils {
    public static void showClan(JavaPlugin plugin, DatabaseDriver dbDriver) {
        List<Map<String, Object>> rsPlayerClan = dbDriver.selectData("clan_id, player_name", "players", null);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        boolean accept = false;

        for (Map<String, Object> i : rsPlayerClan) {
            int getClanID = (int) i.get("clan_id");
            String getPlayerName = (String) i.get("player_name");
            List<Map<String, Object>> rsClan = dbDriver.selectData("clan_name, clan_color", "clans", "WHERE clan_id = ?", getClanID);
            if (rsClan.isEmpty()) {
                continue;
            }
            String clanName = (String) rsClan.get(0).get("clan_name");
            String clanColor = (String) rsClan.get(0).get("clan_color");
            Player getPlayer = plugin.getServer().getPlayer(getPlayerName);

            if (getPlayer == null) {
                continue;
            }

            if (getClanID == 0) {
                getPlayer.setDisplayName(getPlayer.getName());
                accept = true;
                continue;
            }

            Team clan = board.getTeam(clanName);
            if (clan == null) {
                clan = board.registerNewTeam(clanName);
            }
            clan.addPlayer(getPlayer);
            clan.setPrefix(ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName) + " ");
            getPlayer.setDisplayName(ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET) + " " + getPlayer.getName());
            accept = true;
        }
        if (!accept) getPlayer.setDisplayName(getPlayer.getName());//мне нравится такое решения для теста
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            onlinePlayer.setScoreboard(board);
        }
    }
}
