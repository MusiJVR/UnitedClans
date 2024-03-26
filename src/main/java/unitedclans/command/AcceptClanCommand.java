package unitedclans.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.ShowClanUtils;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class AcceptClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public AcceptClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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
            if (args.length != 0) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            List<Map<String, Object>> rsInvitation = sql.sqlSelectData("ClanID", "INVITATIONS", "UUID = '" + uuid + "'");
            if (rsInvitation.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_INVITED", true);
            }
            Integer ClanID = (Integer) rsInvitation.get(0).get("ClanID");

            List<Map<String, Object>> rsClan = sql.sqlSelectData("CountMembers, ClanName", "CLANS", "ClanID = " + ClanID);
            Integer countMembers = (Integer) rsClan.get(0).get("CountMembers");
            if (countMembers >= 25) {
                return GeneralUtils.checkUtil(playerSender, language, "THIS_CLAN_MAX", true);
            }
            String clanName = (String) rsClan.get(0).get("ClanName");

            List<Map<String, Object>> rsClanPlayers = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID = " + ClanID);
            String playerjoinedmsg = LocalizationUtils.langCheck(language, "PLAYER_JOINED");
            for (Map<String, Object> i : rsClanPlayers) {
                String playerName = (String) i.get("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerName);
                if (playerClan == null) {
                    continue;
                }
                playerClan.sendMessage(playerjoinedmsg.replace("%player%", playerSender.getName()));
            }

            String joinedclanmsg = LocalizationUtils.langCheck(language, "SUCCESSFULLY_JOINED_CLAN");
            sender.sendMessage(joinedclanmsg.replace("%clan%", clanName));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            sql.sqlUpdateData("PLAYERS", "ClanID = " + ClanID + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.member") + "', LetterRead = " + 0, "UUID = '" + uuid + "'");
            sql.sqlUpdateData("CLANS", "CountMembers = CountMembers + 1", "ClanID = " + ClanID);
            sql.sqlDeleteData("INVITATIONS", "UUID = '" + uuid + "'");

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " joined the " + clanName + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}