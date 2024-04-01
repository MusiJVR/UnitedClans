package unitedclans.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unitedclans.UnitedClans;
import unitedclans.utils.LocalizationUtils;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private SqliteDriver sql;
    public PlaceholderAPIHook(JavaPlugin plugin, SqliteDriver sql) {
        this.plugin = plugin;
        this.sql = sql;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "unitedclans";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");

        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            Player player = offlinePlayer.getPlayer();

            if (params.equals("clanName")) {
                try {
                    List<Map<String, Object>> rsClanID = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer clanID = (Integer) rsClanID.get(0).get("ClanID");

                    if (clanID != 0) {
                        List<Map<String, Object>> rsClanName = sql.sqlSelectData("ClanName, ClanColor", "CLANS", "ClanID = " + clanID);
                        String clanName = (String) rsClanName.get(0).get("ClanName");
                        String clanColor = (String) rsClanName.get(0).get("ClanColor");

                        return ChatColor.valueOf(clanColor) + (ChatColor.BOLD + clanName + ChatColor.RESET) + " ";
                    }

                    return "";
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("clanCountMembers")) {
                try {
                    List<Map<String, Object>> rsClanID = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer clanID = (Integer) rsClanID.get(0).get("ClanID");

                    if (clanID != 0) {
                        List<Map<String, Object>> rsClanCountMembers = sql.sqlSelectData("CountMembers", "CLANS", "ClanID = " + clanID);
                        Integer countMembers = (Integer) rsClanCountMembers.get(0).get("CountMembers");

                        return ChatColor.YELLOW + countMembers.toString() + "☠ " + ChatColor.RESET;
                    }

                    return "";
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("clanKills")) {
                try {
                    List<Map<String, Object>> rsClanID = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer clanID = (Integer) rsClanID.get(0).get("ClanID");

                    if (clanID != 0) {
                        List<Map<String, Object>> rsClanKills = sql.sqlSelectData("Kills", "CLANS", "ClanID = " + clanID);
                        Integer clanKills = (Integer) rsClanKills.get(0).get("Kills");

                        return ChatColor.DARK_RED + clanKills.toString() + "\uD83D\uDDE1 " + ChatColor.RESET;
                    }

                    return "";
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("clanBank")) {
                try {
                    List<Map<String, Object>> rsClanID = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer clanID = (Integer) rsClanID.get(0).get("ClanID");

                    if (clanID != 0) {
                        List<Map<String, Object>> rsClanBank = sql.sqlSelectData("Bank", "CLANS", "ClanID = " + clanID);
                        Integer clanBank = (Integer) rsClanBank.get(0).get("Bank");

                        return ChatColor.DARK_GREEN + clanBank.toString() + "$ " + ChatColor.RESET;
                    }

                    return "";
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("playerRole")) {
                try {
                    List<Map<String, Object>> rsPlayerRole = sql.sqlSelectData("ClanRole", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    String playerRole = (String) rsPlayerRole.get(0).get("ClanRole");

                    if (!playerRole.equals(UnitedClans.getInstance().getConfig().getString("roles.no-clan"))) {
                        String color = "WHITE";
                        String role = "";
                        if (playerRole.equals(UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                            color = "GREEN";
                            role = LocalizationUtils.langCheck(language, "MEMBER");
                        } else if (playerRole.equals(UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                            color = "GOLD";
                            role = LocalizationUtils.langCheck(language, "ELDER");
                        } else if (playerRole.equals(UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                            color = "DARK_PURPLE";
                            role = LocalizationUtils.langCheck(language, "LEADER");
                        }
                        return ChatColor.valueOf(color) + role + ChatColor.RESET;
                    }

                    return "";
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("playerKills")) {
                try {
                    List<Map<String, Object>> rsPlayerKills = sql.sqlSelectData("Kills", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer playerKills = (Integer) rsPlayerKills.get(0).get("Kills");

                    return ChatColor.DARK_RED + playerKills.toString() + "\uD83D\uDDE1 " + ChatColor.RESET;
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            } else if (params.equals("playerDonations")) {
                try {
                    List<Map<String, Object>> rsPlayerDonations = sql.sqlSelectData("Donations", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
                    Integer playerDonations = (Integer) rsPlayerDonations.get(0).get("Donations");

                    return ChatColor.DARK_GREEN + playerDonations.toString() + "$ " + ChatColor.RESET;
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
        }
        return null;
    }
}