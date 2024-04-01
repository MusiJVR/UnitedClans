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


public class LeaveClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public LeaveClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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
            if (args.length != 1) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            String clanNameInput = args[0];

            if (clanNameInput == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }

            List<Map<String, Object>> rsLeavingPlayer = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String LeavingPlayerRole = (String) rsLeavingPlayer.get(0).get("ClanRole");
            Integer LeavingPlayerClanID = (Integer) rsLeavingPlayer.get(0).get("ClanID");

            List<Map<String, Object>> rsgetClan = sql.sqlSelectData("ClanName, ClanID", "CLANS", "ClanName = '" + clanNameInput + "'");
            if (rsgetClan.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }
            String getClanName = (String) rsgetClan.get(0).get("ClanName");
            Integer getClanID = (Integer) rsgetClan.get(0).get("ClanID");

            if (LeavingPlayerClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            if (!getClanID.equals(LeavingPlayerClanID)) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_THIS_CLAN", true);
            }

            if (Objects.equals(LeavingPlayerRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_LEADER", true);
            }

            sql.sqlUpdateData("PLAYERS", "ClanID = " + 0 + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.no-clan") + "', Kills = " + 0 + ", Donations = " + 0 + ", LetterRead = " + 0, "UUID = '" + uuid + "'");
            sql.sqlUpdateData("CLANS", "CountMembers = CountMembers - 1", "ClanID = " + LeavingPlayerClanID);

            List<Map<String, Object>> rsClanPlayers = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + LeavingPlayerClanID);
            String msgPlayerLeave = LocalizationUtils.langCheck(language, "PLAYER_LEAVE");
            for (Map<String, Object> i : rsClanPlayers) {
                String playerNameClan = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerNameClan);
                if (playerClan == null || playerClan == playerSender) {
                    continue;
                }
                playerClan.sendMessage(msgPlayerLeave.replace("%player%", playerSender.getName()));
            }

            List<Map<String, Object>> rsClanName = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + LeavingPlayerClanID);
            String LeavingPlayerClanName = (String) rsClanName.get(0).get("ClanName");
            String msgSuccessfullyLeft = LocalizationUtils.langCheck(language, "SUCCESSFULLY_LEFT");
            playerSender.sendMessage(msgSuccessfullyLeft.replace("%clan%", LeavingPlayerClanName));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " left the " + getClanName + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}