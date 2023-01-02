package adhdmc.simplenicks.util.saving;

import adhdmc.simplenicks.SimpleNicks;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerPDC implements AbstractSaving {
    public static final NamespacedKey nickNameSave = new NamespacedKey(SimpleNicks.getInstance(), "nickname");

    @Override
    public String getNickname(OfflinePlayer p) {
        Player player = p.getPlayer();
        if (player == null) return null;
        PersistentDataContainer pdc = p.getPlayer().getPersistentDataContainer();
        return pdc.get(nickNameSave, PersistentDataType.STRING);
    }

    @Override
    public boolean setNickname(OfflinePlayer p, String nickname) {
        Player player = p.getPlayer();
        if (player == null) return false;
        PersistentDataContainer pdc = p.getPlayer().getPersistentDataContainer();
        pdc.set(nickNameSave, PersistentDataType.STRING, nickname);
        return true;
    }

    @Override
    public boolean resetNickname(OfflinePlayer p) {
        Player player = p.getPlayer();
        if (player == null) return false;
        PersistentDataContainer pdc = p.getPlayer().getPersistentDataContainer();
        pdc.remove(nickNameSave);
        return true;
    }
}