package unitedclans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;


public class CreateClanTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("<clan name>");
        } else if (args.length == 2) {
            String inputColor = args[1].toUpperCase();
            String[] colorCollection = new String[] {"AQUA", "BLACK", "BLUE", "DARK_AQUA", "DARK_BLUE", "DARK_GRAY", "DARK_GREEN", "DARK_PURPLE" ,"DARK_RED", "GOLD", "GRAY", "GREEN", "LIGHT_PURPLE", "RED", "WHITE", "YELLOW"};
            List<String> colorNames = null;
            for (String color:colorCollection) {
                if (color.startsWith(inputColor)) {
                    if (colorNames == null) {
                        colorNames = new ArrayList<>();
                    }
                    colorNames.add(color);
                }
            }
            if (colorNames != null) {
                Collections.sort(colorNames);
            }
            return colorNames;
        }
        return new ArrayList<>();
    }
}