package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.MenuClanUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class MenuClanCommand implements CommandExecutor {
    private SqliteDriver sql;
    public MenuClanCommand(SqliteDriver sql) {
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

            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            Integer PlayerClanID = (Integer) rsPlayerClan.get(0).get("ClanID");
            if (PlayerClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            MenuClanUtils.openClanMenu(playerSender);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}