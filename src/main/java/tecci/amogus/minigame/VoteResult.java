package tecci.amogus.minigame;

import java.util.Optional;
import java.util.UUID;

public class VoteResult {
    public enum ResultType {
        SKIPPED,
        TIE,
        EJECTED
    }

    private final ResultType resultType;
    private final Optional<UUID> ejectedPlayer;

    public VoteResult(ResultType resultType, Optional<UUID> ejectedPlayer) {
        this.resultType = resultType;
        this.ejectedPlayer = ejectedPlayer;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public Optional<UUID> getEjectedPlayer() {
        return ejectedPlayer;
    }
}
