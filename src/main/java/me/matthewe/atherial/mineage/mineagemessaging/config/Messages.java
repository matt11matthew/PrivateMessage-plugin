package me.matthewe.atherial.mineage.mineagemessaging.config;

import net.atherial.api.config.SerializedName;
import net.atherial.api.config.YamlConfig;
import net.atherial.api.plugin.utilities.MessageUtils;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew E on 5/25/2019 at 8:26 PM for the project MineageMessaging
 */
public class Messages extends YamlConfig {
    @SerializedName("prefix")
    public static String PREFIX = "&3&lMineage&b&lMessaging &7";
    @SerializedName("reloadConfig")
    public static String RELOAD = "%prefix%Reloaded config.";
    @SerializedName("toggleMessages.enabled")
    public static String ENABLE_MESSAGES = "%prefix%Messaging now &aenabled.";
    @SerializedName("toggleMessages.disabled")
    public static String DISABLE_MESSAGES = "%prefix%Messaging now &cdisabled.";


    @SerializedName("commandsToUnregister")
    public static List<String> UNREGISTER_COMMANDS = new ArrayList<>();
    @SerializedName("privateMessage.sending")
    public static String SENDING_MESSAGE = "&8[&ame &8> &a%target_display_name%&8] &f%message%";
    @SerializedName("privateMessage.incoming")
    public static String INCOMING_MESSAGE = "&8[&a%sender_display_name% &8> &ame&8] &f%message%";

    @SerializedName("clickable")
    public static Boolean CLICK_TO_MESSAGE = true;
    @SerializedName("errors.playerOffline")
    public static String PLAYER_OFFLINE = "%prefix%&cCould not find the player &7%player%&c.";
    @SerializedName("errors.messagingDisabled")
    public static String MESSAGING_DISABLED = "%prefix%&cThe player &7%player%&c has messaging disabled.";
    @SerializedName("errors.youHavMessagingDisabled")
    public static String YOU_MESSAGING_DISABLED = "%prefix%&cYou currently have messaging disabled.";
    @SerializedName("errors.cantMessageSelf")
    public static String CANT_MESSAGE_SELF = "%prefix%&cYou cannot message yourself.";
    @SerializedName("errors.nobodyToReplyTo")
    public static String NOBODY_TO_REPLY_TO = "%prefix%&cYou have nobody to reply to.";

    public Messages(Plugin plugin) {
        super("messages.yml", plugin);
    }

    public static String replacePrefix(String input) {
        return MessageUtils.colorize(input).replaceAll("%prefix%", MessageUtils.colorize(PREFIX));
    }

}
