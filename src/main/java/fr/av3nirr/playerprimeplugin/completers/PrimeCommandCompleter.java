package fr.av3nirr.playerprimeplugin.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PrimeCommandCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(!(sender instanceof Player p)) return null;
        if (args.length == 1) {
            // Complétion du premier argument ("add", "clear", "remove", "set")
            List<String> completions = new ArrayList<>();
            completions.add("add");
            completions.add("remove");
            return completions;
        } else if (args.length == 2 && args[0] != null) {
            List<String> completions = new ArrayList<>();
            for (Player pl: p.getServer().getOnlinePlayers()) {
                completions.add(pl.getName());
            }
            return completions;
        }
        return null;
    }
}
