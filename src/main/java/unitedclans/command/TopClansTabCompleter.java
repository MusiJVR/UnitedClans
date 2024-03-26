package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;


public class TopClansTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String inputTop = args[0].toLowerCase();
            ArrayList<String> topList = new ArrayList<>();
            topList.add("kills");
            topList.add("money");
            List<String> tops = null;
            for (String top : topList) {
                if (top.toString().toLowerCase().startsWith(inputTop)) {
                    if (tops == null) {
                        tops = new ArrayList<>();
                    }
                    tops.add(top);
                }
            }
            if (tops != null) {
                Collections.sort(tops);
            }
            return tops;
        }
        return new ArrayList<>();
    }
}