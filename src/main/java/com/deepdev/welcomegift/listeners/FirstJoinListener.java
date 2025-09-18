package com.deepdev.welcomegift.listeners;

import com.deepdev.welcomegift.WelcomeGift;
import com.deepdev.welcomegift.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class FirstJoinListener implements Listener {

    private final WelcomeGift plugin;

    public FirstJoinListener(WelcomeGift plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!plugin.getConfig().contains("players." + uuid.toString())) {
            // First join → give rewards
            ItemBuilder.RewardManager rewardManager = new ItemBuilder.RewardManager(plugin);
            rewardManager.giveRewards(player);

            // Save data
            plugin.getConfig().set("players." + uuid.toString(), true);
            plugin.saveConfig();
        }
    }
}
