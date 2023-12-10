package me.matthewe.atherial.mineage.mineagemessaging.commands.message;

import net.atherial.api.plugin.command.spigot.SpigotCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Matthew E on 5/25/2019 at 8:13 PM for the project MineageMessaging
 */
public class HelpMessageSubCommand extends SpigotCommand {
    private MessageCommand messageCommand;

    public HelpMessageSubCommand(MessageCommand messageCommand) {
        super("help");
        this.messageCommand = messageCommand;
        this.playerOnly = false;
        this.permission = "mineage.messaging.player";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        messageCommand.sendHelp(sender, args);
    }

    @Override
    public List<net.atherial.api.plugin.command.spigot.HelpSubCommand> getHelp(String[] strings) {
        return Arrays.asList(net.atherial.api.plugin.command.spigot.HelpSubCommand.builder()
                .command(messageCommand.getLabel().toLowerCase() + " help")
                .permission("mineage.messaging.player")
                .description("Help command.")
                .build());
    }
}
