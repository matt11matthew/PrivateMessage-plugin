package me.matthewe.atherial.mineage.mineagemessaging.player;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import net.atherial.api.storage.AtherialPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Matthew E on 5/25/2019 at 8:22 PM for the project MineageMessaging
 */
public class MessagePlayer extends AtherialPlayer<MessagePlayer> {
    private boolean messagingEnabled;
    private UUID lastMessaged;

    public MessagePlayer(UUID uuid, boolean messagingEnabled, UUID lastMessaged) {
        super(uuid);
        this.messagingEnabled = messagingEnabled;
        this.lastMessaged = lastMessaged;
    }

    public static MessagePlayer get(Player player) {
        return MineageMessaging.getInstance().getHandler(PlayerHandler.class).getPlayerManager().getPlayer(player);
    }

    public void setLastMessaged(UUID lastMessaged) {
        this.lastMessaged = lastMessaged;
    }

    public UUID getLastMessaged() {
        return lastMessaged;
    }

    public boolean isMessagingEnabled() {
        return messagingEnabled;
    }

    public void setMessagingEnabled(boolean messagingEnabled) {
        this.messagingEnabled = messagingEnabled;
    }

    @Override
    public Class<MessagePlayer> getValueClass() {
        return MessagePlayer.class;
    }
}
