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


public class KickClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public KickClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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

            String playerName = args[0];

            if (playerName == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_PLAYER_NAME", true);
            }

            List<Map<String, Object>> rsKickedPlayer = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "PlayerName = '" + playerName + "'");
            if (rsKickedPlayer.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_PLAYER_NAME", true);
            }
            String KickedPlayerRole = (String) rsKickedPlayer.get(0).get("ClanRole");
            Integer KickedPlayerClanID = (Integer) rsKickedPlayer.get(0).get("ClanID");

            if (Objects.equals(playerSender.getName(), playerName)) {
                return GeneralUtils.checkUtil(playerSender, language, "KICK_YOURSELF", true);
            }

            List<Map<String, Object>> rsSender = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String getRoleUUID = (String) rsSender.get(0).get("ClanRole");
            Integer getClanID = (Integer) rsSender.get(0).get("ClanID");
            if (getClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            if (!KickedPlayerClanID.equals(getClanID)) {
                return GeneralUtils.checkUtil(playerSender, language, "PLAYER_NOT_YOUR_CLAN", true);
            }

            if (!Objects.equals(getRoleUUID, UnitedClans.getInstance().getConfig().getString("roles.leader")) && !Objects.equals(getRoleUUID, UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NO_RIGHTS_KICK", true);
            }

            if (Objects.equals(KickedPlayerRole, getRoleUUID) || Objects.equals(KickedPlayerRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                return GeneralUtils.checkUtil(playerSender, language, "ROLE_IS_HIGHER", true);
            }

            sql.sqlUpdateData("PLAYERS", "ClanID = " + 0 + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.no-clan") + "', Kills = " + 0 + ", Donations = " + 0 + ", LetterRead = " + 0, "PlayerName = '" + playerName + "'");
            sql.sqlUpdateData("CLANS", "CountMembers = CountMembers - 1", "ClanID = " + getClanID);

            List<Map<String, Object>> rsClanPlayers = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + getClanID);
            String playerkickedmsg = LocalizationUtils.langCheck(language, "PLAYER_WAS_KICKED");
            for (Map<String, Object> i : rsClanPlayers) {
                String playerNameClan = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerNameClan);
                if (playerClan == null) {
                    continue;
                }
                playerClan.sendMessage(playerkickedmsg.replace("%player%", playerName));
            }
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            Player argPlayerName = plugin.getServer().getPlayer(playerName);

            List<Map<String, Object>> rsClanName = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + KickedPlayerClanID);
            String KickedPlayerClanName = (String) rsClanName.get(0).get("ClanName");
            if (argPlayerName != null) {
                String msgYouWasKicked = LocalizationUtils.langCheck(language, "YOU_WAS_KICKED");
                argPlayerName.sendMessage(msgYouWasKicked.replace("%clan%", KickedPlayerClanName));
                argPlayerName.playSound(argPlayerName.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " kicked " + playerName + " from the " + KickedPlayerClanName + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}