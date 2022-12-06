package adhdmc.simplenicks.config;

import adhdmc.simplenicks.SimpleNicks;
import adhdmc.simplenicks.util.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Locale {
    private static Locale instance;
    private final SimpleNicks pluginInstance;
    private final String localeName = "locale.yml";
    private YamlConfiguration localeConfig = null;
    private File localeFile = null;



    private Locale() {
        this.pluginInstance = (SimpleNicks) SimpleNicks.getInstance();
    }

    public static Locale getInstance() {
        if (instance == null) {
            instance = new Locale();
            instance.getLocaleConfig();
            Defaults.setLocaleDefaults();
            instance.saveConfig();
            instance.loadLocaleMessages();
        }
        return instance;
    }

    public void reloadConfig() {
        if (this.localeFile == null) {
            this.localeFile = new File(this.pluginInstance.getDataFolder(), localeName);
        }
        this.localeConfig = YamlConfiguration.loadConfiguration(this.localeFile);
        this.localeConfig.options().copyDefaults(true);
        InputStream defaultStream = this.pluginInstance.getResource(localeName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.localeConfig.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getLocaleConfig() {
        if (this.localeConfig == null) {
            reloadConfig();
        }
        return this.localeConfig;
    }

    public void saveConfig() {
        getLocaleConfig();
        if (this.localeConfig == null || this.localeFile == null) {
            return;
        }
        try {
            this.getLocaleConfig().save(this.localeFile);
        } catch (IOException e) {
            pluginInstance.getLogger().severe("[saveConfig()] Could not save config to " + this.localeFile);
            e.printStackTrace();
        }
        if (!this.localeFile.exists()) {
            this.pluginInstance.saveResource(localeName, false);
        }
    }

    public void loadLocaleMessages() {
        FileConfiguration locale = getLocaleConfig();
        for (Message m : Message.values()) {
            if (m == Message.VERSION) {
                continue;
            }
            String localeOption = m.toString().toLowerCase(java.util.Locale.ENGLISH).replace('_', '-');
            String message = locale.getString(localeOption, null);
            if (message == null) {
                continue;
            }
            m.setMessage(message);
        }
    }
}

