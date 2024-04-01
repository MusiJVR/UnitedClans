package unitedclans.command;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class BankDepositClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public BankDepositClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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

            String depositInput = args[0];

            if (!GeneralUtils.checkDigits(depositInput)) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_BANK", true);
            }

            List<Map<String, Object>> rsPlayerSender = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            Integer senderClanID = (Integer) rsPlayerSender.get(0).get("ClanID");
            if (senderClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            Integer deposit = new Integer(depositInput);
            if (deposit < 1 || deposit > 64) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_BANK", true);
            }

            if (!playerSender.getInventory().contains(Material.valueOf(UnitedClans.getInstance().getConfig().getString("server-currency")), deposit)) {
                return GeneralUtils.checkUtil(playerSender, language, "NOT_CURRENCY", true);
            }

            sql.sqlUpdateData("PLAYERS", "Donations = Donations + " + deposit, "UUID = '" + uuid + "'");
            sql.sqlUpdateData("CLANS", "Bank = Bank + " + deposit, "ClanID = " + senderClanID);

            GeneralUtils.removeItems(playerSender, deposit);

            String msgSuccessfullyDepositBank = LocalizationUtils.langCheck(language, "SUCCESSFULLY_DEPOSIT_BANK");
            sender.sendMessage(msgSuccessfullyDepositBank.replace("%value%", deposit.toString()));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            List<Map<String, Object>> rsClan = sql.sqlSelectData("ClanName", "CLANS", "ClanID = " + senderClanID);
            String senderClanName = (String) rsClan.get(0).get("ClanName");

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " deposited " + deposit + "$ into of the " + senderClanName + " clan bank");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}