package me.matthewe.atherial.mineage.mineagemessaging;

import net.atherial.api.event.AtherialListener;
import net.atherial.api.plugin.handler.Handler;
import net.atherial.api.plugin.utilities.logger.AtherialLogger;
import net.atherial.api.plugin.utilities.logger.Logger;

import java.util.ArrayList;

/**
 * Created by Matthew E on 5/25/2019 at 8:15 PM for the project MineageMessaging
 */
public abstract class MineageMessagingHandler extends Handler<MineageMessaging, AtherialListener> {
    public MineageMessaging mineageMessaging;
    public Logger logger;

    public MineageMessagingHandler(MineageMessaging mineageMessaging) {
        super(mineageMessaging);
        this.mineageMessaging = mineageMessaging;
        this.eventListeners = new ArrayList<>();
        this.logger = new AtherialLogger(this.getClass(),mineageMessaging);
    }

    @Override
    public void registerListeners() {
        this.eventListeners.forEach(eventListener -> eventListener.setup(this.mineageMessaging.getEventListener()));
    }

    public abstract void reloadHandler();

    @Override
    public void enable() {
        super.enable();
        this.registerListeners();
    }
}
