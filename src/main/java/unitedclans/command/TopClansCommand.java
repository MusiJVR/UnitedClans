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


public class TopClansCommand implements CommandExecutor {
    private SqliteDriver sql;
    public TopClansCommand(SqliteDriver sql) {
        this.sql = sql;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;
        try {
            if (args.length != 1) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            String topNameInput = args[0];

            if (topNameInput == null || !topNameInput.equals("kills") && !topNameInput.equals("money")) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_TOP", true);
            }

            if (topNameInput.equals("kills")) {
                List<Map<String, Object>> rsKills = sql.sqlSelectData("ClanName, ClanColor, Kills", "CLANS", "Kills", 10);
                String msgTop = LocalizationUtils.langCheck(language, "TITLE_KILLS_TOP") + "\n";
                if (rsKills.isEmpty()) {
                    msgTop = msgTop + LocalizationUtils.langCheck(language, "TITLE_EMPTY_TOP") + "\n";
                } else {
                    for (int i = 0; i < rsKills.size(); i++) {
                        String clanName = (String) rsKills.get(i).get("ClanName");
                        String clanColor = (String) rsKills.get(i).get("ClanColor");
                        Integer kills = (Integer) rsKills.get(i).get("Kills");
                        msgTop = msgTop + ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET) + " - " + kills + "\uD83D\uDDE1" + "\n";
                    }
                }
                msgTop = msgTop + LocalizationUtils.langCheck(language, "TITLE_END");
                sender.sendMessage(msgTop);
            } else if (topNameInput.equals("money")) {
                List<Map<String, Object>> rsMoney = sql.sqlSelectData("ClanName, ClanColor, Bank", "CLANS", "Bank", 10);
                String msgTop = LocalizationUtils.langCheck(language, "TITLE_MONEY_TOP") + "\n";
                if (rsMoney.isEmpty()) {
                    msgTop = msgTop + LocalizationUtils.langCheck(language, "TITLE_EMPTY_TOP") + "\n";
                } else {
                    for (int i = 0; i < rsMoney.size(); i++) {
                        String clanName = (String) rsMoney.get(i).get("ClanName");
                        String clanColor = (String) rsMoney.get(i).get("ClanColor");
                        Integer bank = (Integer) rsMoney.get(i).get("Bank");
                        msgTop = msgTop + ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET) + " - " + bank + "$" + "\n";
                    }
                }
                msgTop = msgTop + LocalizationUtils.langCheck(language, "TITLE_END");
                sender.sendMessage(msgTop);
            }
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}