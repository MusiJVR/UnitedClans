package unitedclans.utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import unitedclans.UnitedClans;


public class GeneralUtils {
    public static boolean checkUtil (Player player, String language, String msg, Boolean value) {
        player.sendMessage(LocalizationUtils.langCheck(language, msg));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
        return value;
    }

    public static boolean checkDigits (String string) {
        boolean digits = true;
        for(int i = 0; i < string.length() && digits; i++) {
            if(!Character.isDigit(string.charAt(i))) {
                digits = false;
            }
        }
        return digits;
    }

    public static int setDefaultValue(Integer value, String pathConfig, Integer minValue, Integer maxValue) {
        String valueDefaultConfig = UnitedClans.getInstance().getConfig().getString(pathConfig);
        if (GeneralUtils.checkDigits(valueDefaultConfig)) {
            Integer valueDefault = new Integer(valueDefaultConfig);

            if (valueDefault >= minValue && valueDefault <= maxValue) {
                value = valueDefault;
            }
        }

        return value;
    }

    public static void removeItems (Player player, Integer value) {
        Integer itemsRemoved = 0;
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getType() != Material.valueOf(UnitedClans.getInstance().getConfig().getString("server-currency"))) {
                continue;
            }

            Integer amount = itemStack.getAmount();
            Integer itemsToRemove = Math.min(value - itemsRemoved, amount);
            itemStack.setAmount(amount - itemsToRemove);
            itemsRemoved += itemsToRemove;

            if (itemsRemoved >= value) {
                break;
            }
        }
    }
}