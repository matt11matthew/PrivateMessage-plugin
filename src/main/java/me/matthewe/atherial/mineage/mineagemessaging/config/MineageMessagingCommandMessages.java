package me.matthewe.atherial.mineage.mineagemessaging.config;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import net.atherial.api.plugin.CommandMessages;

/**
 * Created by Matthew E on 5/25/2019 at 6:18 PM for the project AtherialApiTest
 */
public class MineageMessagingCommandMessages implements CommandMessages {
    private CommandConfig commandConfig;

    public MineageMessagingCommandMessages(MineageMessaging mineageMessaging) {
        this.commandConfig = new CommandConfig(mineageMessaging).load();
    }

    @Override
    public String getNoPermissionMessage() {
        return commandConfig.noPermission;
    }

    @Override
    public String getHelpArgumentsColor() {
        return commandConfig.helpArgumentsColor;
    }

    @Override
    public String getHelpLine() {
        return commandConfig.helpLine;
    }

    @Override
    public String getHelpHeader() {
        return commandConfig.helpHeader;
    }

    @Override
    public String getHelpFooter() {
        return commandConfig.helpFooter;
    }

    @Override
    public String getPlayerOnlyCommandMessage() {
        return commandConfig.playerOnlyCommand;
    }

    @Override
    public String getCorrectCommandArgumentUsage() {
        return commandConfig.correctCommandArgumentUsage;
    }

    @Override
    public String getCorrectCommandUsage() {
        return commandConfig.correctCommandUsage;
    }
}
