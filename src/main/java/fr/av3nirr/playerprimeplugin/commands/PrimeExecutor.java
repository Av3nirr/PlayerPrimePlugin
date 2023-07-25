package fr.av3nirr.playerprimeplugin.commands;

import fr.av3nirr.playerprimeplugin.PlayerPrimePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class PrimeExecutor implements CommandExecutor {
    PlayerPrimePlugin main = PlayerPrimePlugin.getInstance();
    FileConfiguration primes = main.getPrimesFile();
    Player target;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;

        if(args.length < 2) return true;
        try{
            target = Bukkit.getPlayer(args[1]);
        }catch (ArrayIndexOutOfBoundsException ignored){}
        if(target == null){
            p.sendMessage("§cThere is a problem with the player, wa can't find it.");
            return true;
        }
        UUID targetUUID = target.getUniqueId();
        switch (args[0]){
            case "add"-> {
                if (args.length <3){
                    p.sendMessage("§cYou must specify a prime amount !");
                    return true;
                }
                if (target == p){
                    p.sendMessage("§You can't set a prime on your head !");
                }
                if(primes.get("primes." + targetUUID) == null){
                    primes.set("primes." + targetUUID + ".value", args[2]);
                    primes.set("primes." + targetUUID + ".player", p.getUniqueId());
                    main.savePrimes();
                    Bukkit.broadcastMessage(p.getName() + "Just set a prime on the head of §e" + target.getName());
                    target.sendTitle("§aNew prime", p.getName() + "just placed a §e" + args[2] + "$ §f on your head", 1,20,1);
                    return true;
                }
                p.sendMessage("§cThe target player already have a prime on his head !");
                return true;
            }
            case "remove"-> {
                if(!p.hasPermission("playerprimeplugin.removeprime")){
                    p.sendMessage("§cYou don't have the permission to do that !");
                }
                if(primes.get("primes." + targetUUID) != null){
                    primes.set("primes." + targetUUID, null);
                    main.savePrimes();
                    Bukkit.broadcastMessage(p.getName() + "Just removed the prime on the head of §e" + target.getName());
                    target.sendTitle("§aRemoved prime", p.getName() + "just removed the §e" + primes.get("primes." + targetUUID + ".value")+ "$ §f on your head", 1,20,1);
                    return true;
                }
            }
            default -> {
                return true;
            }
        }

        return false;
    }
}
