package unitedclans.langs;

import org.bukkit.configuration.file.YamlConfiguration;


public class Langs {
    public enum LangEN {
        INVALID_COMMAND("messages.invalid-command", "§cThe command was entered incorrectly "),
        WRONG_CLAN_NAME("messages.wrong-clan-name", "§cWrong clan name "),
        LENGTH_CLAN_NAME("messages.length-clan-name", "§cThe clan name can only be in the range from 3 to 12 characters "),
        WRONG_COLOR_NAME("messages.wrong-color-name", "§cWrong color name "),
        WRONG_PLAYER_NAME("messages.wrong-player-name", "§cWrong player name "),
        WRONG_ROLE_NAME("messages.wrong-role-name", "§cWrong role name "),
        CLAN_NAME_TAKEN("messages.clan-name-taken", "§eThis clan name is already taken "),
        YOU_MEMBER_CLAN("messages.you-member-clan", "§eYou are already a member of a clan "),
        YOU_NOT_MEMBER_CLAN("messages.you-not-member-clan", "§eYou are not a member of a clan "),
        YOU_NOT_MEMBER_THIS_CLAN("messages.you-not-member-this-clan", "§eYou are not a member of this clan "),
        SUCCESS_CREATE_CLAN("messages.success-create-clan", "§bYou have successfully created the§r §e§l%clan%§r §bclan "),
        NOT_LEADER("messages.not-leader", "§eYou are not the§r §b§lLeader§r §eof this clan "),
        NO_RIGHTS_INVITE("messages.no-rights-invite", "§eYou do not have enough rights to invite a player "),
        NO_RIGHTS_SET_ROLE("messages.no-rights-set-role", "§eYou do not have enough rights to change the player role "),
        NO_RIGHTS_KICK("messages.no-rights-kick", "§eYou do not have enough rights to kick a player "),
        ALREADY_SENT_INVITATION("messages.already-sent-invitation", "§eAt the moment, someone has already sent an invitation to this player "),
        INVITATION_SENT("messages.invitation-sent", "§bThe invitation has been sent "),
        SUCCESS_DELETE_CLAN("messages.success-delete-clan", "§bThe§r §e§l%clan%§r §bclan was successfully deleted "),
        LEADER_DELETE_CLAN("messages.leader-delete-clan", "§bThe§r §e§lLeader§r §bof your clan has decided to delete the clan "),
        PLAYER_MEMBER_CLAN("messages.player-member-clan", "§eThis player is already in the clan "),
        PLAYER_NOT_MEMBER_CLAN("messages.player-not-member-clan", "§eThe player is not a member of a clan "),
        INVITATION("messages.invitation", "§bYou have received an invitation to the§r §e§l%clan%§r §bclan from the player§r §e§l%player% "),
        CLICK_INVITE("messages.click-invite", "Click to accept the invitation "),
        ACCEPT("messages.accept", "§bAccept invitation§r §e§l[✔] "),
        YOU_NOT_INVITED("messages.you-not-invited", "§eYou have no active invitations "),
        THIS_CLAN_MAX("messages.this-clan-max", "§eThis clan already has the maximum number of players "),
        YOUR_CLAN_MAX("messages.your-clan-max", "§eYour clan already has the maximum number of players "),
        SEND_INVITATION_YOURSELF("messages.send-invitation-yourself", "§eYou cannot send an invitation to yourself "),
        SUCCESSFULLY_JOINED_CLAN("messages.successfully-joined-clan", "§bYou have successfully joined the clan§r §e§l%clan% "),
        PLAYER_JOINED("messages.player-joined", "§bPlayer§r §e§l%player%§r §bjoined the clan "),
        PLAYER_NOT_YOUR_CLAN("messages.player-not-your-clan", "§eThis player is not a member of your clan "),
        ROLE_IS_HIGHER("messages.role-is-higher", "§eYou cannot kick a player because his role is higher or the same as yours "),
        PLAYER_WAS_KICKED("messages.player-was-kicked", "§bPlayer§r §e§l%player%§r §bwas kicked out of the clan "),
        KICK_YOURSELF("messages.kick-yourself", "§eYou cannot kick yourself out "),
        YOU_WAS_KICKED("messages.you-was-kicked", "§bYou were kicked out of the§r §e§l%clan%§r §bclan "),
        YOU_LEADER("messages.you-leader", "§eYou cannot leave a clan because you are the§r §b§lLeader§r §eof that clan "),
        PLAYER_LEAVE("messages.player-leave", "§bPlayer§r §e§l%player%§r §bleft the clan "),
        SUCCESSFULLY_LEFT("messages.successfully-left", "§bYou have successfully left the§r §e§l%clan%§r §bclan "),
        SET_ROLE_YOURSELF("messages.set-role-yourself", "§eYou cannot change your role "),
        YOU_BEEN_ASSIGNED("messages.you-been-assigned", "§bYou have been assigned the role of§r §e§l%role% "),
        SUCCESSFULLY_CHANGED_ROLE("messages.successfully-changed-role", "§bYou have successfully changed the player role to§r §e§l%role% "),
        YOU_ALREADY_LEADER("messages.you-already-leader", "§eYou are already the§r §b§lLeader§r §eof this clan "),
        SUCCESSFULLY_CHANGE_LEADER("messages.successfully-change-leader", "§bYou have successfully transferred the§r §e§lLeader§r §brole to player§r §e§l%player% "),
        CHANGE_LEADER("messages.change-leader", "§e§l%old-leader%§r §bhanded over the role of§r §e§lLeader§r §bto§r §e§l%new-leader%§r "),
        YOU_HAVE_BEEN_LEADER("messages.you-have-been-leader", "§bYou have been given the role of the§r §e§lLeader§r §bof the clan§r "),
        INVALID_BANK("messages.invalid-bank", "§cInvalid value, it must be no more than 64 and contain only numbers "),
        NO_RIGHTS_BANK("messages.no-rights-bank", "§eYou do not have enough rights to empty your clan bank account "),
        EMPTY_BANK("messages.empty-bank", "§eThere are not enough funds in the clan bank account:§r §b§l%value%$§r "),
        NOT_CURRENCY("messages.not-currency", "§eYou don't have that much currency "),
        INVENTORY_FULL("messages.inventory-full", "§eThere is not enough space in your inventory "),
        SUCCESSFULLY_DEPOSIT_BANK("messages.successfully-deposit-bank", "§bYou have successfully topped up your clan bank account by§r §e§l%value%$§r "),
        SUCCESSFULLY_WITHDRAW_BANK("messages.successfully-withdraw-bank", "§bYou have successfully emptied your clan bank account by§r §e§l%value%$§r "),
        NOT_CURRENCY_CREATE_CLAN("messages.not-currency-create-clan", "§eYou don't have enough currency to create a clan "),
        LETTER_MESSAGE("messages.letter-message", "§bLetter:§r§e%letter%§r "),
        UNREAD_LETTER_MESSAGE("messages.unread-letter-message", "§bYou have an unread clan letter "),
        SUCCESSFULLY_LETTER_MESSAGE("messages.successfully-letter-message", "§bYou have successfully written the letter to your clan "),
        NO_RIGHTS_LETTER("messages.no-rights-letter", "§eYou do not have enough rights to write a letter to the clan "),
        NO_LETTER_WRITTEN("messages.no-letter-written", "§eThe clan§r §b§lLeader§r §ehas not yet written letters to the players "),
        NO_RIGHTS_USE_COMMAND("messages.no-rights-use-command", "§eYou do not have sufficient rights to use this command "),
        CLAN_NOT_EXIST("messages.clan-not-exist", "§eThis clan doesn't exist "),
        INFO_PATTERN("messages.info-pattern", "§bClan name:§r %clan% \n§bClan Leader:§r §5%leader%§r \n§bCount members:§r §e%members%☠§r \n§bCount kills:§r §4%kills%\uD83D\uDDE1§r \n§bClan bank:§r §2%bank%$§r "),
        INVALID_TOP("messages.invalid-top", "§cIncorrect top name "),
        TITLE_KILLS_TOP("messages.title-kills-top", "§b§l⏴-------§r§e§lTOP KILLS§r§b§l-------⏵§r "),
        TITLE_MONEY_TOP("messages.title-money-top", "§b§l⏴-------§r§e§lTOP MONEY§r§b§l-------⏵§r "),
        TITLE_END("messages.title-end", "§b§l⏴---------------------⏵§r "),
        TITLE_EMPTY_TOP("messages.title-empty-top", "Top empty "),

