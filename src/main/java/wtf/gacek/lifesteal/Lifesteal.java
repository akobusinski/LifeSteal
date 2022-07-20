package wtf.gacek.lifesteal;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.gacek.lifesteal.commands.LifestealCommand;
import wtf.gacek.lifesteal.listeners.Listener;
import wtf.gacek.lifesteal.managers.LifestealManager;
import wtf.gacek.lifesteal.tabcompleters.LifestealCompleter;

import java.util.Objects;

public final class Lifesteal extends JavaPlugin {
    private final LifestealManager lifeManager = new LifestealManager();
    private static Lifesteal instance;
    @Override
    public void onEnable() {
        instance = this;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        getServer().getPluginManager().registerEvents(new Listener(), this);
        addCommand("lifesteal", new LifestealCommand());
        addTabCompleter("lifesteal", new LifestealCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LifestealManager getLifeManager() {
        return instance.lifeManager;
    }

    public static Lifesteal getInstance() {
        return instance;
    }

    public void addCommand(String name, CommandExecutor executor) {
        Objects.requireNonNull(this.getCommand(name)).setExecutor(executor);
    }

    public void addTabCompleter(String name, TabCompleter completer) {
        Objects.requireNonNull(this.getCommand(name)).setTabCompleter(completer);
    }
}
