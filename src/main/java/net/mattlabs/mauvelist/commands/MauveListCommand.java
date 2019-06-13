package net.mattlabs.mauvelist.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.mattlabs.mauvelist.MauveList;
import net.mattlabs.mauvelist.messaging.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("mauvelist|ml")
@CommandPermission("mauvelist.admin")
public class MauveListCommand extends BaseCommand {

    @Default
    @Description("MauveList base command.")
    public void onDefault(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) MauveList.INSTANCE.getLogger().info("Version " +
                Bukkit.getPluginManager().getPlugin("MauveList").getDescription().getVersion());
        else commandSender.spigot().sendMessage(Messages.version());
    }
}