        CLAN_MENU("menu-gui.clan-menu.inventory-name", "Clan menu "),
        CLAN_BANK("menu-gui.clan-menu.clan-bank", "Clan bank "),
        CLAN_BANK_DESCRIPTION("menu-gui.clan-menu.clan-bank-description", "Opens the clan bank menu "),
        CLAN_MEMBERS("menu-gui.clan-menu.clan-members", "Clan members "),
        CLAN_MEMBERS_DESCRIPTION("menu-gui.clan-menu.clan-members-description", "Opens list of clan members "),
        TOP_CLANS("menu-gui.clan-menu.top-clans", "Top clans "),
        TOP_CLANS_DESCRIPTION("menu-gui.clan-menu.top-clans-description", "Opens list of the best clans "),
        CLAN_SETTINGS("menu-gui.clan-menu.clan-settings", "Clan settings "),
        CLAN_SETTINGS_DESCRIPTION("menu-gui.clan-menu.clan-settings-description", "Opens the clan settings menu "),
        CLAN_INFO("menu-gui.clan-menu.clan-info", "Clan information "),
        CLAN_INFO_DESCRIPTION("menu-gui.clan-menu.clan-info-description", "Shows information about the clan "),

        MEMBERS_MENU("menu-gui.members-menu.inventory-name", "Members menu "),
        PREVIOUS_PAGE("menu-gui.members-menu.previous-page", "Previous page "),
        PREVIOUS_PAGE_DESCRIPTION("menu-gui.members-menu.previous-page-description", "Opens previous page "),
        NEXT_PAGE("menu-gui.members-menu.next-page", "Next page "),
        NEXT_PAGE_DESCRIPTION("menu-gui.members-menu.next-page-description", "Opens next page "),
        MEMBERS_BACK_TO_MENU("menu-gui.members-menu.members-back-to-menu", "Go back "),
        MEMBERS_BACK_TO_MENU_DESCRIPTION("menu-gui.members-menu.members-back-to-menu-description", "Opens main clan menu "),

