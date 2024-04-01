package unitedclans.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.*;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class InviteClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public InviteClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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

            String playerNameInput = args[0];
            Player playerName = plugin.getServer().getPlayer(playerNameInput);

            if (playerName == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_PLAYER_NAME", true);
            }

            if (uuid == playerName.getUniqueId()) {
                return GeneralUtils.checkUtil(playerSender, language, "SEND_INVITATION_YOURSELF", true);
            }

            List<Map<String, Object>> rsSender = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String getRoleUUID = (String) rsSender.get(0).get("ClanRole");
            Integer getClanID = (Integer) rsSender.get(0).get("ClanID");

            if (getClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            List<Map<String, Object>> rsInvitedPlayer = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + playerName.getUniqueId() + "'");
            Integer ClanID = (Integer) rsInvitedPlayer.get(0).get("ClanID");

            if (ClanID != 0) {
                return GeneralUtils.checkUtil(playerSender, language, "PLAYER_MEMBER_CLAN", true);
            }

            if (!Objects.equals(getRoleUUID, UnitedClans.getInstance().getConfig().getString("roles.leader")) && !Objects.equals(getRoleUUID, UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NO_RIGHTS_INVITE", true);
            }

            List<Map<String, Object>> rsInviteChecker = sql.sqlSelectData("UUID", "INVITATIONS", "UUID = '" + playerName.getUniqueId() + "'");
            if (!rsInviteChecker.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "ALREADY_SENT_INVITATION", true);
            }

            List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanName, CountMembers", "CLANS", "ClanID = " + getClanID);
            String clanName = (String) rsClan.get(0).get("ClanName");
            Integer countMembers = (Integer) rsClan.get(0).get("CountMembers");

            Integer max = 25;
            max = GeneralUtils.setDefaultValue(max, "clan-max-player", 1, 100);

            if (countMembers >= max) {
                return GeneralUtils.checkUtil(playerSender, language, "YOUR_CLAN_MAX", true);
            }

            Map<String, Object> insertMap = new HashMap<>();
            insertMap.put("UUID", playerName.getUniqueId());
            insertMap.put("PlayerName", playerName.getName());
            insertMap.put("ClanID", getClanID);
            sql.sqlInsertData("INVITATIONS", insertMap);

            String msgInvitation = LocalizationUtils.langCheck(language, "INVITATION");
            TextComponent msgAccept = new TextComponent(LocalizationUtils.langCheck(language, "ACCEPT"));
            msgAccept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(LocalizationUtils.langCheck(language, "CLICK_INVITE"))));
            msgAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptclan"));
            playerName.sendMessage(msgInvitation.replace("%clan%", clanName).replace("%player%", sender.getName()));
            playerName.sendMessage(msgAccept);
            sender.sendMessage(LocalizationUtils.langCheck(language, "INVITATION_SENT"));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " invited " + playerName.getName() + " to the " + clanName + " clan");

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        sql.sqlDeleteData("INVITATIONS", "UUID = '" + playerName.getUniqueId() + "'");
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            }, 1200);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}