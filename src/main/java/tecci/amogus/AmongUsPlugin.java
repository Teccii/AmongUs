package tecci.amogus;

import org.bukkit.plugin.java.JavaPlugin;
import tecci.amogus.listeners.PlayerEventListener;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.InvUI;

public final class AmongUsPlugin extends JavaPlugin {
    private GameManager gameManager;

    @Override
    public void onEnable() {
        super.onEnable();
        InvUI.getInstance().setPlugin(this);

        gameManager = new GameManager(this);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(gameManager), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gameManager.cleanUp();
    }
}
