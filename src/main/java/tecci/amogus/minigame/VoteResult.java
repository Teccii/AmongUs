package tecci.amogus.minigame;

import java.util.UUID;

public class VoteResult {
    public enum ResultType {
        SKIPPED,
        TIE,
        EJECTED
    }

    private final ResultType resultType;
    private final UUID ejectedPlayer;

    public VoteResult(ResultType resultType, UUID ejectedPlayer) {
        this.resultType = resultType;
        this.ejectedPlayer = ejectedPlayer;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public UUID getEjectedPlayer() {
        return ejectedPlayer;
    }
}
