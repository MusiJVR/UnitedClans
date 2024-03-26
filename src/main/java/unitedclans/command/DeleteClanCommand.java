package unitedclans.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.ShowClanUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class DeleteClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public DeleteClanCommand(JavaPlugin plugin, SqliteDriver sql) {
        this.plugin = plugin;
        this.sql = sql;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;
        UUID uuid = playerSender.getUniqueId();
        try {
            if (args.length != 1) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            String clanNameInput = args[0];

            if (clanNameInput == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }

            List<Map<String, Object>> rsgetClan = sql.sqlSelectData("ClanName, ClanID", "CLANS", "ClanName = '" + clanNameInput + "'");
            if (rsgetClan.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }
            String getClanName = (String) rsgetClan.get(0).get("ClanName");
            Integer getClanID = (Integer) rsgetClan.get(0).get("ClanID");

            List<Map<String, Object>> rsgetClanPlayer = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String getLeaderUUID = (String) rsgetClanPlayer.get(0).get("ClanRole");
            Integer getClanIDPlayer = (Integer) rsgetClanPlayer.get(0).get("ClanID");

            if (getClanIDPlayer == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            if (!getClanID.equals(getClanIDPlayer)) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_THIS_CLAN", true);
            }

            if (!Objects.equals(getLeaderUUID, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NOT_LEADER", true);
            }

            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + getClanIDPlayer);
            for (Map<String, Object> i : rsPlayerClan) {
                String playerName = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerName);
                if (playerClan == null || playerClan == playerSender) {
                    continue;
                }

                playerClan.sendMessage(LocalizationUtils.langCheck(language, "LEADER_DELETE_CLAN"));
                playerClan.playSound(playerClan.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            sql.sqlUpdateData("PLAYERS", "ClanID = " + 0 + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.no-clan") + "', Kills = " + 0 + ", Donations = " + 0 + ", LetterRead = " + 0, "ClanID = " + getClanID);
            sql.sqlDeleteData("CLANS", "ClanID = " + getClanID);
            sql.sqlDeleteData("LETTERS", "ClanID = " + getClanID);

            String deleteclanmsg = LocalizationUtils.langCheck(language, "SUCCESS_DELETE_CLAN");
            sender.sendMessage(deleteclanmsg.replace("%clan%", getClanName));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " deleted the " + clanNameInput + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}