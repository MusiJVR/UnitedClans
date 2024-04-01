package unitedclans.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unitedclans.UnitedClans;
import unitedclans.utils.GeneralUtils;


public class HelpClanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        String language = UnitedClans.getInstance().getConfig().getString("lang");
        Player playerSender = (Player) sender;

        if (args.length != 0) {
            return GeneralUtils.checkUtil(playerSender, language, "INVALID_COMMAND", false);
        }

        if (language.equals("en_en")) {
            sender.sendMessage("§b§l⏴-------§r§e§lUnitedClans§r§b§l-------⏵§r \n" +
                    "§bCommand:§r §ehelpclan§r \n" +
                    "§bDescription:§r §eThis command allows you to display all possible plugin commands§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §einfoclan§r \n" +
                    "§bDescription:§r §eThis command allows you to view information about the clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name (not necessary)>§r \n \n" +
                    "§bCommand:§r §ecreateclan§r \n" +
                    "§bDescription:§r §eThis command allows you to create a clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name> <color>§r \n \n" +
                    "§bCommand:§r §edeleteclan§r \n" +
                    "§bDescription:§r §eThis command allows you to delete a clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name>§r \n \n" +
                    "§bCommand:§r §einviteclan§r \n" +
                    "§bDescription:§r §eThis command allows you to invite a player to a clan§r \n" +
                    "§bUsage:§r §e/<command> <player>§r \n \n" +
                    "§bCommand:§r §eacceptclan§r \n" +
                    "§bDescription:§r §eThis command allows you to accept an invitation to a clan§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §ekickclan§r \n" +
                    "§bDescription:§r §eThis command allows you to kick a player from the clan§r \n" +
                    "§bUsage:§r §e/<command> <player>§r \n \n" +
                    "§bCommand:§r §eleaveclan§r \n" +
                    "§bDescription:§r §eThis command allows the player to leave the clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name>§r \n \n" +
                    "§bCommand:§r §esetroleclan§r \n" +
                    "§bDescription:§r §eThis command allows you to set the role of the player§r \n" +
                    "§bUsage:§r §e/<command> <player> <role>§r \n \n" +
                    "§bCommand:§r §emenuclan§r \n" +
                    "§bDescription:§r §eThis command allows you to open the clan menu§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §echatclan§r \n" +
                    "§bDescription:§r §eThis command allows you to send messages to the clan chat§r \n" +
                    "§bUsage:§r §e/<command> <message>§r \n \n" +
                    "§bCommand:§r §echangeleaderclan§r \n" +
                    "§bDescription:§r §eThis command allows you to change the clan Leader§r \n" +
                    "§bUsage:§r §e/<command> <clan name> <player>§r \n \n" +
                    "§bCommand:§r §ebankdepositclan§r \n" +
                    "§bDescription:§r §eThis command allows you to deposit currency into the clan's bank account§r \n" +
                    "§bUsage:§r §e/<command> <number>§r \n \n" +
                    "§bCommand:§r §ebankwithdrawclan§r \n" +
                    "§bDescription:§r §eThis command allows you to withdraw currency from a clan bank account§r \n" +
                    "§bUsage:§r §e/<command> <number>§r \n \n" +
                    "§bCommand:§r §etopclans§r \n" +
                    "§bDescription:§r §eThis command allows you to open the top clans§r \n" +
                    "§bUsage:§r §e/<command> <top name>§r \n \n" +
                    "§bCommand:§r §eletterclan§r \n" +
                    "§bDescription:§r §eThis command allows you to create and view a letter to the clan§r \n" +
                    "§bUsage:§r §e/<command> <letter (not necessary)>§r \n" +
                    "§b§l⏴--------------------------⏵§r");
        } else if (language.equals("ru_ru")) {
            sender.sendMessage("§b§l⏴-------§r§e§lUnitedClans§r§b§l-------⏵§r \n" +
                    "§bКоманда:§r §ehelpclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет отображать все возможные команды плагина§r \n" +
                    "§bИспользование:§r §e/<command>§r \n \n" +
                    "§bКоманда:§r §einfoclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет просмотреть информацию о клане§r \n" +
                    "§bИспользование:§r §e/<command> <clan name (not necessary)>§r \n \n" +
                    "§bКоманда:§r §ecreateclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет создать клан§r \n" +
                    "§bИспользование:§r §e/<command> <clan name> <color>§r \n \n" +
                    "§bКоманда:§r §edeleteclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет удалить клан§r \n" +
                    "§bИспользование:§r §e/<command> <clan name>§r \n \n" +
                    "§bКоманда:§r §einviteclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет пригласить игрока в клан§r \n" +
                    "§bИспользование:§r §e/<command> <player>§r \n \n" +
                    "§bКоманда:§r §eacceptclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет принять приглашение в клан§r \n" +
                    "§bИспользование:§r §e/<command>§r \n \n" +
                    "§bКоманда:§r §ekickclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет выгнать игрока из клана§r \n" +
                    "§bИспользование:§r §e/<command> <player>§r \n \n" +
                    "§bКоманда:§r §eleaveclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет игроку выйти из клана§r \n" +
                    "§bИспользование:§r §e/<command> <clan name>§r \n \n" +
                    "§bКоманда:§r §esetroleclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет вам установить роль игрока§r \n" +
                    "§bИспользование:§r §e/<command> <player> <role>§r \n \n" +
                    "§bКоманда:§r §emenuclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет открыть меню клана§r \n" +
                    "§bИспользование:§r §e/<command>§r \n \n" +
                    "§bКоманда:§r §echatclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет отправлять сообщения в чат клана§r \n" +
                    "§bИспользование:§r §e/<command> <message>§r \n \n" +
                    "§bКоманда:§r §echangeleaderclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет сменить Главу клана§r \n" +
                    "§bИспользование:§r §e/<command> <clan name> <player>§r \n \n" +
                    "§bКоманда:§r §ebankdepositclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет внести валюту на счет кланового банка§r \n" +
                    "§bИспользование:§r §e/<command> <number>§r \n \n" +
                    "§bКоманда:§r §ebankwithdrawclan§r \n" +
                    "§bОписание:§r §eЭта команда позволяет вывести валюту со счета кланового банка§r \n" +
                    "§bИспользование:§r §e/<command> <number>§r \n \n" +
                    "§bКоманда:§r §etopclans§r \n" +
                    "§bОписание:§r §eЭта команда позволяет открыть топ кланов§r \n" +
                    "§bИспользование:§r §e/<command> <top name>§r \n \n" +
                    "§bКоманда:§r §eletterclan§r \n" +
                    "§bОписание:§r §eДанная команда позволяет создать и просмотреть письмо клану§r \n" +
                    "§bИспользование:§r §e/<command> <letter (not necessary)>§r \n" +
                    "§b§l⏴--------------------------⏵§r");
        } else {
            sender.sendMessage("§b§l⏴-------§r§e§lUnitedClans§r§b§l-------⏵§r \n" +
                    "§bCommand:§r §ehelpclan§r \n" +
                    "§bDescription:§r §eThis command allows you to display all possible plugin commands§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §einfoclan§r \n" +
                    "§bDescription:§r §eThis command allows you to view information about the clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name (not necessary)>§r \n \n" +
                    "§bCommand:§r §ecreateclan§r \n" +
                    "§bDescription:§r §eThis command allows you to create a clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name> <color>§r \n \n" +
                    "§bCommand:§r §edeleteclan§r \n" +
                    "§bDescription:§r §eThis command allows you to delete a clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name>§r \n \n" +
                    "§bCommand:§r §einviteclan§r \n" +
                    "§bDescription:§r §eThis command allows you to invite a player to a clan§r \n" +
                    "§bUsage:§r §e/<command> <player>§r \n \n" +
                    "§bCommand:§r §eacceptclan§r \n" +
                    "§bDescription:§r §eThis command allows you to accept an invitation to a clan§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §ekickclan§r \n" +
                    "§bDescription:§r §eThis command allows you to kick a player from the clan§r \n" +
                    "§bUsage:§r §e/<command> <player>§r \n \n" +
                    "§bCommand:§r §eleaveclan§r \n" +
                    "§bDescription:§r §eThis command allows the player to leave the clan§r \n" +
                    "§bUsage:§r §e/<command> <clan name>§r \n \n" +
                    "§bCommand:§r §esetroleclan§r \n" +
                    "§bDescription:§r §eThis command allows you to set the role of the player§r \n" +
                    "§bUsage:§r §e/<command> <player> <role>§r \n \n" +
                    "§bCommand:§r §emenuclan§r \n" +
                    "§bDescription:§r §eThis command allows you to open the clan menu§r \n" +
                    "§bUsage:§r §e/<command>§r \n \n" +
                    "§bCommand:§r §echatclan§r \n" +
                    "§bDescription:§r §eThis command allows you to send messages to the clan chat§r \n" +
                    "§bUsage:§r §e/<command> <message>§r \n \n" +
                    "§bCommand:§r §echangeleaderclan§r \n" +
                    "§bDescription:§r §eThis command allows you to change the clan Leader§r \n" +
                    "§bUsage:§r §e/<command> <clan name> <player>§r \n \n" +
                    "§bCommand:§r §ebankdepositclan§r \n" +
                    "§bDescription:§r §eThis command allows you to deposit currency into the clan's bank account§r \n" +
                    "§bUsage:§r §e/<command> <number>§r \n \n" +
                    "§bCommand:§r §ebankwithdrawclan§r \n" +
                    "§bDescription:§r §eThis command allows you to withdraw currency from a clan bank account§r \n" +
                    "§bUsage:§r §e/<command> <number>§r \n \n" +
                    "§bCommand:§r §etopclans§r \n" +
                    "§bDescription:§r §eThis command allows you to open the top clans§r \n" +
                    "§bUsage:§r §e/<command> <top name>§r \n \n" +
                    "§bCommand:§r §eletterclan§r \n" +
                    "§bDescription:§r §eThis command allows you to create and view a letter to the clan§r \n" +
                    "§bUsage:§r §e/<command> <letter (not necessary)>§r \n" +
                    "§b§l⏴--------------------------⏵§r");
        }
        playerSender.playSound(playerSender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

        return true;
    }
}