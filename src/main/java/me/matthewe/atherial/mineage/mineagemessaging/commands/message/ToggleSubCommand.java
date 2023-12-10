package me.matthewe.atherial.mineage.mineagemessaging.commands.message;

import me.matthewe.atherial.mineage.mineagemessaging.config.Messages;
import me.matthewe.atherial.mineage.mineagemessaging.player.MessagePlayer;
import net.atherial.api.plugin.command.spigot.CommandUtils;
import net.atherial.api.plugin.command.spigot.HelpSubCommand;
import net.atherial.api.plugin.command.spigot.SpigotCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static net.atherial.api.plugin.utilities.MessageUtils.message;

/**
 * Created by Matthew E on 5/25/2019 at 8:13 PM for the project MineageMessaging
 */
public class ToggleSubCommand extends SpigotCommand {
    private MessageCommand messageCommand;

    public ToggleSubCommand(MessageCommand messageCommand) {
        super("toggle");
        this.messageCommand = messageCommand;
        this.playerOnly = true;
        this.permission = "mineage.messaging.player";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length != 0) {
            CommandUtils.sendCommandUsage(sender, "/" + messageCommand.getLabel() + " toggle");
            return;
        }

        MessagePlayer messagePlayer = MessagePlayer.get(player);
        if (messagePlayer != null) {
            boolean toggle = !messagePlayer.isMessagingEnabled();
            if (toggle) {
                message(player, Messages.replacePrefix(Messages.ENABLE_MESSAGES));
            } else {
                message(player, Messages.replacePrefix(Messages.DISABLE_MESSAGES));
            }
            messagePlayer.setMessagingEnabled(toggle);
        }
    }

    @Override
    public List<HelpSubCommand> getHelp(String[] strings) {
        return Arrays.asList(HelpSubCommand.builder()
                .command(messageCommand.getLabel().toLowerCase() + " toggle").permission("mineage.messaging.player").description("Toggle messages").build());
    }
}
