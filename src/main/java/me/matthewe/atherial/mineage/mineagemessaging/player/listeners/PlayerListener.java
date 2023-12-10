package me.matthewe.atherial.mineage.mineagemessaging.player.listeners;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import me.matthewe.atherial.mineage.mineagemessaging.player.MessagePlayer;
import me.matthewe.atherial.mineage.mineagemessaging.player.MessagePlayerManager;
import me.matthewe.atherial.mineage.mineagemessaging.player.PlayerHandler;
import net.atherial.api.event.AtherialEventListener;
import net.atherial.api.event.AtherialListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 5/25/2019 at 8:24 PM for the project MineageMessaging
 */
public class PlayerListener implements AtherialListener {
    private PlayerHandler playerHandler;

    public PlayerListener() {
        playerHandler = MineageMessaging.getInstance().getHandler(PlayerHandler.class);
    }

    @Override
    public void setup(AtherialEventListener eventListener) {
        eventListener.onJoin(event -> playerHandler.getPlayerManager().loadPlayer(event.getPlayer()));
        eventListener.onQuit(event -> {
            MessagePlayerManager playerManager = playerHandler.getPlayerManager();
            if (playerManager.isPlayer(event.getPlayer())) {
                MessagePlayer messagePlayer = playerManager.getPlayer(event.getPlayer());

                if (messagePlayer != null) {
                    if (messagePlayer.getLastMessaged() != null) {
                        Player lastMessagePlayer = Bukkit.getPlayer(messagePlayer.getLastMessaged());
                        if (lastMessagePlayer != null && lastMessagePlayer.isOnline()) {
                            MessagePlayer lastMessagedMessagePlayer = playerManager.getPlayer(lastMessagePlayer);
                            if (lastMessagedMessagePlayer !=null&&lastMessagedMessagePlayer.getLastMessaged()!=null&&lastMessagedMessagePlayer.getLastMessaged().equals(event.getPlayer().getUniqueId())){
                                lastMessagedMessagePlayer.setLastMessaged(null);
                            }
                        }

                    }
                    messagePlayer.setLastMessaged(null);
                }
            }
            playerManager.savePlayer(event.getPlayer().getUniqueId());
        });
    }
}
