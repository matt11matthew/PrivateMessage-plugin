package me.matthewe.atherial.mineage.mineagemessaging.player;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import me.matthewe.atherial.mineage.mineagemessaging.MineageMessagingHandler;
import me.matthewe.atherial.mineage.mineagemessaging.player.listeners.PlayerListener;

/**
 * Created by Matthew E on 5/25/2019 at 8:20 PM for the project MineageMessaging
 */
public class PlayerHandler extends MineageMessagingHandler {
    private MessagePlayerManager playerManager;

    public PlayerHandler(MineageMessaging mineageMessaging) {
        super(mineageMessaging);
    }

    @Override
    public void onEnable() {
        this.playerManager = new MessagePlayerManager(this.mineageMessaging);
        this.playerManager.loadAllOnline();
        this.registerListener(new PlayerListener());


    }

    public MessagePlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onDisable() {
        this.playerManager.saveAllOnline();

    }

    @Override
    public void reloadHandler() {

    }
}
