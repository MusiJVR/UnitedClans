package unitedclans.command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class LetterClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public LetterClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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
            List<Map<String, Object>> rsPlayerSender = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String senderClanRole = (String) rsPlayerSender.get(0).get("ClanRole");
            Integer senderClanID = (Integer) rsPlayerSender.get(0).get("ClanID");
            if (senderClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            if (args.length < 1) {
                List<Map<String, Object>> rsLetter = sql.sqlSelectData("Letter", "LETTERS", "ClanID = " + senderClanID);
                String msgLetter = (String) rsLetter.get(0).get("Letter");

                if (Objects.equals(msgLetter, "null")) {
                    return GeneralUtils.checkUtil(playerSender, language, "NO_LETTER_WRITTEN", true);
                }

                String letterpattern = LocalizationUtils.langCheck(language, "LETTER_MESSAGE");
                sender.sendMessage(letterpattern.replace("%letter%", msgLetter));
                playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                sql.sqlUpdateData("PLAYERS", "LetterRead = " + 0, "UUID = '" + uuid + "'");
                return true;
            }

            if (!Objects.equals(senderClanRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NO_RIGHTS_LETTER", true);
            }

            StringBuilder letter = new StringBuilder();
            for (int i=0; i<args.length; i++) {
                letter.append(" " + args[i]);
            }

            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + senderClanID);
            for (Map<String, Object> i : rsPlayerClan) {
                String playerName = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerName);
                if (playerClan == null || playerClan == playerSender) {
                    continue;
                }

                playerClan.sendMessage(LocalizationUtils.langCheck(language, "UNREAD_LETTER_MESSAGE"));
                playerClan.playSound(playerClan.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            sql.sqlUpdateData("PLAYERS", "LetterRead = " + 1, "ClanID = " + senderClanID);
            sql.sqlUpdateData("PLAYERS", "LetterRead = " + 0, "UUID = '" + uuid + "'");
            sql.sqlUpdateData("LETTERS", "Letter = '" + letter + "'", "ClanID = " + senderClanID);

            sender.sendMessage(LocalizationUtils.langCheck(language, "SUCCESSFULLY_LETTER_MESSAGE"));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + senderClanID);
            String ClanName = (String) rsClan.get(0).get("ClanName");

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " wrote a letter to the " + ClanName + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}