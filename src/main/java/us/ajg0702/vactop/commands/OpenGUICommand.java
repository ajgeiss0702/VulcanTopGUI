package us.ajg0702.vactop.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.ajg0702.commands.BaseCommand;
import us.ajg0702.commands.CommandSender;
import us.ajg0702.vactop.TopPlugin;

import java.util.Collections;
import java.util.List;

public class OpenGUICommand extends BaseCommand {
    private final TopPlugin plugin;
    public OpenGUICommand(TopPlugin plugin) {
        super("topviolation", Collections.singletonList("topvl"), "vulcan.topgui", "A GUI of the players with the most violations");
        this.plugin = plugin;
    }

    @Override
    public List<String> autoComplete(CommandSender commandSender, String[] strings) {
        return Collections.emptyList();
    }

    @Override
    public void execute(CommandSender sender, String[] args, String label) {
        if(!checkPermission(sender)) {
            sender.sendMessage(Component.text("&cNo permission!"));
            return;
        }
        if(!sender.isPlayer()) {
            sender.sendMessage(Component.text("&cPlayers only!"));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.getTopGUI().refreshItems());
        plugin.getTopGUI().open((Player) sender.getHandle());
    }
}
