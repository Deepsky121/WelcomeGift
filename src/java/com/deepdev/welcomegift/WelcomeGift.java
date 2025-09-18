package com.deepdev.welcomegift;

import com.deepdev.welcomegift.commands.WelcomeGiftCommand;
import com.deepdev.welcomegift.listeners.FirstJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WelcomeGift extends JavaPlugin {

    private static WelcomeGift instance;

    @Override
    public void onEnable() {
        instance = this;

        // Load default config
        saveDefaultConfig();

        // Register listener
        getServer().getPluginManager().registerEvents(new FirstJoinListener(this), this);

        // Register command
        getCommand("welcomegift").setExecutor(new WelcomeGiftCommand(this));
        getCommand("welcomegift").setTabCompleter(new WelcomeGiftCommand(this));

        getLogger().info("DeepsWelcomeGift has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DeepsWelcomeGift has been disabled!");
    }

    public static WelcomeGift getInstance() {
        return instance;
    }
}
