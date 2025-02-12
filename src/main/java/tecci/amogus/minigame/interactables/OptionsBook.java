package tecci.amogus.minigame.interactables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.NumberItem;
import tecci.amogus.gui.OptionsBookTabItem;
import tecci.amogus.gui.ToggleItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.MinigameConfig;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class OptionsBook extends GuiInteractable {
    private MinigameConfig config;

    public OptionsBook(GameManager gameManager, Location location) {
        super(gameManager, location);
        this.config = gameManager.getConfig();
    }

    @Override
    public Gui getGui(Player player) {
        Gui impostorsTab = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . A 1 . . . . .",
                        ". . B 2 . . . . .",
                        ". . C 3 . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('A', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("# Impostors").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('B', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Impostor Kill Cooldown").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('C', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Impostor Wallhacks").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('1', new NumberItem(value -> config.setImpostorCount(value), Object::toString, 1, 3, config.getImpostorCount()))
                .addIngredient('2', new NumberItem(value -> config.setImpostorKillCooldown(value), value -> String.valueOf(value * 2.5 + 7.5), 1, 21, config.getImpostorKillCooldown()))
                .addIngredient('3', new ToggleItem(value -> config.setImpostorWallhacks(value), config.getImpostorWallhacks()))
                .build();

        Gui meetingsTab = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . A 1 . D 4 . .",
                        ". . B 2 . E 5 . .",
                        ". . C 3 . F 6 . .",
                        ". . . . . . . . ."
                )
                .addIngredient('A', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("# Emergency Meetings").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('B', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Emergency Cooldown").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('C', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Discussion Time").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('D', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Voting Time").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('E', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Anonymous Votes").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('F', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Confirm Ejects").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('1', new NumberItem(value -> config.setEmergencyMeetings(value), Object::toString, 0, 9, config.getEmergencyMeetings()))
                .addIngredient('2', new NumberItem(value -> config.setEmergencyCooldown(value), value -> String.valueOf((value * 5.0)), 0, 12, config.getEmergencyCooldown()))
                .addIngredient('3', new NumberItem(value -> config.setDiscussionTime(value), value -> String.valueOf(value * 15.0), 0, 8, config.getDiscussionTime()))
                .addIngredient('4', new NumberItem(value -> config.setVotingTime(value), value -> String.valueOf(value * 15.0), 0, 8, config.getVotingTime()))
                .addIngredient('5', new ToggleItem(value -> config.setAnonymousVotes(value), config.getAnonymousVotes()))
                .addIngredient('6', new ToggleItem(value -> config.setConfirmEjects(value), config.getConfirmEjects()))
                .build();

        Gui tasksTab = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . A 1 . D 4 . .",
                        ". . B 2 . E 5 . .",
                        ". . C 3 . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('A', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Task Bar Updates").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('B', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("# Common Tasks").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('C', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("# Long Tasks").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('D', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("# Short Tasks").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('E', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Visual Tasks").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('1', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("TODO: ENUM ITEM").color(ChatColor.GREEN).italic(false).build())
                ))
                .addIngredient('2', new NumberItem(value -> config.setCommonTaskCount(value), Object::toString, 0, 2, config.getCommonTaskCount()))
                .addIngredient('3', new NumberItem(value -> config.setLongTaskCount(value), Object::toString, 0, 2, config.getLongTaskCount()))
                .addIngredient('4', new NumberItem(value -> config.setShortTaskCount(value), Object::toString, 0, 5, config.getShortTaskCount()))
                .addIngredient('5', new ToggleItem(value -> config.setVisualTasks(value), config.getVisualTasks()))
                .build();

        return TabGui.normal()
                .setStructure(
                        ". . . A B C . . .",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('A', new OptionsBookTabItem(0))
                .addIngredient('B', new OptionsBookTabItem(1))
                .addIngredient('C', new OptionsBookTabItem(2))
                .setTabs(List.of(impostorsTab, meetingsTab, tasksTab))
                .build();
    }

    @Override
    public void interact(Player player) {
        Window.single()
                .setGui(getGui(player))
                .setTitle("Options Book")
                .addCloseHandler(this::saveConfig)
                .open(player);
    }

    private void saveConfig() {
        gameManager.setConfig(config);
    }
}
