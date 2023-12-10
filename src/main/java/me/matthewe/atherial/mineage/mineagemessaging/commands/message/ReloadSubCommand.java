package me.matthewe.atherial.mineage.mineagemessaging.commands.message;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import me.matthewe.atherial.mineage.mineagemessaging.config.Messages;
import net.atherial.api.plugin.command.spigot.HelpSubCommand;
import net.atherial.api.plugin.command.spigot.SpigotCommand;
import net.atherial.api.plugin.utilities.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Matthew E on 5/25/2019 at 8:13 PM for the project MineageMessaging
 */
public class ReloadSubCommand extends SpigotCommand {
    private MessageCommand messageCommand;

    public ReloadSubCommand(MessageCommand messageCommand) {
        super("reload");
        this.messageCommand = messageCommand;
        this.permission = "mineage.messaging.admin";
        this.playerOnly = false;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        MineageMessaging.getInstance().reloadHandlers();
        MessageUtils.message(sender, Messages.replacePrefix(Messages.RELOAD));
    }

    @Override
    public List<HelpSubCommand> getHelp(String[] strings) {
        return Arrays.asList(HelpSubCommand.builder()
                .command(messageCommand.getLabel().toLowerCase() + " reload")
                .permission("mineage.messaging.admin")
                .description("Reload command.")
                .build());
    }
}
