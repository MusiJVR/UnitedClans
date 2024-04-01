package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class InfoClanTabCompleter implements TabCompleter {
    private SqliteDriver sql;
    public InfoClanTabCompleter(SqliteDriver sql) {
        this.sql = sql;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                Player playerSender = (Player) sender;
                if (!playerSender.isOp()) {
                    return new ArrayList<>();
                }

                String inputClan = args[0].toLowerCase();
                List<Map<String, Object>> rsClans = sql.sqlSelectData("ClanName", "CLANS");

                ArrayList<String> clans = new ArrayList<>();
                for (Map<String, Object> i : rsClans) {
                    String clanName = (String) i.get("ClanName");
                    clans.add(clanName);
                }

                List<String> clanNames = null;
                for (String clan : clans) {
                    if (clan.toString().toLowerCase().startsWith(inputClan)) {
                        if (clanNames == null) {
                            clanNames = new ArrayList<>();
                        }
                        clanNames.add(clan);
                    }
                }

                if (clanNames != null) {
                    Collections.sort(clanNames);
                }

                return clanNames;
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }
}