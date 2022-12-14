package adhdmc.simplenicks;

import adhdmc.simplenicks.commands.CommandHandler;
import adhdmc.simplenicks.commands.SubCommand;
import adhdmc.simplenicks.commands.subcommands.*;
import adhdmc.simplenicks.config.Defaults;
import adhdmc.simplenicks.config.Locale;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/*command based
[/nick [nickname] or /nick <player> [nickname]]
need to check if it's a valid name
[Alphanumeric, tho a bypass permission would be nice]
need to check if the minimessage formats are allowed- i,e, not allowing hover/click event tag
[assuming I'd make a set of blacklisted tag types, check for those]
need to check length after parsing
PlaceholderAPI placeholder for before parsing, and after
*/

public final class SimpleNicks extends JavaPlugin {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static Plugin instance;
    private static Locale locale;
    private static final HashMap<String, SubCommand> subCommands = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        registerSubCommands();
        locale = Locale.getInstance();
        this.saveDefaultConfig();
        Defaults.setConfigDefaults();
        this.getCommand("nick").setExecutor(new CommandHandler());
        instance.getServer().getPluginManager().registerEvents(new LoginListener(), this);
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static Map<String, SubCommand> getSubCommands() {
        return Collections.unmodifiableMap(subCommands);
    }

    public static Logger getSimpleNicksLogger(){
        return instance.getLogger();
    }

    private void registerSubCommands() {
        subCommands.put("reset", new Reset());
        subCommands.put("help", new Help());
        subCommands.put("set", new Set());
        subCommands.put("reload", new Reload());
    }
}
