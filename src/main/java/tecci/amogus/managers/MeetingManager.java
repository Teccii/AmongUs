package tecci.amogus.managers;

import org.bukkit.entity.Player;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.VoteResult;

import java.util.*;

public class MeetingManager {
    private final GameManager gameManager;
    private final Map<UUID, Optional<UUID>> playerVotes = new HashMap<>();
    private final Set<UUID> newDeadBodies = new HashSet<>();

    public MeetingManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean isMeetingActive() {
        GamePhaseType currentPhase = gameManager.getCurrentPhase().getPhaseType();

        return currentPhase == GamePhaseType.DISCUSSION || currentPhase == GamePhaseType.VOTING;
    }

    public boolean canVote() {
        return gameManager.getCurrentPhase().getPhaseType() == GamePhaseType.VOTING;
    }

    public VoteResult getVoteResult() {
        Map<UUID, Integer> voteTally = new HashMap<>();
        int skipVotes = 0;

        for (Optional<UUID> vote : playerVotes.values()) {
            if (vote.isPresent()) {
                UUID votedFor = vote.get();
                voteTally.put(votedFor, voteTally.getOrDefault(votedFor, 0) + 1);
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
            return new VoteResult(VoteResult.ResultType.TIE, Optional.empty());
        }

        if (skipVotes > highestVoteCount) {
            return new VoteResult(VoteResult.ResultType.SKIPPED, Optional.empty());
        }

        return new VoteResult(VoteResult.ResultType.EJECTED, Optional.of(mostVotedPlayer));
    }

    public boolean hasVoted(Player player) {
        return playerVotes.containsKey(player.getUniqueId());
    }

    public void addVote(Player player, Optional<UUID> vote) {
        playerVotes.put(player.getUniqueId(), vote);
    }

    public void removeVote(Player player) {
        playerVotes.remove(player.getUniqueId());
    }

    //removes all votes for a player (cuz they left the game or smtn)
    public void removeVotesFor(Player player) {
        UUID uuid = player.getUniqueId();

        playerVotes.entrySet().removeIf(entry -> entry.getValue().isPresent() && entry.getValue().get().equals(uuid));
    }

    public Optional<UUID> getVote(Player player) {
        return playerVotes.get(player.getUniqueId());
    }

    public Set<UUID> getVotesForPlayer(Player player) {
        Set<UUID> votes = new HashSet<>();
        UUID uuid = player.getUniqueId();

        for (Map.Entry<UUID, Optional<UUID>> entry : playerVotes.entrySet()) {
            Optional<UUID> vote = entry.getValue();

            if (vote.isPresent() && vote.get().equals(uuid)) {
                votes.add(entry.getKey());
            }
        }

        return votes;
    }

    public void clearAllVotes() {
        playerVotes.clear();
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
