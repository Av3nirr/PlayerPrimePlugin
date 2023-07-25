package fr.av3nirr.playerprimeplugin;

import fr.av3nirr.playerprimeplugin.commands.PrimeExecutor;
import fr.av3nirr.playerprimeplugin.completers.PrimeCommandCompleter;
import fr.av3nirr.playerprimeplugin.listeners.KillListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PlayerPrimePlugin extends JavaPlugin {
    private static PlayerPrimePlugin INSTANCE;
    private File primesFile;
    private FileConfiguration primeConfig;
    Logger log;

    @Override
    public void onDisable() {
        log.info(String.format(
                "%s %s is now disabled.",
                getDescription().getName(),
                getDescription().getVersion()
        ));
    }


    @Override
    public void onEnable() {
        log = getLogger();
        INSTANCE = this;
        saveDefaultConfig();
        createCustomConfig();

        log.info(String.format(
                "%s %s is now enabled.",
                getDescription().getName(),
                getDescription().getVersion()
        ));

        registerCommands();
        registerListeners();
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new KillListener(), this);
    }

    private void registerCommands() {
        getCommand("prime").setExecutor(new PrimeExecutor());
        getCommand("prime").setTabCompleter(new PrimeCommandCompleter());
    }


    private void createCustomConfig() {
        primesFile = new File(getDataFolder(), "primes.yml");
        if (!primesFile.exists()) {
            primesFile.getParentFile().mkdirs();
            saveResource("primes.yml", false);
        }

        primeConfig = new YamlConfiguration();
        try {
            primeConfig.load(primesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        /* User Edit:
            Instead of the above Try/Catch, you can also use
            YamlConfiguration.loadConfiguration(customConfigFile)
        */
    }
    public FileConfiguration getPrimesFile() {
        return this.primeConfig;
    }
    public void savePrimes(){
        try {
            primeConfig.save(primesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static PlayerPrimePlugin getInstance() {
        return INSTANCE;
    }
}
