package tecci.amogus.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.SkipElement;
import tecci.amogus.gui.VoteElement;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class VoteItem extends GuiItem {
    private final GameManager gameManager;

    public VoteItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Gui getGui(Player player) {
        Player[] playerList = new Player[Bukkit.getOnlinePlayers().size()];
        playerList = Bukkit.getOnlinePlayers().toArray(playerList);
        int playerCount = playerList.length;

        var builder = Gui.normal().addIngredient('S', new SkipElement(gameManager));

        if (playerCount <= 10) {
            builder = builder.setStructure(
                            ". . . A . B . . .",
                            ". . . C . D . . .",
                            ". . . E . F . . .",
                            ". . . G . H . . .",
                            ". . . I . J . . .",
                            ". . S . . . . . ."
                    );

            char[] chars = new char[] {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'
            };

            for (int i = 0; i < playerCount; i++) {
                builder = builder.addIngredient(chars[i], new VoteElement(gameManager, playerList[i]));
            }

            //add the ingredients for player votes and skip button

        } else {
            builder = builder.setStructure(
                            ". . A . B . C . .",
                            ". . D . E . F . .",
                            ". . G . H . I . .",
                            ". . J . K . L . .",
                            ". . M . N . O . .",
                            ". S . . . . . . ."
                    );

            char[] chars = new char[] {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'
            };

            for (int i = 0; i < playerCount; i++) {
                builder = builder.addIngredient(chars[i], new VoteElement(gameManager, playerList[i]));
            }
        }

        return builder.build();
    }

    @Override
    protected ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Vote");
    }
}