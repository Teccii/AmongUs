package tecci.amogus.managers;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import tecci.amogus.minigame.Role;
import tecci.amogus.util.LocationUtil;
import tecci.amogus.util.RandomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private final GameManager gameManager;
    private final Map<UUID, Role> playerRoles = new HashMap<>();
    private final Team aliveTeam;
    private final Team ghostTeam;

    private UUID hostId = null;
    private boolean hostExists = false;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;

        Scoreboard scoreboard = gameManager.getScoreboard();

        ghostTeam = scoreboard.registerNewTeam("Ghost");
        ghostTeam.setDisplayName("Ghost");
        ghostTeam.setCanSeeFriendlyInvisibles(true);
        ghostTeam.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
        ghostTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);

        aliveTeam = scoreboard.registerNewTeam("Alive");
        aliveTeam.setDisplayName("Alive");
        aliveTeam.setCanSeeFriendlyInvisibles(false);
        aliveTeam.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
        aliveTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);

        tryFindNewHost();
    }

    public Team getAliveTeam() {
        return aliveTeam;
    }

    public Team getGhostTeam() {
        return ghostTeam;
    }

    public Role getRole(Player player) {
        return playerRoles.get(player.getUniqueId());
    }

    public void setRole(Player player, Role role) {
        playerRoles.put(player.getUniqueId(), role);
    }

    public void removeRole(Player player) {
        playerRoles.remove(player.getUniqueId());
    }

    public void clearRoles() {
        playerRoles.clear();
    }

    public boolean isDead(Player player) {
        return playerRoles.get(player.getUniqueId()).isDead();
    }

    public boolean isHost(Player player) {
        return hostExists() && hostId.equals(player.getUniqueId());
    }

    public boolean hostExists() { return hostExists; }

    //teleports people into random positions inside a region
    public void teleportAllRandom(double minX, double maxX, double y, double minZ, double maxZ) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double x = RandomUtil.nextDouble(minX, maxX);
            double z = RandomUtil.nextDouble(minZ, maxZ);
            World world = player.getWorld();
            Location location = LocationUtil.normalize(new Location(world, x, y, z));

            int whileLoops = 0;

            while (world.getBlockAt(location).getType() != Material.AIR && whileLoops < 64) {
                x = RandomUtil.nextDouble(minX, maxX);
                z = RandomUtil.nextDouble(minZ, maxZ);
                location = LocationUtil.normalize(new Location(world, x, y, z));
                whileLoops++;
            }

            player.teleport(location);
        }
    }

    //teleports everyone into a ring around a location
    public void teleportAllRadial(Location center, double radius) {
        int playerCount = Bukkit.getOnlinePlayers().size();
        int playersProcessed = 0;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(LocationUtil.normalize(new Location(
                    player.getWorld(),
                    center.getX() + radius * Math.cos(playersProcessed * Math.TAU / playerCount),
                    center.getY(),
                    center.getZ() + radius * Math.sin(playersProcessed * Math.TAU / playerCount)
            )));

            playersProcessed++;
        }
    }

    public void tryFindNewHost() {
        List<Player> players = ImmutableList.copyOf(Bukkit.getOnlinePlayers());

        if (players.isEmpty()) {
            hostId = null;
            hostExists = false;
        } else {
            hostId = players.getFirst().getUniqueId();
            hostExists = true;
        }
    }
}
