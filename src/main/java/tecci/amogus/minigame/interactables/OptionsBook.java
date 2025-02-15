package tecci.amogus.minigame.interactables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.EnumElement;
import tecci.amogus.gui.NumberElement;
import tecci.amogus.gui.OptionsBookTabElement;
import tecci.amogus.gui.ToggleElement;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.MinigameConfig;
import tecci.amogus.minigame.MinigameConfig.TaskBarUpdates;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class OptionsBook extends GuiInteractable {
    private final MinigameConfig config;

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
                .addIngredient('1', new NumberElement(config::setImpostorCount, Object::toString, 1, 3, config.getImpostorCount()))
                .addIngredient('2', new NumberElement(config::setImpostorKillCooldown, value -> String.valueOf(value * 2.5 + 7.5), 1, 21, config.getImpostorKillCooldown()))
                .addIngredient('3', new ToggleElement(config::setImpostorWallhacks, config.getImpostorWallhacks()))
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
                .addIngredient('1', new NumberElement(config::setEmergencyMeetings, Object::toString, 0, 9, config.getEmergencyMeetings()))
                .addIngredient('2', new NumberElement(config::setEmergencyCooldown, value -> String.valueOf((value * 5.0)), 0, 12, config.getEmergencyCooldown()))
                .addIngredient('3', new NumberElement(config::setDiscussionTime, value -> String.valueOf(value * 15.0), 0, 8, config.getDiscussionTime()))
                .addIngredient('4', new NumberElement(config::setVotingTime, value -> String.valueOf(value * 15.0), 0, 8, config.getVotingTime()))
                .addIngredient('5', new ToggleElement(config::setAnonymousVotes, config.getAnonymousVotes()))
                .addIngredient('6', new ToggleElement(config::setConfirmEjects, config.getConfirmEjects()))
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
                .addIngredient('1', new EnumElement<>(config::setTaskBarUpdates, TaskBarUpdates::toString, config.getTaskBarUpdates()))
                .addIngredient('2', new NumberElement(config::setCommonTaskCount, Object::toString, 0, 2, config.getCommonTaskCount()))
                .addIngredient('3', new NumberElement(config::setLongTaskCount, Object::toString, 0, 2, config.getLongTaskCount()))
                .addIngredient('4', new NumberElement(config::setShortTaskCount, Object::toString, 0, 5, config.getShortTaskCount()))
                .addIngredient('5', new ToggleElement(config::setVisualTasks, config.getVisualTasks()))
                .build();

        Gui otherRolesTab = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . A 1 . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('A', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                        .addAllItemFlags()
                        .setDisplayName(new ComponentBuilder("Jester Count").color(ChatColor.WHITE).italic(false).build())
                ))
                .addIngredient('1', new NumberElement(config::setJesterCount, Object::toString, 0, 2, config.getJesterCount()))
                .build();

        return TabGui.normal()
                .setStructure(
                        ". . A B . C D . .",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('A', new OptionsBookTabElement(0))
                .addIngredient('B', new OptionsBookTabElement(1))
                .addIngredient('C', new OptionsBookTabElement(2))
                .addIngredient('D', new OptionsBookTabElement(3))
                .setTabs(List.of(impostorsTab, meetingsTab, tasksTab, otherRolesTab))
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