        MEMBER_MENU("menu-gui.member-menu.inventory-name", "Member menu "),
        CHANGE_ROLE("menu-gui.member-menu.change-role", "Change role "),
        CHANGE_ROLE_DESCRIPTION("menu-gui.member-menu.change-role-description", "Opens the roles menu "),
        KICK_MEMBER("menu-gui.member-menu.kick-member", "Kick member "),
        KICK_MEMBER_DESCRIPTION("menu-gui.member-menu.kick-member-description", "Kicks member out of the clan "),
        MEMBER_BACK_TO_MENU("menu-gui.member-menu.member-back-to-menu", "Go back "),
        MEMBER_BACK_TO_MENU_DESCRIPTION("menu-gui.member-menu.member-back-to-menu-description", "Opens members menu "),

        CHANGE_ROLE_MENU("menu-gui.change-role-menu.inventory-name", "Role change "),
        SET_MEMBER("menu-gui.change-role-menu.set-member", "Member "),
        SET_MEMBER_DESCRIPTION("menu-gui.change-role-menu.set-member-description", "Set the role Member "),
        SET_ELDER("menu-gui.change-role-menu.set-elder", "Elder "),
        SET_ELDER_DESCRIPTION("menu-gui.change-role-menu.set-elder-description", "Set the role Elder "),
        SET_LEADER("menu-gui.change-role-menu.set-leader", "Leader "),
        SET_LEADER_DESCRIPTION("menu-gui.change-role-menu.set-leader-description", "Set the role Leader "),
        CHANGE_ROLE_BACK_TO_MENU("menu-gui.change-role-menu.change-role-back-to-menu", "Go back "),
        CHANGE_ROLE_BACK_TO_MENU_DESCRIPTION("menu-gui.change-role-menu.change-role-back-to-menu-description", "Opens the member menu "),

        CONFIRM_ROLE_MENU("menu-gui.confirm-role-menu.inventory-name", "Confirm role "),
        CONFIRM_ROLE("menu-gui.confirm-role-menu.confirm-role", "Confirm "),
        CONFIRM_ROLE_DESCRIPTION("menu-gui.confirm-role-menu.confirm-role-description", "Set the role Leader "),
        NOT_CONFIRM_ROLE("menu-gui.confirm-role-menu.not-confirm-role", "Cancel "),
        NOT_CONFIRM_ROLE_DESCRIPTION("menu-gui.confirm-role-menu.not-confirm-role-description", "Cancel the transfer of the role Leader "),
        CONFIRM_ROLE_BACK_TO_MENU("menu-gui.confirm-role-menu.confirm-role-back-to-menu", "Go back "),
        CONFIRM_ROLE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-role-menu.confirm-role-back-to-menu-description", "Opens the roles menu "),

        BANK_CLAN_MENU("menu-gui.bank-clan-menu.inventory-name", "Clan Bank "),
        DEPOSIT("menu-gui.bank-clan-menu.deposit", "Deposit "),
        DEPOSIT_DESCRIPTION("menu-gui.bank-clan-menu.deposit-description", "Opens the deposit menu "),
        BANK_ACCOUNT("menu-gui.bank-clan-menu.bank-account", "Bank account "),
        BANK_ACCOUNT_DESCRIPTION("menu-gui.bank-clan-menu.bank-account-description", "%value%$ "),
        WITHDRAW("menu-gui.bank-clan-menu.withdraw", "Withdraw "),
        WITHDRAW_DESCRIPTION("menu-gui.bank-clan-menu.withdraw-description", "Opens the Withdraw menu "),
        BANK_CLAN_BACK_TO_MENU("menu-gui.bank-clan-menu.bank-clan-back-to-menu", "Go back "),
        BANK_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.bank-clan-menu.bank-clan-back-to-menu-description", "Opens main clan menu "),

        DEPOSIT_MENU("menu-gui.deposit-menu.inventory-name", "Deposit menu "),
        DEPOSIT_BACK_TO_MENU("menu-gui.deposit-menu.deposit-back-to-menu", "Go back "),
        DEPOSIT_BACK_TO_MENU_DESCRIPTION("menu-gui.deposit-menu.deposit-back-to-menu-description", "Opens the clan bank menu "),

