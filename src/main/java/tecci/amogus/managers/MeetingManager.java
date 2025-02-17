package tecci.amogus.managers;

import org.bukkit.entity.Player;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.VoteResult;

import java.util.*;

public class MeetingManager {
    private final GameManager gameManager;
    private final Map<UUID, UUID> playerVotes = new HashMap<>();
    private final Set<UUID> newDeadBodies = new HashSet<>();
    private UUID meetingHost = null;
    private boolean canVote = false;

    public MeetingManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean isMeetingActive() {
        GamePhaseType currentPhase = gameManager.getCurrentPhase().getPhaseType();

        return currentPhase == GamePhaseType.DISCUSSION || currentPhase == GamePhaseType.VOTING;
    }

    public VoteResult getVoteResult() {
        Map<UUID, Integer> voteTally = new HashMap<>();
        int skipVotes = 0;

        for (UUID vote : playerVotes.values()) {
            if (vote != null) {
                voteTally.put(vote, voteTally.getOrDefault(vote, 0) + 1);
            } else {
                skipVotes++;
            }
        }

        UUID mostVotedPlayer = null;
        int highestVoteCount = 0;
        boolean isTie = false;

        for (Map.Entry<UUID, Integer> entry : voteTally.entrySet()) {
            int count = entry.getValue();

            if (count > highestVoteCount) {
                highestVoteCount = count;
                mostVotedPlayer = entry.getKey();
                isTie = false;
            } else if (count == highestVoteCount) {
                isTie = true;
            }
        }

        if (isTie || highestVoteCount == skipVotes) {
            return new VoteResult(VoteResult.ResultType.TIE, null);
        }

        if (skipVotes > highestVoteCount) {
            return new VoteResult(VoteResult.ResultType.SKIPPED, null);
        }

        return new VoteResult(VoteResult.ResultType.EJECTED, mostVotedPlayer);
    }

    public boolean hasVoted(Player player) {
        return playerVotes.containsKey(player.getUniqueId());
    }

    public void addVote(Player player, UUID vote) {
        playerVotes.put(player.getUniqueId(), vote);
    }

    public void removeVote(Player player) {
        playerVotes.remove(player.getUniqueId());
    }

    //removes all votes for a player (cuz they left the game or smtn)
    public void removeVotesFor(Player player) {
        UUID uuid = player.getUniqueId();

        playerVotes.entrySet().removeIf(entry -> entry.getValue().equals(uuid));
    }

    public UUID getVote(Player player) {
        return playerVotes.get(player.getUniqueId());
    }

    public Set<UUID> getVotesForPlayer(Player player) {
        Set<UUID> votes = new HashSet<>();
        UUID uuid = player.getUniqueId();

        for (Map.Entry<UUID, UUID> entry : playerVotes.entrySet()) {
            UUID vote = entry.getValue();

            if (vote.equals(uuid)) {
                votes.add(entry.getKey());
            }
        }

        return votes;
    }

    public void clearAllVotes() {
        playerVotes.clear();
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public boolean canVote() {
        return canVote;
    }

    public void setMeetingHost(Player player) {
        meetingHost = player.getUniqueId();
    }

    public UUID getMeetingHost() {
        return meetingHost;
    }

    public Set<UUID> getNewDeadBodies() {
        return newDeadBodies;
    }

    public void addNewDeadBody(Player player) {
        newDeadBodies.add(player.getUniqueId());
    }

    public void removeDeadBody(Player player) {
        newDeadBodies.remove(player.getUniqueId());
    }

    public void clearNewDeadBodies() {
        newDeadBodies.clear();
    }
}
