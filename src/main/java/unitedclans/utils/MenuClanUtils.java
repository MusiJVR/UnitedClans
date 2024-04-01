package unitedclans.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import unitedclans.UnitedClans;

import java.util.*;


public class MenuClanUtils {
    public static void openClanMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory clanMenuGUI = Bukkit.createInventory(player, 36, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CLAN_MENU"));

        ItemStack clanBank = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta clanBank_meta = clanBank.getItemMeta();
        clanBank_meta.setDisplayName(ChatColor.RESET + (ChatColor.YELLOW + LocalizationUtils.langCheck(language, "CLAN_BANK")));
        ArrayList<String> clanBank_lore = new ArrayList<>();
        clanBank_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_BANK_DESCRIPTION"));
        clanBank_meta.setLore(clanBank_lore);
        clanBank.setItemMeta(clanBank_meta);
        clanMenuGUI.setItem(10, clanBank);

        ItemStack players = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta players_meta = players.getItemMeta();
        players_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CLAN_MEMBERS")));
        ArrayList<String> players_lore = new ArrayList<>();
        players_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_MEMBERS_DESCRIPTION"));
        players_meta.setLore(players_lore);
        players.setItemMeta(players_meta);
        clanMenuGUI.setItem(13, players);

        ItemStack topClans = new ItemStack(Material.BEACON, 1);
        ItemMeta topClans_meta = topClans.getItemMeta();
        topClans_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "TOP_CLANS")));
        ArrayList<String> topClans_lore = new ArrayList<>();
        topClans_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "TOP_CLANS_DESCRIPTION"));
        topClans_meta.setLore(topClans_lore);
        topClans.setItemMeta(topClans_meta);
        clanMenuGUI.setItem(16, topClans);

        ItemStack clanSettings = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta clanSettings_meta = clanSettings.getItemMeta();
        clanSettings_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "CLAN_SETTINGS")));
        ArrayList<String> clanSettings_lore = new ArrayList<>();
        clanSettings_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_SETTINGS_DESCRIPTION"));
        clanSettings_meta.setLore(clanSettings_lore);
        clanSettings.setItemMeta(clanSettings_meta);
        clanMenuGUI.setItem(20, clanSettings);

        ItemStack clanInfo = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta clanInfo_meta = clanInfo.getItemMeta();
        clanInfo_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "CLAN_INFO")));
        ArrayList<String> clanInfo_lore = new ArrayList<>();
        clanInfo_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_INFO_DESCRIPTION"));
        clanInfo_meta.setLore(clanInfo_lore);
        clanInfo.setItemMeta(clanInfo_meta);
        clanMenuGUI.setItem(24, clanInfo);

        player.openInventory(clanMenuGUI);
    }

    public static int openMembersMenu(Player player, SqliteDriver sql, Integer pageNumber) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory membersMenuGUI = Bukkit.createInventory(player, 36, ChatColor.BOLD + LocalizationUtils.langCheck(language, "MEMBERS_MENU"));
        try {
            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
            Integer PlayerClanID = (Integer) rsPlayerClan.get(0).get("ClanID");

            List<Map<String, Object>> rsPlayers = sql.sqlSelectData("PlayerName, ClanRole, Kills, Donations", "PLAYERS", "ClanID = " + PlayerClanID);
            for (int i = pageNumber * 27; i < rsPlayers.size(); i++) {
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta playerHead_meta = (SkullMeta) playerHead.getItemMeta();
                playerHead_meta.setDisplayName(ChatColor.RESET + (ChatColor.WHITE + (String) rsPlayers.get(i).get("PlayerName")));
                ArrayList<String> playerHead_lore = new ArrayList<>();
                String playerRole = null;
                if (Objects.equals(rsPlayers.get(i).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                    playerRole = ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "LEADER");
                } else if (Objects.equals(rsPlayers.get(i).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                    playerRole = ChatColor.GOLD + LocalizationUtils.langCheck(language, "ELDER");
                } else if (Objects.equals(rsPlayers.get(i).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                    playerRole = ChatColor.GREEN + LocalizationUtils.langCheck(language, "MEMBER");
                }
                playerHead_lore.add(playerRole);
                playerHead_lore.add(ChatColor.DARK_RED + rsPlayers.get(i).get("Kills").toString() + "\uD83D\uDDE1");
                playerHead_lore.add(ChatColor.DARK_GREEN + rsPlayers.get(i).get("Donations").toString() + "$");
                playerHead_meta.setLore(playerHead_lore);
                playerHead_meta.setOwner(rsPlayers.get(i).get("PlayerName").toString());
                playerHead.setItemMeta(playerHead_meta);
                membersMenuGUI.addItem(playerHead);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        ItemStack previous = new ItemStack(Material.ARROW, 1);
        ItemMeta previous_meta = previous.getItemMeta();
        previous_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "PREVIOUS_PAGE")));
        ArrayList<String> previous_lore = new ArrayList<>();
        previous_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "PREVIOUS_PAGE_DESCRIPTION"));
        previous_meta.setLore(previous_lore);
        previous.setItemMeta(previous_meta);
        membersMenuGUI.setItem(28, previous);

        ItemStack next = new ItemStack(Material.ARROW, 1);
        ItemMeta next_meta = next.getItemMeta();
        next_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "NEXT_PAGE")));
        ArrayList<String> next_lore = new ArrayList<>();
        next_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "NEXT_PAGE_DESCRIPTION"));
        next_meta.setLore(next_lore);
        next.setItemMeta(next_meta);
        membersMenuGUI.setItem(34, next);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "MEMBERS_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "MEMBERS_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        membersMenuGUI.setItem(31, BackToMenu);

        player.openInventory(membersMenuGUI);

        return pageNumber;
    }

    public static void openMemberMenu(Player player, SqliteDriver sql, String selectedPlayerName) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory memberMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "MEMBER_MENU"));

        ItemStack playerSelected = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta playerSelected_meta = (SkullMeta) playerSelected.getItemMeta();
        playerSelected_meta.setDisplayName(ChatColor.RESET + (ChatColor.WHITE + selectedPlayerName));
        ArrayList<String> playerSelected_lore = new ArrayList<>();
        try {
            List<Map<String, Object>> rsplayerSelectedPlayer = sql.sqlSelectData("ClanRole, Kills, Donations", "PLAYERS", "PlayerName = '" + selectedPlayerName + "'");
            String setPlayerRole = null;
            if (Objects.equals(rsplayerSelectedPlayer.get(0).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                setPlayerRole = ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "LEADER");
            } else if (Objects.equals(rsplayerSelectedPlayer.get(0).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                setPlayerRole = ChatColor.GOLD + LocalizationUtils.langCheck(language, "ELDER");
            } else if (Objects.equals(rsplayerSelectedPlayer.get(0).get("ClanRole"), UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                setPlayerRole = ChatColor.GREEN + LocalizationUtils.langCheck(language, "MEMBER");
            }
            playerSelected_lore.add(setPlayerRole);
            playerSelected_lore.add(ChatColor.DARK_RED + rsplayerSelectedPlayer.get(0).get("Kills").toString() + "\uD83D\uDDE1");
            playerSelected_lore.add(ChatColor.DARK_GREEN + rsplayerSelectedPlayer.get(0).get("Donations").toString() + "$");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        playerSelected_meta.setLore(playerSelected_lore);
        playerSelected_meta.setOwner(selectedPlayerName);
        playerSelected.setItemMeta(playerSelected_meta);
        memberMenuGUI.setItem(1, playerSelected);

        ItemStack changeRole = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta changeRole_meta = changeRole.getItemMeta();
        changeRole_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "CHANGE_ROLE")));
        ArrayList<String> changeRole_lore = new ArrayList<>();
        changeRole_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CHANGE_ROLE_DESCRIPTION"));
        changeRole_meta.setLore(changeRole_lore);
        changeRole.setItemMeta(changeRole_meta);
        memberMenuGUI.setItem(3, changeRole);

        ItemStack kickPlayer = new ItemStack(Material.BARRIER, 1);
        ItemMeta kickPlayer_meta = kickPlayer.getItemMeta();
        kickPlayer_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "KICK_MEMBER")));
        ArrayList<String> kickPlayer_lore = new ArrayList<>();
        kickPlayer_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "KICK_MEMBER_DESCRIPTION"));
        kickPlayer_meta.setLore(kickPlayer_lore);
        kickPlayer.setItemMeta(kickPlayer_meta);
        memberMenuGUI.setItem(5, kickPlayer);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "MEMBER_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "MEMBER_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        memberMenuGUI.setItem(7, BackToMenu);

        player.openInventory(memberMenuGUI);
    }

    public static void openChangeRoleMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory changeRoleMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CHANGE_ROLE_MENU"));

        ItemStack member = new ItemStack(Material.BAMBOO, 1);
        ItemMeta member_meta = member.getItemMeta();
        member_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "SET_MEMBER")));
        ArrayList<String> member_lore = new ArrayList<>();
        member_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_MEMBER_DESCRIPTION"));
        member_meta.setLore(member_lore);
        member.setItemMeta(member_meta);
        changeRoleMenuGUI.setItem(1, member);

        ItemStack elder = new ItemStack(Material.STICK, 1);
        ItemMeta elder_meta = elder.getItemMeta();
        elder_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "SET_ELDER")));
        ArrayList<String> elder_lore = new ArrayList<>();
        elder_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_ELDER_DESCRIPTION"));
        elder_meta.setLore(elder_lore);
        elder.setItemMeta(elder_meta);
        changeRoleMenuGUI.setItem(3, elder);

        ItemStack leader = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta leader_meta = leader.getItemMeta();
        leader_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "SET_LEADER")));
        ArrayList<String> leader_lore = new ArrayList<>();
        leader_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_LEADER_DESCRIPTION"));
        leader_meta.setLore(leader_lore);
        leader.setItemMeta(leader_meta);
        changeRoleMenuGUI.setItem(5, leader);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CHANGE_ROLE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CHANGE_ROLE_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        changeRoleMenuGUI.setItem(7, BackToMenu);

        player.openInventory(changeRoleMenuGUI);
    }

    public static void openConfirmRoleMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory confirmRoleMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_MENU"));

        ItemStack confirm = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta confirm_meta = confirm.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CONFIRM_ROLE")));
        ArrayList<String> confirm_lore = new ArrayList<>();
        confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_DESCRIPTION"));
        confirm_meta.setLore(confirm_lore);
        confirm.setItemMeta(confirm_meta);
        for (int i = 0; i < 4; i++) {
            confirmRoleMenuGUI.setItem(i, confirm);
        }

        ItemStack not_confirm = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta not_confirm_meta = not_confirm.getItemMeta();
        not_confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "NOT_CONFIRM_ROLE")));
        ArrayList<String> not_confirm_lore = new ArrayList<>();
        not_confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "NOT_CONFIRM_ROLE_DESCRIPTION"));
        not_confirm_meta.setLore(not_confirm_lore);
        not_confirm.setItemMeta(not_confirm_meta);
        for (int i = 5; i < 9; i++) {
            confirmRoleMenuGUI.setItem(i, not_confirm);
        }

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        confirmRoleMenuGUI.setItem(4, BackToMenu);

        player.openInventory(confirmRoleMenuGUI);
    }

    public static void openBankMenu(Player player, SqliteDriver sql) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory clanBankGUI = Bukkit.createInventory(player, 36, ChatColor.BOLD + LocalizationUtils.langCheck(language, "BANK_CLAN_MENU"));

        ItemStack deposit = new ItemStack(Material.BOWL, 1);
        ItemMeta deposit_meta = deposit.getItemMeta();
        deposit_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "DEPOSIT")));
        ArrayList<String> deposit_lore = new ArrayList<>();
        deposit_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "DEPOSIT_DESCRIPTION"));
        deposit_meta.setLore(deposit_lore);
        deposit.setItemMeta(deposit_meta);
        clanBankGUI.setItem(10, deposit);

        ItemStack accountBank = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta accountBank_meta = accountBank.getItemMeta();
        accountBank_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "BANK_ACCOUNT")));
        ArrayList<String> accountBank_lore = new ArrayList<>();
        try {
            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
            Integer PlayerClanID = (Integer) rsPlayerClan.get(0).get("ClanID");

            List<Map<String, Object>> rsBank = sql.sqlSelectData("Bank", "CLANS", "ClanID = " + PlayerClanID);
            Integer bankAmount = (Integer) rsBank.get(0).get("Bank");

            String msgAmount = LocalizationUtils.langCheck(language, "BANK_ACCOUNT_DESCRIPTION");
            accountBank_lore.add(ChatColor.GOLD + msgAmount.replace("%value%", bankAmount.toString()));
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        accountBank_meta.setLore(accountBank_lore);
        accountBank.setItemMeta(accountBank_meta);
        clanBankGUI.setItem(13, accountBank);

        ItemStack withdraw = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta withdraw_meta = withdraw.getItemMeta();
        withdraw_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "WITHDRAW")));
        ArrayList<String> withdraw_lore = new ArrayList<>();
        withdraw_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "WITHDRAW_DESCRIPTION"));
        withdraw_meta.setLore(withdraw_lore);
        withdraw.setItemMeta(withdraw_meta);
        clanBankGUI.setItem(16, withdraw);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "BANK_CLAN_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "BANK_CLAN_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        for (int n = 27; n <= 35; n++) {
            clanBankGUI.setItem(n, BackToMenu);
        }

        player.openInventory(clanBankGUI);
    }

    public static void openDepositMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory clanBankGUI = Bukkit.createInventory(player, 45, ChatColor.BOLD + LocalizationUtils.langCheck(language, "DEPOSIT_MENU"));

        ItemStack deposit8 = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta deposit8_meta = deposit8.getItemMeta();
        deposit8_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "8$"));
        deposit8.setItemMeta(deposit8_meta);
        clanBankGUI.setItem(10, deposit8);

        ItemStack deposit16 = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta deposit16_meta = deposit16.getItemMeta();
        deposit16_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "16$"));
        deposit16.setItemMeta(deposit16_meta);
        clanBankGUI.setItem(13, deposit16);

        ItemStack deposit32 = new ItemStack(Material.EMERALD, 1);
        ItemMeta deposit32_meta = deposit32.getItemMeta();
        deposit32_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "32$"));
        deposit32.setItemMeta(deposit32_meta);
        clanBankGUI.setItem(16, deposit32);

        ItemStack deposit48 = new ItemStack(Material.DIAMOND, 1);
        ItemMeta deposit48_meta = deposit48.getItemMeta();
        deposit48_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "48$"));
        deposit48.setItemMeta(deposit48_meta);
        clanBankGUI.setItem(20, deposit48);

        ItemStack deposit64 = new ItemStack(Material.NETHERITE_INGOT, 1);
        ItemMeta deposit64_meta = deposit64.getItemMeta();
        deposit64_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "64$"));
        deposit64.setItemMeta(deposit64_meta);
        clanBankGUI.setItem(24, deposit64);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "DEPOSIT_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "DEPOSIT_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        for (int n = 36; n <= 44; n++) {
            clanBankGUI.setItem(n, BackToMenu);
        }

        player.openInventory(clanBankGUI);
    }

    public static void openWithdrawMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory clanBankGUI = Bukkit.createInventory(player, 45, ChatColor.BOLD + LocalizationUtils.langCheck(language, "WITHDRAW_MENU"));

        ItemStack deposit8 = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta deposit8_meta = deposit8.getItemMeta();
        deposit8_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "8$"));
        deposit8.setItemMeta(deposit8_meta);
        clanBankGUI.setItem(10, deposit8);

        ItemStack deposit16 = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta deposit16_meta = deposit16.getItemMeta();
        deposit16_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "16$"));
        deposit16.setItemMeta(deposit16_meta);
        clanBankGUI.setItem(13, deposit16);

        ItemStack deposit32 = new ItemStack(Material.EMERALD, 1);
        ItemMeta deposit32_meta = deposit32.getItemMeta();
        deposit32_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "32$"));
        deposit32.setItemMeta(deposit32_meta);
        clanBankGUI.setItem(16, deposit32);

        ItemStack deposit48 = new ItemStack(Material.DIAMOND, 1);
        ItemMeta deposit48_meta = deposit48.getItemMeta();
        deposit48_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "48$"));
        deposit48.setItemMeta(deposit48_meta);
        clanBankGUI.setItem(20, deposit48);

        ItemStack deposit64 = new ItemStack(Material.NETHERITE_INGOT, 1);
        ItemMeta deposit64_meta = deposit64.getItemMeta();
        deposit64_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + "64$"));
        deposit64.setItemMeta(deposit64_meta);
        clanBankGUI.setItem(24, deposit64);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "WITHDRAW_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "WITHDRAW_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        for (int n = 36; n <= 44; n++) {
            clanBankGUI.setItem(n, BackToMenu);
        }

        player.openInventory(clanBankGUI);
    }

    public static void openTopClanMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory topClanGUI = Bukkit.createInventory(player, 27, ChatColor.BOLD + LocalizationUtils.langCheck(language, "TOP_CLAN_MENU"));

        ItemStack kills_top = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta kills_top_meta = kills_top.getItemMeta();
        kills_top_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "KILLS_TOP")));
        ArrayList<String> kills_top_lore = new ArrayList<>();
        kills_top_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "KILLS_TOP_DESCRIPTION"));
        kills_top_meta.setLore(kills_top_lore);
        kills_top.setItemMeta(kills_top_meta);
        topClanGUI.setItem(10, kills_top);

        ItemStack money_top = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta money_top_meta = money_top.getItemMeta();
        money_top_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "MONEY_TOP")));
        ArrayList<String> money_top_lore = new ArrayList<>();
        money_top_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "MONEY_TOP_DESCRIPTION"));
        money_top_meta.setLore(money_top_lore);
        money_top.setItemMeta(money_top_meta);
        topClanGUI.setItem(16, money_top);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "TOP_CLAN_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "TOP_CLAN_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        topClanGUI.setItem(13, BackToMenu);

        player.openInventory(topClanGUI);
    }

    public static void openClanSettingsMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory clanSettingsMenuGUI = Bukkit.createInventory(player, 27, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CLAN_SETTINGS_MENU"));

        ItemStack letter = new ItemStack(Material.MAP, 1);
        ItemMeta letter_meta = letter.getItemMeta();
        letter_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "LETTER_CLAN")));
        ArrayList<String> letter_lore = new ArrayList<>();
        letter_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "LETTER_CLAN_DESCRIPTION"));
        letter_meta.setLore(letter_lore);
        letter.setItemMeta(letter_meta);
        clanSettingsMenuGUI.setItem(10, letter);

        ItemStack leaveClan = new ItemStack(Material.BARRIER, 1);
        ItemMeta leaveClan_meta = leaveClan.getItemMeta();
        leaveClan_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "LEAVE_CLAN")));
        ArrayList<String> leaveClan_lore = new ArrayList<>();
        leaveClan_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "LEAVE_CLAN_DESCRIPTION"));
        leaveClan_meta.setLore(leaveClan_lore);
        leaveClan.setItemMeta(leaveClan_meta);
        clanSettingsMenuGUI.setItem(12, leaveClan);

        ItemStack deleteClan = new ItemStack(Material.TNT, 1);
        ItemMeta deleteClan_meta = deleteClan.getItemMeta();
        deleteClan_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "DELETE_CLAN")));
        ArrayList<String> deleteClan_lore = new ArrayList<>();
        deleteClan_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "DELETE_CLAN_DESCRIPTION"));
        deleteClan_meta.setLore(deleteClan_lore);
        deleteClan.setItemMeta(deleteClan_meta);
        clanSettingsMenuGUI.setItem(14, deleteClan);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CLAN_SETTINGS_CLAN_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_SETTINGS_CLAN_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        clanSettingsMenuGUI.setItem(16, BackToMenu);

        player.openInventory(clanSettingsMenuGUI);
    }

    public static void openConfirmLeaveClanMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory confirmLeaveClanMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CONFIRM_LEAVE_MENU"));

        ItemStack confirm = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta confirm_meta = confirm.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CONFIRM_LEAVE")));
        ArrayList<String> confirm_lore = new ArrayList<>();
        confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_LEAVE_DESCRIPTION"));
        confirm_meta.setLore(confirm_lore);
        confirm.setItemMeta(confirm_meta);
        for (int i = 0; i < 4; i++) {
            confirmLeaveClanMenuGUI.setItem(i, confirm);
        }

        ItemStack not_confirm = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta not_confirm_meta = not_confirm.getItemMeta();
        not_confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "NOT_CONFIRM_LEAVE")));
        ArrayList<String> not_confirm_lore = new ArrayList<>();
        not_confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "NOT_CONFIRM_LEAVE_DESCRIPTION"));
        not_confirm_meta.setLore(not_confirm_lore);
        not_confirm.setItemMeta(not_confirm_meta);
        for (int i = 5; i < 9; i++) {
            confirmLeaveClanMenuGUI.setItem(i, not_confirm);
        }

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CONFIRM_LEAVE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_LEAVE_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        confirmLeaveClanMenuGUI.setItem(4, BackToMenu);

        player.openInventory(confirmLeaveClanMenuGUI);
    }

    public static void openConfirmDeleteClanMenu(Player player) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory confirmDeleteClanMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CONFIRM_DELETE_MENU"));

        ItemStack confirm = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta confirm_meta = confirm.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CONFIRM_DELETE")));
        ArrayList<String> confirm_lore = new ArrayList<>();
        confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_DELETE_DESCRIPTION"));
        confirm_meta.setLore(confirm_lore);
        confirm.setItemMeta(confirm_meta);
        for (int i = 0; i < 4; i++) {
            confirmDeleteClanMenuGUI.setItem(i, confirm);
        }

        ItemStack not_confirm = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta not_confirm_meta = not_confirm.getItemMeta();
        not_confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "NOT_CONFIRM_DELETE")));
        ArrayList<String> not_confirm_lore = new ArrayList<>();
        not_confirm_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "NOT_CONFIRM_DELETE_DESCRIPTION"));
        not_confirm_meta.setLore(not_confirm_lore);
        not_confirm.setItemMeta(not_confirm_meta);
        for (int i = 5; i < 9; i++) {
            confirmDeleteClanMenuGUI.setItem(i, not_confirm);
        }

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CONFIRM_DELETE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_DELETE_BACK_TO_MENU_DESCRIPTION"));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        confirmDeleteClanMenuGUI.setItem(4, BackToMenu);

        player.openInventory(confirmDeleteClanMenuGUI);
    }
}