        WITHDRAW_MENU("menu-gui.withdraw-menu.inventory-name", "Withdraw menu "),
        WITHDRAW_BACK_TO_MENU("menu-gui.withdraw-menu.withdraw-back-to-menu", "Go back "),
        WITHDRAW_BACK_TO_MENU_DESCRIPTION("menu-gui.withdraw-menu.withdraw-back-to-menu-description", "Opens the clan bank menu "),

        TOP_CLAN_MENU("menu-gui.top-clan-menu.inventory-name", "Top clans "),
        KILLS_TOP("menu-gui.top-clan-menu.kills-top", "Top kills "),
        KILLS_TOP_DESCRIPTION("menu-gui.top-clan-menu.kills-top-description", "Shows top kills "),
        MONEY_TOP("menu-gui.top-clan-menu.money-top", "Top money "),
        MONEY_TOP_DESCRIPTION("menu-gui.top-clan-menu.money-top-description", "Shows top money "),
        TOP_CLAN_BACK_TO_MENU("menu-gui.top-clan-menu.top-clan-back-to-menu", "Go back "),
        TOP_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.top-clan-menu.top-clan-back-to-menu-description", "Opens main clan menu "),

        CLAN_SETTINGS_MENU("menu-gui.clan-settings-menu.inventory-name", "Clan settings "),
        LETTER_CLAN("menu-gui.clan-settings-menu.letter-clan", "Clan letter "),
        LETTER_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.letter-clan-description", "Shows the letter to the clan "),
        LEAVE_CLAN("menu-gui.clan-settings-menu.leave-clan", "Leave the clan "),
        LEAVE_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.leave-clan-description", "Leaves the clan "),
        DELETE_CLAN("menu-gui.clan-settings-menu.delete-clan", "Delete clan "),
        DELETE_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.delete-clan-description", "Deletes a clan "),
        CLAN_SETTINGS_CLAN_BACK_TO_MENU("menu-gui.clan-settings-menu.clan-settings-back-to-menu", "Go back "),
        CLAN_SETTINGS_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.clan-settings-menu.clan-settings-back-to-menu-description", "Opens main clan menu "),

        CONFIRM_LEAVE_MENU("menu-gui.confirm-leave-menu.inventory-name", "Confirm leave "),
        CONFIRM_LEAVE("menu-gui.confirm-leave-menu.confirm-leave", "Confirm "),
        CONFIRM_LEAVE_DESCRIPTION("menu-gui.confirm-leave-menu.confirm-leave-description", "Leaves the clan "),
        NOT_CONFIRM_LEAVE("menu-gui.confirm-leave-menu.not-confirm-leave", "Cancel "),
        NOT_CONFIRM_LEAVE_DESCRIPTION("menu-gui.confirm-leave-menu.not-confirm-leave-description", "Cancels leaving the clan "),
        CONFIRM_LEAVE_BACK_TO_MENU("menu-gui.confirm-leave-menu.confirm-leave-back-to-menu", "Go back "),
        CONFIRM_LEAVE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-leave-menu.confirm-leave-back-to-menu-description", "Opens the clan settings menu "),

        CONFIRM_DELETE_MENU("menu-gui.confirm-delete-menu.inventory-name", "Confirm delete "),
        CONFIRM_DELETE("menu-gui.confirm-delete-menu.confirm-delete", "Confirm "),
        CONFIRM_DELETE_DESCRIPTION("menu-gui.confirm-delete-menu.confirm-delete-description", "Deletes the clan "),
        NOT_CONFIRM_DELETE("menu-gui.confirm-delete-menu.not-confirm-delete", "Cancel "),
        NOT_CONFIRM_DELETE_DESCRIPTION("menu-gui.confirm-delete-menu.not-confirm-delete-description", "Cancels deletion of the clan "),
        CONFIRM_DELETE_BACK_TO_MENU("menu-gui.confirm-delete-menu.confirm-delete-back-to-menu", "Go back "),
        CONFIRM_DELETE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-delete-menu.confirm-delete-back-to-menu-description", "Opens the clan settings menu "),

        LEADER("roles.leader", "Leader "),
        ELDER("roles.elder", "Elder "),
        MEMBER("roles.member", "Member "),
        NO_CLAN("roles.no-clan", "NoClan ");
        private String path;
        private String msg;
        private static YamlConfiguration LANG;

        LangEN(String path, String msg) {
            this.path = path;
            this.msg = msg;
        }

        public static void setFile(YamlConfiguration config) {
            LANG = config;
        }

        @Override
        public String toString() {
            return LANG.getString(path, msg);
        }

        public String getMsg() {
            return this.msg;
        }
        public String getPath() {
            return this.path;
        }
    }

