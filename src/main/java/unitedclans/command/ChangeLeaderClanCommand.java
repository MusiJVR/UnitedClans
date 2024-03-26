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


public class ChangeLeaderClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public ChangeLeaderClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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
            if (args.length != 2) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            String inputClanName = args[0];
            String playerName = args[1];

            List<Map<String, Object>> rsSenderPlayer = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String PlayerRole = (String) rsSenderPlayer.get(0).get("ClanRole");
            Integer PlayerClanID = (Integer) rsSenderPlayer.get(0).get("ClanID");

            if (PlayerClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            List<Map<String, Object>> rsSenderClan = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + PlayerClanID);
            if (inputClanName == null || rsSenderClan.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }
            String ClanName = (String) rsSenderClan.get(0).get("ClanName");

            if (!Objects.equals(inputClanName, ClanName)) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }

            if (playerName == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_PLAYER_NAME", true);
            }

            List<Map<String, Object>> rsLeaderPlayer = sql.sqlSelectData("ClanID", "PLAYERS", "PlayerName IS '" + playerName + "'");
            if (rsLeaderPlayer.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_PLAYER_NAME", true);
            }
            Integer LeaderPlayerClanID = (Integer) rsLeaderPlayer.get(0).get("ClanID");

            if (!Objects.equals(PlayerRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NOT_LEADER", true);
            }

            if (Objects.equals(playerSender.getName(), playerName)) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_ALREADY_LEADER", true);
            }

            if (!PlayerClanID.equals(LeaderPlayerClanID)) {
                return GeneralUtils.checkUtil(playerSender, language, "PLAYER_NOT_YOUR_CLAN", true);
            }

            sql.sqlUpdateData("PLAYERS", "ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.elder") + "'", "UUID = '" + uuid + "'");
            sql.sqlUpdateData("PLAYERS", "ClanRole = '" + PlayerRole + "'", "PlayerName = '" + playerName + "'");

            String successfullychangeleadermsg = LocalizationUtils.langCheck(language, "SUCCESSFULLY_CHANGE_LEADER");
            sender.sendMessage(successfullychangeleadermsg.replace("%player%", playerName));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            List<Map<String, Object>> rsClanPlayers = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + PlayerClanID);
            String changeleadermsg = LocalizationUtils.langCheck(language, "CHANGE_LEADER");
            for (Map<String, Object> i : rsClanPlayers) {
                String playerNameClan = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerNameClan);
                if (playerClan == null || Objects.equals(playerNameClan, playerSender.getName()) || Objects.equals(playerNameClan, playerName)) {
                    continue;
                }

                playerClan.sendMessage(changeleadermsg.replace("%old-leader%", playerSender.getName()).replace("%new-leader%", playerName));
                playerClan.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            Player argPlayerName = plugin.getServer().getPlayer(playerName);
            if (argPlayerName != null) {
                argPlayerName.sendMessage(LocalizationUtils.langCheck(language, "YOU_HAVE_BEEN_LEADER"));
                argPlayerName.playSound(argPlayerName.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " passed on the role of " + UnitedClans.getInstance().getConfig().getString("roles.leader") + " of the " + ClanName + " clan to " + playerName);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}