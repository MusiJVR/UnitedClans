package unitedclans.command;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class BankWithdrawClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public BankWithdrawClanCommand(JavaPlugin plugin, SqliteDriver sql) {
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

            String withdrawInput = args[0];

            if (!GeneralUtils.checkDigits(withdrawInput)) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_BANK", true);
            }

            List<Map<String, Object>> rsPlayerSender = sql.sqlSelectData("ClanRole, ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            String senderRole = (String) rsPlayerSender.get(0).get("ClanRole");
            Integer senderClanID = (Integer) rsPlayerSender.get(0).get("ClanID");
            if (senderClanID == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_NOT_MEMBER_CLAN", true);
            }

            if (Objects.equals(senderRole, UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                return GeneralUtils.checkUtil(playerSender, language, "NO_RIGHTS_BANK", true);
            }

            Integer withdraw = new Integer(withdrawInput);
            if (withdraw < 1 || withdraw > 64) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_BANK", true);
            }

            List<Map<String, Object>> rsBank = sql.sqlSelectData("ClanName, Bank", "CLANS", "ClanID = " + senderClanID);
            String senderClanName = (String) rsBank.get(0).get("ClanName");
            Integer bankAccount = (Integer) rsBank.get(0).get("Bank");
            if (bankAccount - withdraw < 0) {
                String emptybankmsg = LocalizationUtils.langCheck(language, "EMPTY_BANK");
                sender.sendMessage(emptybankmsg.replace("%value%", bankAccount.toString()));
                playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
                return true;
            }

            int emptySlots = 0;
            for (int slot = 0; slot < 36; slot++) {
                if (playerSender.getInventory().getItem(slot) == null) {
                        emptySlots++;
                }
            }

            if (emptySlots == 0) {
                return GeneralUtils.checkUtil(playerSender, language, "INVENTORY_FULL", true);
            }

            sql.sqlUpdateData("CLANS", "Bank = Bank - " + withdraw, "ClanID = " + senderClanID);

            for (int i = 0; i < withdraw; i++) {
                playerSender.getInventory().addItem(new ItemStack(Material.valueOf(UnitedClans.getInstance().getConfig().getString("server-currency"))));
            }

            String successfullywithdrawbankmsg = LocalizationUtils.langCheck(language, "SUCCESSFULLY_WITHDRAW_BANK");
            sender.sendMessage(successfullywithdrawbankmsg.replace("%value%", withdraw.toString()));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " withdrew " + withdraw + "$ from the " + senderClanName + " clan bank");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}