    public enum LangRU {
        INVALID_COMMAND("messages.invalid-command", "§cНекорректная команда "),
        WRONG_CLAN_NAME("messages.wrong-clan-name", "§cНеверное название клана "),
        LENGTH_CLAN_NAME("messages.length-clan-name", "§cНазвание клана может содержать от 3 до 12 символов "),
        WRONG_COLOR_NAME("messages.wrong-color-name", "§cНеверное название цвета "),
        WRONG_PLAYER_NAME("messages.wrong-player-name", "§cНеверное имя игрока "),
        WRONG_ROLE_NAME("messages.wrong-role-name", "§cНеверное название роли "),
        CLAN_NAME_TAKEN("messages.clan-name-taken", "§eЭто название клана уже занято "),
        YOU_MEMBER_CLAN("messages.you-member-clan", "§eВы уже состоите в клане "),
        YOU_NOT_MEMBER_CLAN("messages.you-not-member-clan", "§eВы не состоите в клане "),
        YOU_NOT_MEMBER_THIS_CLAN("messages.you-not-member-this-clan", "§eВы не состоите в этом клане "),
        SUCCESS_CREATE_CLAN("messages.success-create-clan", "§bВы успешно создали клан§r §e§l%clan% "),
        NOT_LEADER("messages.not-leader", "§eВы не являетесь§r §b§lГлавой§r §eэтого клана "),
        NO_RIGHTS_INVITE("messages.no-rights-invite", "§eУ вас недостаточно прав, чтобы пригласить игрока "),
        NO_RIGHTS_SET_ROLE("messages.no-rights-set-role", "§eУ вас недостаточно прав для изменения роли игрока "),
        NO_RIGHTS_KICK("messages.no-rights-kick", "§eУ вас недостаточно прав, чтобы выгнать игрока "),
        ALREADY_SENT_INVITATION("messages.already-sent-invitation", "§eНа данный момент этому игроку кто-то уже отправил приглашение "),
        INVITATION_SENT("messages.invitation-sent", "§bПриглашение отправлено "),
        SUCCESS_DELETE_CLAN("messages.success-delete-clan", "§bКлан§r §e§l%clan%§r §bуспешно удален "),
        LEADER_DELETE_CLAN("messages.leader-delete-clan", "§e§lГлава§r §bвашего клана решил удалить клан "),
        PLAYER_MEMBER_CLAN("messages.player-member-clan", "§eЭтот игрок уже состоит в клане "),
        PLAYER_NOT_MEMBER_CLAN("messages.player-not-member-clan", "§eЭтот игрок не состоит в клане "),
        INVITATION("messages.invitation", "§bВы получили приглашение в клан§r §e§l%clan%§r §bот игрока§r §e§l%player% "),
        CLICK_INVITE("messages.click-invite", "Нажмите, чтобы принять приглашение "),
        ACCEPT("messages.accept", "§bПринять приглашение§r §e§l[✔] "),
        YOU_NOT_INVITED("messages.you-not-invited", "§eУ вас нет активных приглашений "),
        THIS_CLAN_MAX("messages.this-clan-max", "§eВ этом клане уже максимальное количество игроков "),
        YOUR_CLAN_MAX("messages.your-clan-max", "§eВ вашем клане уже максимальное количество игроков "),
        SEND_INVITATION_YOURSELF("messages.send-invitation-yourself", "§eВы не можете отправить приглашение самому себе "),
        SUCCESSFULLY_JOINED_CLAN("messages.successfully-joined-clan", "§bВы успешно вступили в клан§r §e§l%clan% "),
        PLAYER_JOINED("messages.player-joined", "§bИгрок§r §e§l%player%§r §bвступил в клан "),
        PLAYER_NOT_YOUR_CLAN("messages.player-not-your-clan", "§eЭтот игрок не состоит в вашем клане "),
        ROLE_IS_HIGHER("messages.role-is-higher", "§eВы не можете выгнать игрока, потому что его роль выше или такая же, как ваша "),
        PLAYER_WAS_KICKED("messages.player-was-kicked", "§bИгрок§r §e§l%player%§r §bбыл выгнан из клана "),
        KICK_YOURSELF("messages.kick-yourself", "§eВы не можете выгнать себя "),
        YOU_WAS_KICKED("messages.you-was-kicked", "§bВас выгнали из клана§r §e§l%clan% "),
        YOU_LEADER("messages.you-leader", "§eВы не можете покинуть клан, потому что вы являетесь§r §b§lГлавой§r §eэтого клана "),
        PLAYER_LEAVE("messages.player-leave", "§bИгрок§r §e§l%player%§r §bпокинул клан "),
        SUCCESSFULLY_LEFT("messages.successfully-left", "§bВы успешно покинули клан§r §e§l%clan% "),
        SET_ROLE_YOURSELF("messages.set-role-yourself", "§eВы не можете изменить свою роль "),
        YOU_BEEN_ASSIGNED("messages.you-been-assigned", "§bВам была присвоена роль§r §e§l%role% "),
        SUCCESSFULLY_CHANGED_ROLE("messages.successfully-changed-role", "§bВы успешно изменили роль игрока на§r §e§l%role% "),
        YOU_ALREADY_LEADER("messages.you-already-leader", "§eВы уже являетесь§r §b§lГлавой§r §eэтого клана "),
        SUCCESSFULLY_CHANGE_LEADER("messages.successfully-change-leader", "§bВы успешно передали роль§r §e§lГлавы§r §bигроку§r §e§l%player% "),
        CHANGE_LEADER("messages.change-leader", "§e§l%old-leader%§r §bпередал роль§r §e§lГлавы§r §e§l%new-leader%§r "),
        YOU_HAVE_BEEN_LEADER("messages.you-have-been-leader", "§bВам передали роль§r §e§lГлавы§r §bклана "),
        INVALID_BANK("messages.invalid-bank", "§cНекорректное значение, оно должно быть не больше 64 и содержать только цифры "),
        NO_RIGHTS_BANK("messages.no-rights-bank", "§eУ вас недостаточно прав на опустошение счета банка клана "),
        EMPTY_BANK("messages.empty-bank", "§eНа счету банка клана недостаточно средств:§r §b§l%value%$§r "),
        NOT_CURRENCY("messages.not-currency", "§eУ вас нет такого количества валюты "),
        INVENTORY_FULL("messages.inventory-full", "§eВ вашем инвентаре не хватает места "),
        SUCCESSFULLY_DEPOSIT_BANK("messages.successfully-deposit-bank", "§bВы успешно пополнили счет банка клана на§r §e§l%value%$§r "),
        SUCCESSFULLY_WITHDRAW_BANK("messages.successfully-withdraw-bank", "§bВы успешно опустошили счет банка клана на§r §e§l%value%$§r "),
        NOT_CURRENCY_CREATE_CLAN("messages.not-currency-create-clan", "§eУ вас не хватает валюты для создания клана "),
        LETTER_MESSAGE("messages.letter-message", "§bПисьмо:§r§e%letter%§r "),
        UNREAD_LETTER_MESSAGE("messages.unread-letter-message", "§bУ вас есть непрочитанное письмо клана "),
        SUCCESSFULLY_LETTER_MESSAGE("messages.successfully-letter-message", "§bВы успешно написали письмо своему клану "),
        NO_RIGHTS_LETTER("messages.no-rights-letter", "§eУ вас недостаточно прав, чтобы написать письмо клану "),
        NO_LETTER_WRITTEN("messages.no-letter-written", "§b§lГлава§r §eклана еще не писал писем для игроков "),
        NO_RIGHTS_USE_COMMAND("messages.no-rights-use-command", "§eУ вас недостаточно прав на использование данной команжы "),
        CLAN_NOT_EXIST("messages.clan-not-exist", "§eЭтого клана не существует "),
        INFO_PATTERN("messages.info-pattern", "§bНазвание клана:§r %clan% \n§bГлава клана:§r §5%leader%§r \n§bКоличество участников:§r §e%members%☠§r \n§bКоличество килов:§r §4%kills%\uD83D\uDDE1§r \n§bБанк клана:§r §2%bank%$§r "),
        INVALID_TOP("messages.invalid-top", "§cНекорректное имя топа "),
        TITLE_KILLS_TOP("messages.title-kills-top", "§b§l⏴-------§r§e§lТОП ПО КИЛАМ§r§b§l-------⏵§r "),
        TITLE_MONEY_TOP("messages.title-money-top", "§b§l⏴-------§r§e§lТОП ПО ВАЛЮТЕ§r§b§l-------⏵§r "),
        TITLE_END("messages.title-end", "§b§l⏴---------------------⏵§r "),
        TITLE_EMPTY_TOP("messages.title-empty-top", "Топ пустой "),

