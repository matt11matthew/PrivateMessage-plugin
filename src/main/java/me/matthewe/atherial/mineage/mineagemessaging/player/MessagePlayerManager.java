package me.matthewe.atherial.mineage.mineagemessaging.player;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import net.atherial.api.storage.PlayerYamlManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

/**
 * Created by Matthew E on 5/25/2019 at 8:22 PM for the project MineageMessaging
 */
public class MessagePlayerManager extends PlayerYamlManager<MineageMessaging, MessagePlayer> {

    public MessagePlayerManager(MineageMessaging plugin) {
        super(plugin, "players.yml", true);
    }

    @Override
    public MessagePlayer getTemplateData(UUID uuid) {
        return new MessagePlayer(uuid, true, null);
    }

    @Override
    public ConfigurationSection saveToFile(MessagePlayer messagePlayer, String s, ConfigurationSection configurationSection) {
        configurationSection.set("messagingEnabled", messagePlayer.isMessagingEnabled());
        configurationSection.set("lastMessaged", messagePlayer.getLastMessaged() == null ? "none" : messagePlayer.getLastMessaged().toString());
        return configurationSection;
    }

    @Override
    public MessagePlayer loadFromFile(UUID uuid, String s, ConfigurationSection configurationSection) {
        UUID lastMessaged = null;
        if (configurationSection.isSet("lastMessage")) {
            String lastMessage = configurationSection.getString("lastMessaged");
            if (!lastMessage.equalsIgnoreCase("none")) {
                try {
                    lastMessaged = UUID.fromString(lastMessage);
                } catch (Exception ignored) {

                }
            }
        }
        return new MessagePlayer(uuid, configurationSection.getBoolean("messagingEnabled", true), lastMessaged);
    }

}
