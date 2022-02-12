package us.ajg0702.vactop;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import me.frep.vulcan.api.data.IPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TopGUI extends FastInv {
    private final TopPlugin plugin;
    private ViolationType sortingBy = ViolationType.TOTAL;
    public TopGUI(TopPlugin plugin) {
        super(54, "Top Violations");

        this.plugin = plugin;

        refreshItems();
    }

    public void refreshItems() {
        getInventory().clear();

        setItem(1,
                glowIfActive(ViolationType.TOTAL, new ItemBuilder(Material.HOPPER)
                        .name(color("&6Total Violations"))
                ).build(),
                e -> changeSort(ViolationType.TOTAL)
        );
        setItem(2,
                glowIfActive(ViolationType.COMBAT, new ItemBuilder(Material.IRON_SWORD)
                        .name(color("&6Combat Violations"))
                ).build(),
                e -> changeSort(ViolationType.COMBAT)
        );
        setItem(3,
                glowIfActive(ViolationType.MOVEMENT, new ItemBuilder(Material.IRON_BOOTS)
                        .name(color("&6Movement Violations"))
                ).build(),
                e -> changeSort(ViolationType.MOVEMENT)
        );
        setItem(4,
                glowIfActive(ViolationType.PLAYER, new ItemBuilder(Material.BUCKET)
                        .name(color("&6Player Violations"))
                ).build(),
                e -> changeSort(ViolationType.PLAYER)
        );
        setItem(5,
                glowIfActive(ViolationType.AUTOCLICKER, new ItemBuilder(Material.BRICK)
                        .name(color("&6Autoclicker Violations"))
                ).build(),
                e -> changeSort(ViolationType.AUTOCLICKER)
        );
        setItem(6,
                glowIfActive(ViolationType.TIMER, new ItemBuilder(Material.CLOCK)
                        .name(color("&6Timer Violations"))
                ).build(),
                e -> changeSort(ViolationType.TIMER)
        );
        setItem(7,
                glowIfActive(ViolationType.SCAFFHOLD, new ItemBuilder(Material.SCAFFOLDING)
                        .name(color("&6Scaffhold Violations"))
                ).build(),
                e -> changeSort(ViolationType.SCAFFHOLD)
        );

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.sort((o1, o2) -> {
            IPlayerData p1 = plugin.getVulcanAPI().getPlayerData(o1);
            IPlayerData p2 = plugin.getVulcanAPI().getPlayerData(o2);
            return sortingBy.get(p2) - sortingBy.get(p1);
        });

        for(Player player : players) {
            int i = 9 + players.indexOf(player);
            if(i > getInventory().getSize()) break;
            IPlayerData data = plugin.getVulcanAPI().getPlayerData(player);
            ItemStack head = new ItemBuilder(plugin.getHeadUtils().getHeadItem(player.getUniqueId()))
                    .name(color("&r"+player.getName()+" &7- &r"+data.getTotalViolations()+"&7VL"))
                    .lore(colors(
                            "",
                            "&cCombat: &f"+data.getCombatViolations(),
                            "&cMovement: &f"+data.getMovementViolations(),
                            "&cPlayer: &f"+data.getPlayerViolations(),
                            "&cAutoclicker: &f"+data.getAutoClickerViolations(),
                            "&cTimer: &f"+data.getTimerViolations(),
                            "&cScaffhold: &f"+data.getScaffoldViolations()
                    ))
                    .build();
            setItem(i, head, e -> {
                e.getWhoClicked().closeInventory();
                Bukkit.dispatchCommand(e.getWhoClicked(), "vulcan clickalert "+player.getName());
            });
        }
    }

    private ItemBuilder glowIfActive(ViolationType thisIs, ItemBuilder ib) {
        if(thisIs == sortingBy) {
            ib.flags(ItemFlag.HIDE_ENCHANTS);
            ib.enchant(Enchantment.DAMAGE_ALL);
        }
        return ib;
    }

    private String color(String m) {
        return ChatColor.translateAlternateColorCodes('&', m);
    }

    private List<String> colors(String... msgs) {
        List<String> r = new ArrayList<>();
        for(String msg : msgs) {
            r.add(color(msg));
        }
        return r;
    }

    private void changeSort(ViolationType newSort) {
        sortingBy = newSort;
        refreshItems();
    }
}
