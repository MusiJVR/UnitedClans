package unitedclans.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class ChatClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public ChatClanCommand(JavaPlugin plugin, SqliteDriver sql) {
        this.plugin = plugin;
        this.sql = sql;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;
        UUID uuid = playerSender.getUniqueId();
        try {
            if (args.length < 1) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            List<Map<String, Object>> rsPlayerSender = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            Integer senderClanID = (Integer) rsPlayerSender.get(0).get("ClanID");
            if (senderClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            StringBuilder message = new StringBuilder();
            for (int i=0; i<args.length; i++) {
                message.append(" " + args[i]);
            }

            String messagepattern = UnitedClans.getInstance().getConfig().getString("clan-msg-pattern");
            List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanName, ClanColor", "CLANS", "ClanID = " + senderClanID);
            String clanName = (String) rsClan.get(0).get("ClanName");
            String clanColor = (String) rsClan.get(0).get("ClanColor");

            List<Map<String, Object>> rsClanPlayers = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + senderClanID);
            for (Map<String, Object> i : rsClanPlayers) {
                String playerNameClan = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerNameClan);
                if (playerClan == null) {
                    continue;
                }
                playerClan.sendMessage(messagepattern.replace("%clan%", ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET)).replace("%sender%", playerSender.getName()).replace("%message%", message));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}