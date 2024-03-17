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
        Inventory clanMenuGUI = Bukkit.createInventory(player, 27, ChatColor.BOLD + LocalizationUtils.langCheck(language, "CLAN_MENU"));

        ItemStack clanBank = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta clanBank_meta = clanBank.getItemMeta();
        clanBank_meta.setDisplayName(ChatColor.RESET + (ChatColor.YELLOW + LocalizationUtils.langCheck(language, "CLAN_BANK")));
        ArrayList<String> clanBank_lore = new ArrayList<>();
        clanBank_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_BANK_DESCRIPTION")));
        clanBank_meta.setLore(clanBank_lore);
        clanBank.setItemMeta(clanBank_meta);
        clanMenuGUI.setItem(10, clanBank);

        ItemStack players = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta players_meta = players.getItemMeta();
        players_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CLAN_MEMBERS")));
        ArrayList<String> players_lore = new ArrayList<>();
        players_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CLAN_MEMBERS_DESCRIPTION")));
        players_meta.setLore(players_lore);
        players.setItemMeta(players_meta);
        clanMenuGUI.setItem(13, players);

        ItemStack topClans = new ItemStack(Material.BEACON, 1);
        ItemMeta topClans_meta = topClans.getItemMeta();
        topClans_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "TOP_CLANS")));
        ArrayList<String> topClans_lore = new ArrayList<>();
        topClans_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "TOP_CLANS_DESCRIPTION")));
        topClans_meta.setLore(topClans_lore);
        topClans.setItemMeta(topClans_meta);
        clanMenuGUI.setItem(16, topClans);

        player.openInventory(clanMenuGUI);
    }

    public static void openMembersMenu(Player player, SqliteDriver sql) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory membersMenuGUI = Bukkit.createInventory(player, 36, ChatColor.BOLD + LocalizationUtils.langCheck(language, "MEMBERS_MENU"));
        try {
            List<Map<String, Object>> rsPlayerClan = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + player.getUniqueId() + "'");
            Integer PlayerClanID = (Integer) rsPlayerClan.get(0).get("ClanID");

            List<Map<String, Object>> rsPlayers = sql.sqlSelectData("PlayerName, ClanRole", "PLAYERS", "ClanID = " + PlayerClanID);

            ArrayList<String> PlayerList = new ArrayList<>();
            ArrayList<String> RoleList = new ArrayList<>();
            for (Map<String, Object> i : rsPlayers) {
                String getPlayersName = (String) i.get("PlayerName");
                PlayerList.add(getPlayersName);

                String getPlayersRole = (String) i.get("ClanRole");
                String setRole = null;
                if (Objects.equals(getPlayersRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                    setRole = LocalizationUtils.langCheck(language, "LEADER");
                } else if (Objects.equals(getPlayersRole, UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                    setRole = LocalizationUtils.langCheck(language, "ELDER");
                } else if (Objects.equals(getPlayersRole, UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                    setRole = LocalizationUtils.langCheck(language, "MEMBER");
                }
                RoleList.add(setRole);
            }

            for (int i = 0; i < PlayerList.size(); i++) {
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                meta.setDisplayName(ChatColor.RESET + (ChatColor.WHITE + PlayerList.get(i)));
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.ITALIC + (ChatColor.DARK_PURPLE + RoleList.get(i)));
                meta.setLore(lore);
                meta.setOwner(PlayerList.get(i));
                playerHead.setItemMeta(meta);
                membersMenuGUI.addItem(playerHead);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

            ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
            ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
            BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "MEMBERS_BACK_TO_MENU")));
            ArrayList<String> BackToMenu_lore = new ArrayList<>();
            BackToMenu_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "MEMBERS_BACK_TO_MENU_DESCRIPTION")));
            BackToMenu_meta.setLore(BackToMenu_lore);
            BackToMenu.setItemMeta(BackToMenu_meta);
            for (int n = 27; n <= 35; n++) {
                membersMenuGUI.setItem(n, BackToMenu);
            }

            player.openInventory(membersMenuGUI);
    }

    public static void openMemberMenu(Player player, SqliteDriver sql, String selectedPlayerName) {
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Inventory memberMenuGUI = Bukkit.createInventory(player, 9, ChatColor.BOLD + LocalizationUtils.langCheck(language, "MEMBER_MENU"));

        ItemStack playerSelected = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta playerSelected_meta = (SkullMeta) playerSelected.getItemMeta();
        playerSelected_meta.setDisplayName(ChatColor.RESET + (ChatColor.WHITE + selectedPlayerName));
        ArrayList<String> playerSelected_lore = new ArrayList<>();
        try {
            List<Map<String, Object>> rsplayerSelectedRole = sql.sqlSelectData("ClanRole", "PLAYERS", "PlayerName = '" + selectedPlayerName + "'");
            String playerSelectedRole = (String) rsplayerSelectedRole.get(0).get("ClanRole");

            String setPlayerRole = null;
            if (Objects.equals(playerSelectedRole, UnitedClans.getInstance().getConfig().getString("roles.leader"))) {
                setPlayerRole = LocalizationUtils.langCheck(language, "LEADER");
            } else if (Objects.equals(playerSelectedRole, UnitedClans.getInstance().getConfig().getString("roles.elder"))) {
                setPlayerRole = LocalizationUtils.langCheck(language, "ELDER");
            } else if (Objects.equals(playerSelectedRole, UnitedClans.getInstance().getConfig().getString("roles.member"))) {
                setPlayerRole = LocalizationUtils.langCheck(language, "MEMBER");
            }
            playerSelected_lore.add(ChatColor.ITALIC + (ChatColor.DARK_PURPLE + setPlayerRole));
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
        changeRole_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CHANGE_ROLE_DESCRIPTION")));
        changeRole_meta.setLore(changeRole_lore);
        changeRole.setItemMeta(changeRole_meta);
        memberMenuGUI.setItem(3, changeRole);

        ItemStack kickPlayer = new ItemStack(Material.BARRIER, 1);
        ItemMeta kickPlayer_meta = kickPlayer.getItemMeta();
        kickPlayer_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "KICK_MEMBER")));
        ArrayList<String> kickPlayer_lore = new ArrayList<>();
        kickPlayer_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "KICK_MEMBER_DESCRIPTION")));
        kickPlayer_meta.setLore(kickPlayer_lore);
        kickPlayer.setItemMeta(kickPlayer_meta);
        memberMenuGUI.setItem(5, kickPlayer);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "MEMBER_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "MEMBER_BACK_TO_MENU_DESCRIPTION")));
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
        member_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_MEMBER_DESCRIPTION")));
        member_meta.setLore(member_lore);
        member.setItemMeta(member_meta);
        changeRoleMenuGUI.setItem(1, member);

        ItemStack elder = new ItemStack(Material.STICK, 1);
        ItemMeta elder_meta = elder.getItemMeta();
        elder_meta.setDisplayName(ChatColor.RESET + (ChatColor.GOLD + LocalizationUtils.langCheck(language, "SET_ELDER")));
        ArrayList<String> elder_lore = new ArrayList<>();
        elder_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_ELDER_DESCRIPTION")));
        elder_meta.setLore(elder_lore);
        elder.setItemMeta(elder_meta);
        changeRoleMenuGUI.setItem(3, elder);

        ItemStack leader = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta leader_meta = leader.getItemMeta();
        leader_meta.setDisplayName(ChatColor.RESET + (ChatColor.DARK_PURPLE + LocalizationUtils.langCheck(language, "SET_LEADER")));
        ArrayList<String> leader_lore = new ArrayList<>();
        leader_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "SET_LEADER_DESCRIPTION")));
        leader_meta.setLore(leader_lore);
        leader.setItemMeta(leader_meta);
        changeRoleMenuGUI.setItem(5, leader);

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CHANGE_ROLE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CHANGE_ROLE_BACK_TO_MENU_DESCRIPTION")));
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
        confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.GREEN + LocalizationUtils.langCheck(language, "CONFIRM")));
        ArrayList<String> confirm_lore = new ArrayList<>();
        confirm_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_DESCRIPTION")));
        confirm_meta.setLore(confirm_lore);
        confirm.setItemMeta(confirm_meta);
        for (int i = 0; i < 4; i++) {
            confirmRoleMenuGUI.setItem(i, confirm);
        }

        ItemStack not_confirm = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta not_confirm_meta = not_confirm.getItemMeta();
        not_confirm_meta.setDisplayName(ChatColor.RESET + (ChatColor.RED + LocalizationUtils.langCheck(language, "NOT_CONFIRM")));
        ArrayList<String> not_confirm_lore = new ArrayList<>();
        not_confirm_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "NOT_CONFIRM_DESCRIPTION")));
        not_confirm_meta.setLore(not_confirm_lore);
        not_confirm.setItemMeta(not_confirm_meta);
        for (int i = 5; i < 9; i++) {
            confirmRoleMenuGUI.setItem(i, not_confirm);
        }

        ItemStack BackToMenu = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta BackToMenu_meta = BackToMenu.getItemMeta();
        BackToMenu_meta.setDisplayName(ChatColor.RESET + (ChatColor.AQUA + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_BACK_TO_MENU")));
        ArrayList<String> BackToMenu_lore = new ArrayList<>();
        BackToMenu_lore.add(ChatColor.ITALIC + (ChatColor.GRAY + LocalizationUtils.langCheck(language, "CONFIRM_ROLE_BACK_TO_MENU_DESCRIPTION")));
        BackToMenu_meta.setLore(BackToMenu_lore);
        BackToMenu.setItemMeta(BackToMenu_meta);
        confirmRoleMenuGUI.setItem(4, BackToMenu);

        player.openInventory(confirmRoleMenuGUI);
    }
}