        CLAN_MENU("menu-gui.clan-menu.inventory-name", "Меню клана "),
        CLAN_BANK("menu-gui.clan-menu.clan-bank", "Банк клана "),
        CLAN_BANK_DESCRIPTION("menu-gui.clan-menu.clan-bank-description", "Открывает меню банка клана "),
        CLAN_MEMBERS("menu-gui.clan-menu.clan-members", "Меню участников "),
        CLAN_MEMBERS_DESCRIPTION("menu-gui.clan-menu.clan-members-description", "Открывает список участников "),
        TOP_CLANS("menu-gui.clan-menu.top-clans", "Топ кланов "),
        TOP_CLANS_DESCRIPTION("menu-gui.clan-menu.top-clans-description", "Открывает список лучших кланов "),
        CLAN_SETTINGS("menu-gui.clan-menu.clan-settings", "Настройка клана "),
        CLAN_SETTINGS_DESCRIPTION("menu-gui.clan-menu.clan-settings-description", "Открывает меню настроек клана "),
        CLAN_INFO("menu-gui.clan-menu.clan-info", "Клановая информация "),
        CLAN_INFO_DESCRIPTION("menu-gui.clan-menu.clan-info-description", "Показывает информацию о клане "),

        MEMBERS_MENU("menu-gui.members-menu.inventory-name", "Меню участников "),
        PREVIOUS_PAGE("menu-gui.members-menu.previous-page", "Предыдущая страница "),
        PREVIOUS_PAGE_DESCRIPTION("menu-gui.members-menu.previous-page-description", "Открывает предыдущую страницу "),
        NEXT_PAGE("menu-gui.members-menu.next-page", "Следующая страница "),
        NEXT_PAGE_DESCRIPTION("menu-gui.members-menu.next-page-description", "Открывает следующую страницу "),
        MEMBERS_BACK_TO_MENU("menu-gui.members-menu.members-back-to-menu", "Вернуться назад "),
        MEMBERS_BACK_TO_MENU_DESCRIPTION("menu-gui.members-menu.members-back-to-menu-description", "Открывает основное меню клана "),

