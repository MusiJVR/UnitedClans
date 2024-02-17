package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;

import java.sql.*;
import java.util.*;

public class AcceptClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private Connection con;
    public AcceptClanCommand(JavaPlugin plugin, Connection con) {
        this.plugin = plugin;
        this.con = con;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            return false;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        try {
            Statement stmt = con.createStatement();
            ResultSet rsInvitation = stmt.executeQuery("SELECT * FROM INVITATIONS WHERE UUID IS '" + uuid + "';");
            Integer ClanID = rsInvitation.getInt("ClanID");
            if (!rsInvitation.next()) {
                sender.sendMessage(UnitedClans.getInstance().getConfig().getString("messages.younotinvited"));
                return true;
            }

            ResultSet rsClanPlayers = stmt.executeQuery("SELECT * FROM PLAYERS WHERE ClanID IS " + ClanID + ";");
            String playerjoinedmsg = UnitedClans.getInstance().getConfig().getString("messages.playerjoined");
            while (rsClanPlayers.next()) {
                String playerName = rsClanPlayers.getString("PlayerName");
                Player playerClan = plugin.getServer().getPlayer(playerName);
                playerClan.sendMessage(playerjoinedmsg.replace("%player%",player.getName()));
            }
            ResultSet rsClanName = stmt.executeQuery("SELECT * FROM CLANS WHERE ClanID IS " + ClanID + ";");
            String joinedclanmsg = UnitedClans.getInstance().getConfig().getString("messages.successfullyjoinedclan");
            sender.sendMessage(joinedclanmsg.replace("%clan%",rsClanName.getString("ClanName")));

            String tablePLAYERS = "UPDATE PLAYERS SET ClanID = " + ClanID + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.member") + "' WHERE UUID IS '" + uuid + "';";
            stmt.executeUpdate(tablePLAYERS);
            String tableINVITATIONS = "DELETE FROM INVITATIONS WHERE UUID IS '" + uuid + "'";
            stmt.executeUpdate(tableINVITATIONS);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}