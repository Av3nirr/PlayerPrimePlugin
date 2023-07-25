package fr.av3nirr.playerprimeplugin.listeners;

import fr.av3nirr.playerprimeplugin.PlayerPrimePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class KillListener implements Listener {
    PlayerPrimePlugin main = PlayerPrimePlugin.getInstance();
    FileConfiguration primes = main.getPrimesFile();

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = e.getEntity().getKiller();
        if (primes.get("primes." + p.getUniqueId()) != null) {
            primes.set("primes." + p.getUniqueId(), null);
            main.savePrimes();
            Bukkit.broadcastMessage(killer.getName() + "Just killed §e" + p.getName() + " §fwho had a prime on his head. He won §a" + primes.get("primes." + p.getUniqueId() + ".value") + "$");
            killer.sendTitle("§aWon prime", p.getName() + "had a prime on his head. You just won §e" + primes.get("primes." + p.getUniqueId() + ".value") + "$", 1, 20, 1);
        }

    }

}