        MEMBER_MENU("menu-gui.member-menu.inventory-name", "Меню участника "),
        CHANGE_ROLE("menu-gui.member-menu.change-role", "Сменить роль "),
        CHANGE_ROLE_DESCRIPTION("menu-gui.member-menu.change-role-description", "Открывает меню ролей "),
        KICK_MEMBER("menu-gui.member-menu.kick-member", "Выгнать участника "),
        KICK_MEMBER_DESCRIPTION("menu-gui.member-menu.kick-member-description", "Выгоняет участника из клана "),
        MEMBER_BACK_TO_MENU("menu-gui.member-menu.member-back-to-menu", "Вернуться назад "),
        MEMBER_BACK_TO_MENU_DESCRIPTION("menu-gui.member-menu.member-back-to-menu-description", "Открывает меню участников "),

        CHANGE_ROLE_MENU("menu-gui.change-role-menu.inventory-name", "Смена роли "),
        SET_MEMBER("menu-gui.change-role-menu.set-member", "Участник "),
        SET_MEMBER_DESCRIPTION("menu-gui.change-role-menu.set-member-description", "Установит роль Участника "),
        SET_ELDER("menu-gui.change-role-menu.set-elder", "Старейшина "),
        SET_ELDER_DESCRIPTION("menu-gui.change-role-menu.set-elder-description", "Установит роль Старейшины "),
        SET_LEADER("menu-gui.change-role-menu.set-leader", "Глава "),
        SET_LEADER_DESCRIPTION("menu-gui.change-role-menu.set-leader-description", "Установит роль Главы "),
        CHANGE_ROLE_BACK_TO_MENU("menu-gui.change-role-menu.change-role-back-to-menu", "Вернуться назад "),
        CHANGE_ROLE_BACK_TO_MENU_DESCRIPTION("menu-gui.change-role-menu.change-role-back-to-menu-description", "Открывает меню участника "),

        CONFIRM_ROLE_MENU("menu-gui.confirm-role-menu.inventory-name", "Подтверждение роли "),
        CONFIRM_ROLE("menu-gui.confirm-role-menu.confirm-role", "Подтвердить "),
        CONFIRM_ROLE_DESCRIPTION("menu-gui.confirm-role-menu.confirm-role-description", "Установит роль Главы "),
        NOT_CONFIRM_ROLE("menu-gui.confirm-role-menu.not-confirm-role", "Отмена "),
        NOT_CONFIRM_ROLE_DESCRIPTION("menu-gui.confirm-role-menu.not-confirm-role-description", "Отменяет установку роли Главаы "),
        CONFIRM_ROLE_BACK_TO_MENU("menu-gui.confirm-role-menu.confirm-role-back-to-menu", "Вернуться назад "),
        CONFIRM_ROLE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-role-menu.confirm-role-back-to-menu-description", "Открывает меню ролей "),

        BANK_CLAN_MENU("menu-gui.bank-clan-menu.inventory-name", "Банк клана "),
        DEPOSIT("menu-gui.bank-clan-menu.deposit", "Пополнить "),
        DEPOSIT_DESCRIPTION("menu-gui.bank-clan-menu.deposit-description", "Открывает меню пополнения "),
        BANK_ACCOUNT("menu-gui.bank-clan-menu.bank-account", "Счет банка "),
        BANK_ACCOUNT_DESCRIPTION("menu-gui.bank-clan-menu.bank-account-description", "%value%$ "),
        WITHDRAW("menu-gui.bank-clan-menu.withdraw", "Снять "),
        WITHDRAW_DESCRIPTION("menu-gui.bank-clan-menu.withdraw-description", "Открывает меню вывода "),
        BANK_CLAN_BACK_TO_MENU("menu-gui.bank-clan-menu.bank-clan-back-to-menu", "Вернуться назад "),
        BANK_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.bank-clan-menu.bank-clan-back-to-menu-description", "Открывает основное меню клана "),

        DEPOSIT_MENU("menu-gui.deposit-menu.inventory-name", "Меню пополнения "),
        DEPOSIT_BACK_TO_MENU("menu-gui.deposit-menu.deposit-back-to-menu", "Вернуться назад "),
        DEPOSIT_BACK_TO_MENU_DESCRIPTION("menu-gui.deposit-menu.deposit-back-to-menu-description", "Открывает меню банка клана "),

