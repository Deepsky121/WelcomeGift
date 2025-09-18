package com.deepdev.welcomegift.util;

import com.deepdev.welcomegift.WelcomeGift;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class RewardManager {

    private final WelcomeGift plugin;

    public RewardManager(WelcomeGift plugin) {
        this.plugin = plugin;
    }

    public void giveRewards(Player player) {
        ConfigurationSection rewards = plugin.getConfig().getConfigurationSection("rewards");
        if (rewards == null) {
            plugin.getLogger().warning("No rewards found in config.yml!");
            return;
        }

        for (String key : rewards.getKeys(false)) {
            ConfigurationSection itemSection = rewards.getConfigurationSection(key);
            if (itemSection == null) continue;

            String materialName = itemSection.getString("material");
            if (materialName == null) continue;

            Material material = Material.matchMaterial(materialName);
            if (material == null) {
                plugin.getLogger().warning("Invalid material in config.yml: " + materialName);
                continue;
            }

            int amount = itemSection.getInt("amount", 1);
            String name = itemSection.getString("name");
            List<String> lore = itemSection.getStringList("lore");
            List<String> enchantments = itemSection.getStringList("enchantments");

            ItemBuilder builder = new ItemBuilder(material)
                    .setAmount(amount)
                    .setName(name)
                    .setLore(lore);

            for (String ench : enchantments) {
                try {
                    String[] parts = ench.split(":");
                    Enchantment enchantment = Enchantment.getByName(parts[0].toUpperCase());
                    int level = Integer.parseInt(parts[1]);
                    if (enchantment != null) {
                        builder.addEnchant(enchantment, level);
                    }
                } catch (Exception ignored) {}
            }

            ItemStack item = builder.build();
            giveItem(player, item);
        }

        String rewardMsg = plugin.getConfig().getString("messages.reward", "&aYou have received your Starter Kit!");
        player.sendMessage(rewardMsg.replace("&", "§"));
    }

    private void giveItem(Player player, ItemStack item) {
        if (item == null) return;

        Material type = item.getType();

        switch (type) {
            case LEATHER_HELMET: case CHAINMAIL_HELMET: case IRON_HELMET: case GOLDEN_HELMET:
            case DIAMOND_HELMET: case NETHERITE_HELMET:
                equipArmor(player, item, EquipmentSlot.HEAD); return;

            case LEATHER_CHESTPLATE: case CHAINMAIL_CHESTPLATE: case IRON_CHESTPLATE: case GOLDEN_CHESTPLATE:
            case DIAMOND_CHESTPLATE: case NETHERITE_CHESTPLATE:
                equipArmor(player, item, EquipmentSlot.CHEST); return;

            case LEATHER_LEGGINGS: case CHAINMAIL_LEGGINGS: case IRON_LEGGINGS: case GOLDEN_LEGGINGS:
            case DIAMOND_LEGGINGS: case NETHERITE_LEGGINGS:
                equipArmor(player, item, EquipmentSlot.LEGS); return;

            case LEATHER_BOOTS: case CHAINMAIL_BOOTS: case IRON_BOOTS: case GOLDEN_BOOTS:
            case DIAMOND_BOOTS: case NETHERITE_BOOTS:
                equipArmor(player, item, EquipmentSlot.FEET); return;

            default:
                player.getInventory().addItem(item);
        }
    }

    private void equipArmor(Player player, ItemStack item, EquipmentSlot slot) {
        PlayerInventory inv = player.getInventory();

        switch (slot) {
            case HEAD:
                if (inv.getHelmet() == null || inv.getHelmet().getType() == Material.AIR) inv.setHelmet(item);
                else inv.addItem(item);
                break;

            case CHEST:
                if (inv.getChestplate() == null || inv.getChestplate().getType() == Material.AIR) inv.setChestplate(item);
                else inv.addItem(item);
                break;

            case LEGS:
                if (inv.getLeggings() == null || inv.getLeggings().getType() == Material.AIR) inv.setLeggings(item);
                else inv.addItem(item);
                break;

            case FEET:
                if (inv.getBoots() == null || inv.getBoots().getType() == Material.AIR) inv.setBoots(item);
                else inv.addItem(item);
                break;
        }
    }
}
