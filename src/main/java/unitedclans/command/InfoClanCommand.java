package unitedclans.command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class InfoClanCommand implements CommandExecutor {
    private SqliteDriver sql;
    public InfoClanCommand(SqliteDriver sql) {
        this.sql = sql;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;
        UUID uuid = playerSender.getUniqueId();
        try {
            List<Map<String, Object>> rsSender = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            Integer senderClanID = (Integer) rsSender.get(0).get("ClanID");

            if (args.length == 0) {
                if (senderClanID == 0) {
                    return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
                }

                List<Map<String, Object>> rsSenderClan = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + senderClanID);
                String senderClanName = (String) rsSenderClan.get(0).get("ClanName");

                return showInfo(playerSender, senderClanName, language);
            }

            if (args.length == 1) {
                String clanNameInput = args[0];

                if (!playerSender.isOp()) {
                    return GeneralUtils.checkUtil(playerSender, language, "NO_RIGHTS_USE_COMMAND", true);
                }

                return showInfo(playerSender, clanNameInput, language);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }

    public boolean showInfo(Player player, String inputClanName, String language) {
        try {
            List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanID, ClanName, ClanColor, CountMembers, Bank, Kills", "CLANS", "ClanName = '" + inputClanName + "'");
            if (rsClan.isEmpty()) {
                return GeneralUtils.checkUtil(player, language, "CLAN_NOT_EXIST", true);
            }
            Integer clanID = (Integer) rsClan.get(0).get("ClanID");
            String clanName = (String) rsClan.get(0).get("ClanName");
            String clanColor = (String) rsClan.get(0).get("ClanColor");
            Integer countMembers = (Integer) rsClan.get(0).get("CountMembers");
            Integer bank = (Integer) rsClan.get(0).get("Bank");
            Integer kills = (Integer) rsClan.get(0).get("Kills");

            List<Map<String, Object>> rsLeader = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + clanID + " AND ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.leader") + "'");
            String leaderName = (String) rsLeader.get(0).get("PlayerName");

            String msgInfo = LocalizationUtils.langCheck(language, "INFO_PATTERN");

            player.sendMessage(msgInfo.replace("%clan%", ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET)).replace("%leader%", leaderName).replace("%members%", countMembers.toString()).replace("%kills%", kills.toString()).replace("%bank%", bank.toString()));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}