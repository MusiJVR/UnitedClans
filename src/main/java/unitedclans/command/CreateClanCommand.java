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
import unitedclans.utils.ShowClanUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class CreateClanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public CreateClanCommand(JavaPlugin plugin, SqliteDriver sql) {
        this.plugin = plugin;
        this.sql = sql;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;
        UUID uuid = playerSender.getUniqueId();
        try {
            if (args.length != 2) {
                return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
            }

            String clanNameInput = args[0];
            String clanColorInput = args[1];

            if (clanNameInput == null) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_CLAN_NAME", true);
            }

            if (clanNameInput.length() < 3 || clanNameInput.length() > 12) {
                return GeneralUtils.checkUtil(playerSender, language, "LENGTH_CLAN_NAME", true);
            }

            boolean valExist = false;
            String[] colorCollection = new String[] {"AQUA", "BLACK", "BLUE", "DARK_AQUA", "DARK_BLUE", "DARK_GRAY", "DARK_GREEN", "DARK_PURPLE" ,"DARK_RED", "GOLD", "GRAY", "GREEN", "LIGHT_PURPLE", "RED", "WHITE", "YELLOW"};
            for (Object valColor:colorCollection) {
                if (Objects.equals(clanColorInput, valColor.toString())) {
                    valExist = true;
                }
            }
            if (!valExist) {
                return GeneralUtils.checkUtil(playerSender, language, "WRONG_COLOR_NAME", true);
            }

            Integer NumberClans = 1;
            List<Map<String, Object>> rsNumberClans = sql.sqlSelectData("ClanID", "CLANS", "ClanID = (SELECT MAX(ClanID) FROM CLANS)");
            if (!rsNumberClans.isEmpty()) {
                NumberClans = (Integer) rsNumberClans.get(0).get("ClanID") + 1;
            }

            List<Map<String, Object>> rsCheckerPlayer = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + uuid + "'");
            Integer ClanID = (Integer) rsCheckerPlayer.get(0).get("ClanID");
            if (ClanID != 0) {
                return GeneralUtils.checkUtil(playerSender, language, "YOU_MEMBER_CLAN", true);
            }

            List<Map<String, Object>> rsCheckerClanName = sql.sqlSelectData("ClanName", "CLANS", "ClanName = '" + clanNameInput + "'");
            if (!rsCheckerClanName.isEmpty()) {
                return GeneralUtils.checkUtil(playerSender, language, "CLAN_NAME_TAKEN", true);
            }

            Integer price = 32;
            price = GeneralUtils.setDefaultValue(price, "clan-creation-price", 0, 64);

            if (!playerSender.getInventory().contains(Material.valueOf(UnitedClans.getInstance().getConfig().getString("server-currency")), price)) {
                return GeneralUtils.checkUtil(playerSender, language, "NOT_CURRENCY_CREATE_CLAN", true);
            }

            Map<String, Object> insertMapCLANS = new HashMap<>();
            insertMapCLANS.put("ClanID", NumberClans);
            insertMapCLANS.put("ClanName", clanNameInput);
            insertMapCLANS.put("ClanColor", clanColorInput);
            insertMapCLANS.put("CountMembers", 1);
            insertMapCLANS.put("Bank", 0);
            insertMapCLANS.put("Kills", 0);
            sql.sqlInsertData("CLANS", insertMapCLANS);
            Map<String, Object> insertMapLETTERS = new HashMap<>();
            insertMapLETTERS.put("ClanID", NumberClans);
            insertMapLETTERS.put("Letter", "null");
            sql.sqlInsertData("LETTERS", insertMapLETTERS);
            sql.sqlUpdateData("PLAYERS", "ClanID = " + NumberClans + ", ClanRole = '" + UnitedClans.getInstance().getConfig().getString("roles.leader") + "'", "UUID = '" + uuid + "'");

            GeneralUtils.removeItems(playerSender, price);

            String msgCreateClan = LocalizationUtils.langCheck(language, "SUCCESS_CREATE_CLAN");
            sender.sendMessage(msgCreateClan.replace("%clan%", clanNameInput));
            playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            ShowClanUtils.showClan(plugin, sql);

            plugin.getServer().getLogger().info("[UnitedClans] " + playerSender.getName() + " created the " + clanNameInput + " clan");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return true;
    }
}