package me.matthewe.atherial.mineage.mineagemessaging.commands;

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


public class ReplyCommand extends SpigotCommand {

    private String label;
    private MineageMessaging mineageMessaging;

    public ReplyCommand(MineageMessaging mineageMessaging) {
        super("reply", "r");
        this.mineageMessaging = mineageMessaging;
        this.playerOnly = true;
        this.permission = "mineage.messaging.player";
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length < 1) {
            CommandUtils.sendCommandUsage(sender, "/" + label, "(message)");
            return;
        }

        Player player = (Player) sender;


        MessagePlayer senderMessagePlayer = MessagePlayer.get((Player) sender);
        if (senderMessagePlayer == null) {
            return;
        }
        if (!senderMessagePlayer.isMessagingEnabled()) {
            message(sender, Messages.replacePrefix(Messages.YOU_MESSAGING_DISABLED).replaceAll("%player%", sender.getName()));
            return;
        }
        if (senderMessagePlayer.getLastMessaged() == null) {
            message(sender, Messages.replacePrefix(Messages.NOBODY_TO_REPLY_TO).replaceAll("%player%", sender.getName()));
            return;
        }
        Player toPlayer = Bukkit.getPlayer(senderMessagePlayer.getLastMessaged());
        if ((toPlayer == null) || !toPlayer.isOnline()) {
            senderMessagePlayer.setLastMessaged(null);
            message(sender, Messages.replacePrefix(Messages.NOBODY_TO_REPLY_TO).replaceAll("%player%", sender.getName()));
            return;
        }

        MessagePlayer messagePlayer = MessagePlayer.get(toPlayer);
        if (messagePlayer == null) {
            senderMessagePlayer.setLastMessaged(null);
            message(sender, Messages.replacePrefix(Messages.NOBODY_TO_REPLY_TO).replaceAll("%player%", sender.getName()));
            return;
        }
        if (!messagePlayer.isMessagingEnabled()) {
            message(sender, Messages.replacePrefix(Messages.MESSAGING_DISABLED).replaceAll("%player%", player.getName()));
            return;
        }
        String message = "";


        if (args.length == 1) {
            message = args[0].trim();
        } else {
            for (String arg : args) {
                message += arg + " ";
            }
        }
        if (message.endsWith(" ")) {
            message = message.trim();
        }
        message = message.replaceAll("\\s+" + Pattern.quote("$$"), Matcher.quoteReplacement("$$"));
        mineageMessaging.sendPrivateMessage((Player) sender, toPlayer, message);
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
        return new ArrayList<>();
    }
}
