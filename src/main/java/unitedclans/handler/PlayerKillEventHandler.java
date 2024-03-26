package unitedclans.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import unitedclans.utils.SqliteDriver;

import java.util.*;


public class PlayerKillEventHandler implements Listener {
    private SqliteDriver sql;
    public PlayerKillEventHandler(SqliteDriver sql) {
        this.sql = sql;
    }

    @EventHandler
    public void PlayerKillEvent(PlayerDeathEvent event) {
        try {
            if (event.getEntity().getKiller() != null) {
                Player killer = (Player) event.getEntity().getKiller();
                Player victim = (Player) event.getEntity();

                List<Map<String, Object>> rsKiller = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + killer.getUniqueId() + "'");
                Integer killerClanID = (Integer) rsKiller.get(0).get("ClanID");

                List<Map<String, Object>> rsVictim = sql.sqlSelectData("ClanID", "PLAYERS", "UUID = '" + victim.getUniqueId() + "'");
                Integer victimClanID = (Integer) rsVictim.get(0).get("ClanID");

                if (killerClanID != 0 && victimClanID != 0) {
                    sql.sqlUpdateData("PLAYERS", "Kills = Kills + 1", "UUID = '" + killer.getUniqueId() + "'");
                    sql.sqlUpdateData("CLANS", "Kills = Kills + 1", "ClanId = " + killerClanID);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}