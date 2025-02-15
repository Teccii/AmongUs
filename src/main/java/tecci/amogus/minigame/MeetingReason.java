package tecci.amogus.minigame;

public enum MeetingReason {
    EMERGENCY_BUTTON,
    DEAD_BODY;

    @Override
    public String toString() {
        return switch (this) {
            case EMERGENCY_BUTTON -> "Emergency Meeting";
            case DEAD_BODY -> "Dead Body Reported";
        };
    }
}
