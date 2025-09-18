package com.deepdev.welcomegift.commands;

import com.deepdev.welcomegift.WelcomeGift;
import com.deepdev.welcomegift.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WelcomeGiftCommand implements CommandExecutor, TabCompleter {

    private final WelcomeGift plugin;

    public WelcomeGiftCommand(WelcomeGift plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§aUsage: /welcomegift <reload|rewards|reset>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission("welcomegift.reload")) {
                    sender.sendMessage("§cYou do not have permission!");
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage("§aConfig reloaded!");
                break;

            case "rewards":
                if (!sender.hasPermission("welcomegift.rewards")) {
                    sender.sendMessage("§cYou do not have permission!");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /welcomegift rewards <player>");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§cPlayer not found!");
                    return true;
                }
                ItemBuilder.RewardManager rewardManager = new ItemBuilder.RewardManager(plugin);
                rewardManager.giveRewards(target);
                sender.sendMessage("§aRewards given to " + target.getName());
                break;

            case "reset":
                if (!sender.hasPermission("welcomegift.reset")) {
                    sender.sendMessage("§cYou do not have permission!");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /welcomegift reset <player>");
                    return true;
                }
                Player resetTarget = Bukkit.getPlayer(args[1]);
                if (resetTarget == null) {
                    sender.sendMessage("§cPlayer not found!");
                    return true;
                }
                UUID uuid = resetTarget.getUniqueId();
                plugin.getConfig().set("players." + uuid.toString(), null);
                plugin.saveConfig();
                sender.sendMessage("§aPlayer " + resetTarget.getName() + " has been reset!");
                break;

            default:
                sender.sendMessage("§aUsage: /welcomegift <reload|rewards|reset>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("reload");
            completions.add("rewards");
            completions.add("reset");
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("rewards") || args[0].equalsIgnoreCase("reset"))) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                completions.add(p.getName());
            }
        }
        return completions;
    }
}
