package us.ajg0702.vactop;

import fr.mrmicky.fastinv.FastInvManager;
import me.frep.vulcan.api.VulcanAPI;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import us.ajg0702.commands.platforms.bukkit.BukkitCommand;
import us.ajg0702.commands.platforms.bukkit.BukkitSender;
import us.ajg0702.vactop.commands.OpenGUICommand;
import us.ajg0702.vactop.utils.HeadUtils;

public class TopPlugin extends JavaPlugin {

    private VulcanAPI vulcanAPI;
    private HeadUtils headUtils;

    private TopGUI gui;

    @Override
    public void onEnable() {
        vulcanAPI = VulcanAPI.Factory.getApi();
        FastInvManager.register(this);

        this.headUtils = new HeadUtils();

        BukkitSender.setAdventure(this);

        gui = new TopGUI(this);

        PluginCommand pluginCommand = getCommand("topviolations");
        assert pluginCommand != null;
        pluginCommand.setExecutor(new BukkitCommand(new OpenGUICommand(this)));

        getLogger().info("VulcanTopGUI v"+getDescription().getVersion()+" by ajgeiss0702 enabled!");
    }


    public VulcanAPI getVulcanAPI() {
        return vulcanAPI;
    }

    public HeadUtils getHeadUtils() {
        return headUtils;
    }

    public TopGUI getTopGUI() {
        return gui;
    }
}
