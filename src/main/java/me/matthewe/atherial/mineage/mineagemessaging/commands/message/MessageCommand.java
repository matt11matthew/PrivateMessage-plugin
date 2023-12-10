package me.matthewe.atherial.mineage.mineagemessaging.commands.message;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import me.matthewe.atherial.mineage.mineagemessaging.config.Messages;
import me.matthewe.atherial.mineage.mineagemessaging.player.MessagePlayer;
import net.atherial.api.plugin.command.spigot.CommandUtils;
import net.atherial.api.plugin.command.spigot.HelpSubCommand;
import net.atherial.api.plugin.command.spigot.SpigotCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.atherial.api.plugin.utilities.MessageUtils.message;

/**
 * Created by Matthew E on 5/25/2019 at 8:09 PM for the project MineageMessaging
 */
public class MessageCommand extends SpigotCommand {

    private String label;
    private MineageMessaging mineageMessaging;

    public MessageCommand(MineageMessaging mineageMessaging) {
        super("message", "msg", "m", "w", "tell");
        this.mineageMessaging = mineageMessaging;
        this.playerOnly = true;
        this.permission = "mineage.messaging.player";
        this.addSubCommand(new ToggleSubCommand(this));
        this.addSubCommand(new ReloadSubCommand(this));
        this.addSubCommand(new HelpMessageSubCommand(this));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if ((args.length == 1) && ((args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("help")))) {
            return;
        }
        if (args.length < 2) {
            CommandUtils.sendCommandUsage(sender, "/" + label, "(player)", "(message)");
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if ((player == null) || !player.isOnline()) {
            message(sender, Messages.replacePrefix(Messages.PLAYER_OFFLINE).replaceAll("%player%", args[0]));
            return;
        }
        if (player.getName().equalsIgnoreCase(sender.getName())) {
            message(sender, Messages.replacePrefix(Messages.CANT_MESSAGE_SELF));
            return;

        }
        MessagePlayer senderMessagePlayer = MessagePlayer.get((Player) sender);
        if ((senderMessagePlayer != null) && !senderMessagePlayer.isMessagingEnabled()) {
            message(sender, Messages.replacePrefix(Messages.YOU_MESSAGING_DISABLED).replaceAll("%player%", sender.getName()));
            return;
        }
        MessagePlayer messagePlayer = MessagePlayer.get(player);
        if ((messagePlayer != null) && !messagePlayer.isMessagingEnabled()) {
            message(sender, Messages.replacePrefix(Messages.MESSAGING_DISABLED).replaceAll("%player%", player.getName()));
            return;
        }
        String message = "";


        if (args.length == 2) {
            message = args[1].trim();
        } else {
            for (int i = 1; i < args.length; i++) {
                message += args[i] + " ";
            }
        }
        if (message.endsWith(" ")) {
            message = message.trim();
        }
        message = message.replaceAll("\\s+" + Pattern.quote("$$"), Matcher.quoteReplacement("$$"));
        mineageMessaging.sendPrivateMessage((Player) sender, player, message);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.label = label;
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public List<HelpSubCommand> getHelp(String[] args) {
        List<HelpSubCommand> helpSubCommands = new ArrayList<>();
        helpSubCommands.add(HelpSubCommand.builder()
                .command("reply").arguments("(message)").permission("mineage.messaging.player").description("Reply to player.").build());
        helpSubCommands.add(HelpSubCommand.builder().description("Send a message to a player.").permission("mineage.messaging.player").command(label).arguments("(player)", "(message)").build());
        for (SpigotCommand value : this.getSubCommandMap().values()) {

            helpSubCommands.addAll(value.getHelp(args));
        }
        return helpSubCommands;
    }
}
