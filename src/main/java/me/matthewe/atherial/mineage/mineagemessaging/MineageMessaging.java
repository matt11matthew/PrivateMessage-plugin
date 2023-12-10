package me.matthewe.atherial.mineage.mineagemessaging;

import me.matthewe.atherial.mineage.mineagemessaging.commands.ReplyCommand;
import me.matthewe.atherial.mineage.mineagemessaging.commands.message.MessageCommand;
import me.matthewe.atherial.mineage.mineagemessaging.config.Messages;
import me.matthewe.atherial.mineage.mineagemessaging.config.MineageMessagingCommandMessages;
import me.matthewe.atherial.mineage.mineagemessaging.player.MessagePlayer;
import me.matthewe.atherial.mineage.mineagemessaging.player.PlayerHandler;
import net.atherial.api.event.AtherialEventListener;
import net.atherial.api.plugin.AtherialPlugin;
import net.atherial.api.plugin.utilities.message.chat.ChatMessage;
import net.atherial.api.plugin.utilities.message.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static net.atherial.api.plugin.utilities.MessageUtils.colorize;
import static net.atherial.api.plugin.utilities.MessageUtils.message;

public class MineageMessaging extends AtherialPlugin {
    private static MineageMessaging instance;
    private List<MineageMessagingHandler> handlers = new ArrayList<>();
    private AtherialEventListener eventListener;
    private Messages messages;

    @Override
    public void initDependencies() {

    }

    @Override
    public void initConfigs() {
        this.messages = new Messages(this);
    }

    @Override
    public void onAtherialEnable() {
        instance = this;
        this.enableAtherial();
        setEnablePlayerDamageByPlayer(false);
        commandMessages = new MineageMessagingCommandMessages(this);
        this.eventListener = AtherialEventListener.create(this);

        if (!Messages.UNREGISTER_COMMANDS.isEmpty()&&Bukkit.getPluginManager().isPluginEnabled("Essentials")) {

            JavaPlugin essentials = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Essentials");
            if (essentials!=null){
                unregisterCommands(Messages.UNREGISTER_COMMANDS.toArray(new String[0]), essentials);
            }
        }

        registerCommand(new MessageCommand(this));
        registerCommand(new ReplyCommand(this));
        handlers.add(new PlayerHandler(this));
        handlers.forEach(MineageMessagingHandler::enable);

    }


    public void sendPrivateMessage(Player sender, Player toPlayer, String message) {
        String senderNameColor = sender.getDisplayName();
        if (senderNameColor.contains("]")) {
            senderNameColor = senderNameColor.split("]")[1].trim();
        }
        String toNameColor = toPlayer.getDisplayName();
        if (toNameColor.contains("]")) {
            toNameColor = toNameColor.split("]")[1].trim();
        }
        MessagePlayer senderMessagePlayer = MessagePlayer.get(sender);
        if (senderMessagePlayer != null) {
            senderMessagePlayer.setLastMessaged(toPlayer.getUniqueId());
        }
        MessagePlayer toMessagePlayer = MessagePlayer.get(toPlayer);
        if (toMessagePlayer != null) {
            toMessagePlayer.setLastMessaged(sender.getUniqueId());
        }

        if (Messages.CLICK_TO_MESSAGE) {


            String sendingMessage = Messages.replacePrefix(Messages.SENDING_MESSAGE)
                    .replaceAll("%sender_name%", sender.getName())
                    .replaceAll("%target_name%", toPlayer.getName())
                    .replaceAll("%target_display_name%", toPlayer.getDisplayName())
                    .replaceAll("%target_name_color%", toNameColor)
                    .replaceAll("%sender_name_color%", senderNameColor)
                    .replaceAll("%sender_display_name%", sender.getDisplayName());
            String incomingMessage = Messages.replacePrefix(Messages.INCOMING_MESSAGE)
                    .replaceAll("%sender_name%", sender.getName())
                    .replaceAll("%target_name%", toPlayer.getName())
                    .replaceAll("%target_display_name%", toPlayer.getDisplayName())
                    .replaceAll("%target_name_color%", toNameColor)
                    .replaceAll("%sender_name_color%", senderNameColor)
                    .replaceAll("%sender_display_name%", sender.getDisplayName());
            int sendingMessageStart = sendingMessage.trim().split("%message%")[0].trim().length();
            int incomingMessageStart = incomingMessage.trim().split("%message%")[0].trim().length();

            ChatMessage.builder()
                    .append(ChatMessage.builder()
                            .text(sendingMessage.substring(0, sendingMessageStart))
                            .clickEvent(ClickEvent.suggestCommand("/msg " + toPlayer.getName() + " "))
                            .build())
                    .append(ChatMessage.builder()
                            .text(sendingMessage.substring(sendingMessageStart).replaceAll("%message%", ChatColor.stripColor(colorize(message).replace("%prefix%", ""))))
                            .build())
                    .build().send(sender);

            ChatMessage.builder()
                    .append(ChatMessage.builder()
                            .text(incomingMessage.substring(0, incomingMessageStart))
                            .clickEvent(ClickEvent.suggestCommand("/msg " + sender.getName() + " "))
                            .build())
                    .append(ChatMessage.builder()
                            .text(incomingMessage.substring(incomingMessageStart).replaceAll("%message%", ChatColor.stripColor(colorize(message).replace("%prefix%", ""))))

                            .build())
                    .build().send(toPlayer);

        } else {
            message(toPlayer, Messages.replacePrefix(Messages.INCOMING_MESSAGE)
                    .replaceAll("%sender_name%", sender.getName())
                    .replaceAll("%target_name%", toPlayer.getName())
                    .replaceAll("%target_display_name%", toPlayer.getDisplayName())
                    .replaceAll("%target_name_color%", toNameColor)
                    .replaceAll("%sender_name_color%", senderNameColor)
                    .replaceAll("%sender_display_name%", sender.getDisplayName())
                    .replaceAll("%message%", ChatColor.stripColor(colorize(message).replace("%prefix%", ""))));
            message(sender, Messages.replacePrefix(Messages.SENDING_MESSAGE)
                    .replaceAll("%sender_name%", sender.getName())
                    .replaceAll("%target_name%", toPlayer.getName())
                    .replaceAll("%target_display_name%", toPlayer.getDisplayName())
                    .replaceAll("%target_name_color%", toNameColor)
                    .replaceAll("%sender_name_color%", senderNameColor)
                    .replaceAll("%sender_display_name%", sender.getDisplayName())
                    .replaceAll("%message%", ChatColor.stripColor(colorize(message).replace("%prefix%", ""))));
        }
    }

    public void reloadHandlers() {

        this.messages = new Messages(this);
        handlers.forEach(MineageMessagingHandler::reloadHandler);
    }

    @Override
    public void onDisable() {
        handlers.forEach(MineageMessagingHandler::disable);
    }

    public static MineageMessaging getInstance() {
        return instance;
    }

    public AtherialEventListener getEventListener() {
        return eventListener;
    }

    public <T extends MineageMessagingHandler> T getHandler(Class<T> clazz) {
        return (T) handlers.stream().filter(mineageMessagingHandler -> mineageMessagingHandler.getClass().equals(clazz)).findFirst().orElseGet(null);
    }
}