        WITHDRAW_MENU("menu-gui.withdraw-menu.inventory-name", "Меню вывода "),
        WITHDRAW_BACK_TO_MENU("menu-gui.withdraw-menu.withdraw-back-to-menu", "Вернуться назад "),
        WITHDRAW_BACK_TO_MENU_DESCRIPTION("menu-gui.withdraw-menu.withdraw-back-to-menu-description", "Открывает меню банка клана "),

        TOP_CLAN_MENU("menu-gui.top-clan-menu.inventory-name", "Топ кланов "),
        KILLS_TOP("menu-gui.top-clan-menu.kills-top", "Топ по килам "),
        KILLS_TOP_DESCRIPTION("menu-gui.top-clan-menu.kills-top-description", "Показывает топ по килам "),
        MONEY_TOP("menu-gui.top-clan-menu.money-top", "Топ по деньгам "),
        MONEY_TOP_DESCRIPTION("menu-gui.top-clan-menu.money-top-description", "Показывает топ по деньгам "),
        TOP_CLAN_BACK_TO_MENU("menu-gui.top-clan-menu.top-clan-back-to-menu", "Вернуться назад "),
        TOP_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.top-clan-menu.top-clan-back-to-menu-description", "Открывает основное меню клана "),

        CLAN_SETTINGS_MENU("menu-gui.clan-settings-menu.inventory-name", "Настройки клана "),
        LETTER_CLAN("menu-gui.clan-settings-menu.letter-clan", "Письмо клану "),
        LETTER_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.letter-clan-description", "Показывает письмо клану "),
        LEAVE_CLAN("menu-gui.clan-settings-menu.leave-clan", "Покинуть клан "),
        LEAVE_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.leave-clan-description", "Покидает клан "),
        DELETE_CLAN("menu-gui.clan-settings-menu.delete-clan", "Удалить клан "),
        DELETE_CLAN_DESCRIPTION("menu-gui.clan-settings-menu.delete-clan-description", "Удаляет клан "),
        CLAN_SETTINGS_CLAN_BACK_TO_MENU("menu-gui.clan-settings-menu.clan-settings-back-to-menu", "Вернуться назад "),
        CLAN_SETTINGS_CLAN_BACK_TO_MENU_DESCRIPTION("menu-gui.clan-settings-menu.clan-settings-back-to-menu-description", "Открывает основное меню клана "),

        CONFIRM_LEAVE_MENU("menu-gui.confirm-leave-menu.inventory-name", "Подтверждение выхода "),
        CONFIRM_LEAVE("menu-gui.confirm-leave-menu.confirm-leave", "Подтвердить "),
        CONFIRM_LEAVE_DESCRIPTION("menu-gui.confirm-leave-menu.confirm-leave-description", "Выйдет из клана "),
        NOT_CONFIRM_LEAVE("menu-gui.confirm-leave-menu.not-confirm-leave", "Отмена "),
        NOT_CONFIRM_LEAVE_DESCRIPTION("menu-gui.confirm-leave-menu.not-confirm-leave-description", "Отменяет выход из клана "),
        CONFIRM_LEAVE_BACK_TO_MENU("menu-gui.confirm-leave-menu.confirm-leave-back-to-menu", "Вернуться назад "),
        CONFIRM_LEAVE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-leave-menu.confirm-leave-back-to-menu-description", "Открывает меню настроек клана "),

        CONFIRM_DELETE_MENU("menu-gui.confirm-delete-menu.inventory-name", "Подтверждение удаления "),
        CONFIRM_DELETE("menu-gui.confirm-delete-menu.confirm-delete", "Подтвердить "),
        CONFIRM_DELETE_DESCRIPTION("menu-gui.confirm-delete-menu.confirm-delete-description", "Удалит клан "),
        NOT_CONFIRM_DELETE("menu-gui.confirm-delete-menu.not-confirm-delete", "Отмена "),
        NOT_CONFIRM_DELETE_DESCRIPTION("menu-gui.confirm-delete-menu.not-confirm-delete-description", "Отменяет удаление клана "),
        CONFIRM_DELETE_BACK_TO_MENU("menu-gui.confirm-delete-menu.confirm-delete-back-to-menu", "Вернуться назад "),
        CONFIRM_DELETE_BACK_TO_MENU_DESCRIPTION("menu-gui.confirm-delete-menu.confirm-delete-back-to-menu-description", "Открывает меню настроек клана "),

        LEADER("roles.leader", "Глава "),
        ELDER("roles.elder", "Старейшина "),
        MEMBER("roles.member", "Участник "),
        NO_CLAN("roles.no-clan", "НетКлана ");
        private String path;
        private String msg;
        private static YamlConfiguration LANG;

        LangRU(String path, String msg) {
            this.path = path;
            this.msg = msg;
        }

        public static void setFile(YamlConfiguration config) {
            LANG = config;
        }

        @Override
        public String toString() {
            return LANG.getString(path, msg);
        }

        public String getMsg() {
            return this.msg;
        }
        public String getPath() {
            return this.path;
        }
    }
}