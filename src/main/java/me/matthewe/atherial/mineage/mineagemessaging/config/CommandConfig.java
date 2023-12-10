package me.matthewe.atherial.mineage.mineagemessaging.config;

import me.matthewe.atherial.mineage.mineagemessaging.MineageMessaging;
import net.atherial.api.plugin.config.json.JsonConfig;

public class CommandConfig extends JsonConfig<MineageMessaging, CommandConfig> {
    public String correctCommandArgumentUsage = "&c&lCorrect Usage: &7%command% &7%arguments%";
    public String correctCommandUsage = "&c&lCorrect Usage: &7%command%";
    public String playerOnlyCommand = "&cThis command is player-only.";
    public String helpHeader = "&7&m----------&f[&a&l%command% Help&f]&7&m----------";
    public String helpFooter = "&7&m----------&f[&a&l%command% Help&f]&7&m----------";
    public String helpLine = "&a/%command% &8- &7%description%";
    public String helpArgumentsColor = "&f";
    public String noPermission = "&cYou don't have the permission %permission%.";

    public CommandConfig(MineageMessaging mineageMessaging) {
        super(mineageMessaging, "command_config.json");
    }

    @Override
    public Class<CommandConfig> getConfigClass() {
        return CommandConfig.class;
    }
}
