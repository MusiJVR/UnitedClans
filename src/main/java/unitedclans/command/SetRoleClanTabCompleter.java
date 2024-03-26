package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import unitedclans.UnitedClans;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class SetRoleClanTabCompleter implements TabCompleter {
    private SqliteDriver sql;
    public SetRoleClanTabCompleter(SqliteDriver sql) {
        this.sql = sql;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                Player playerSender = (Player) sender;
                UUID uuid = playerSender.getUniqueId();
                String inputPlayer = args[0].toLowerCase();

                List<Map<String, Object>> rsSender = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
                Integer ClanID = (Integer) rsSender.get(0).get("ClanID");

                if (ClanID == 0) {
                    return new ArrayList<>();
                }

                List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("PlayerName", "PLAYERS", "ClanID IS " + ClanID);
                ArrayList<String> onlinePlayers = new ArrayList<>();
                for (Map<String, Object> i : rsPlayerClan) {
                    String playerName = (String) i.get("PlayerName");
                    onlinePlayers.add(playerName);
                }
                List<String> onlinePlayerName = null;
                for (String onlinePlayer : onlinePlayers) {
                    if (onlinePlayer.toString().toLowerCase().startsWith(inputPlayer)) {
                        if (onlinePlayerName == null) {
                            onlinePlayerName = new ArrayList<>();
                        }
                        onlinePlayerName.add(onlinePlayer);
                    }
                }

                if (onlinePlayerName != null) {
                    Collections.sort(onlinePlayerName);
                }

                return onlinePlayerName;
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        } else if (args.length == 2) {
            String inputRole = args[1].toLowerCase();
            ArrayList<String> rolesList = new ArrayList<>();
            rolesList.add(UnitedClans.getInstance().getConfig().getString("roles.elder"));
            rolesList.add(UnitedClans.getInstance().getConfig().getString("roles.member"));
            List<String> roles = null;
            for (String role : rolesList) {
                if (role.toString().toLowerCase().startsWith(inputRole)) {
                    if (roles == null) {
                        roles = new ArrayList<>();
                    }
                    roles.add(role);
                }
            }
            if (roles != null) {
                Collections.sort(roles);
            }
            return roles;
        }
        return new ArrayList<>();
    }
}