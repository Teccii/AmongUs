package tecci.amogus.minigame;

public class MinigameConfig {
    public enum TaskBarUpdates {
        ALWAYS,
        MEETINGS,
        NEVER;

        @Override
        public String toString() {
            return switch (this) {
                case ALWAYS -> "Always";
                case MEETINGS -> "Meetings";
                case NEVER -> "Never";
            };
        }
    }

    private int impostorCount = 2;
    private int impostorKillCooldown = 7; //2.5x + 7.5
    private boolean impostorWallhacks = false;

    private int emergencyMeetings = 1;
    private int emergencyCooldown = 3; //5x
    private int discussionTime = 2; //15x
    private int votingTime = 2; //15x
    private boolean anonymousVotes = false;
    private boolean confirmEjects = true;

    private TaskBarUpdates taskBarUpdates = TaskBarUpdates.MEETINGS;
    private int commonTaskCount = 1;
    private int longTaskCount = 1;
    private int shortTaskCount = 4;
    private boolean visualTasks = false;

    public int getImpostorCount() { return impostorCount; }
    public MinigameConfig setImpostorCount(int value) {
        this.impostorCount = clamp(value, 1, 3);
        return this;
    }

    public double getImpostorKillCooldownSeconds() { return impostorKillCooldown * 2.5 + 7.5; }
    public int getImpostorKillCooldown() { return impostorKillCooldown; }
    public MinigameConfig setImpostorKillCooldown(int value) {
        this.impostorKillCooldown = clamp(value, 1, 21);
        return this;
    }

    public boolean getImpostorWallhacks() { return impostorWallhacks; }
    public MinigameConfig setImpostorWallhacks(boolean value) {
        this.impostorWallhacks = value;
        return this;
    }

    public int getEmergencyMeetings() { return emergencyMeetings; }
    public MinigameConfig setEmergencyMeetings(int value) {
        this.emergencyMeetings = clamp(value, 0, 9);
        return this;
    }

    public double getEmergencyCooldownSeconds() { return emergencyCooldown * 5.0; }
    public int getEmergencyCooldown() { return emergencyCooldown; }
    public MinigameConfig setEmergencyCooldown(int value) {
        this.emergencyCooldown = clamp(value, 0, 12);
        return this;
    }

    public double getDiscussionTimeSeconds() { return discussionTime * 15.0; }
    public int getDiscussionTime() { return discussionTime; }
    public MinigameConfig setDiscussionTime(int value) {
        this.discussionTime = clamp(value, 0, 8);
        return this;
    }

    public double getVotingTimeSeconds() { return votingTime * 15.0; }
    public int getVotingTime() { return votingTime; }
    public MinigameConfig setVotingTime(int value) {
        this.votingTime = clamp(value, 0, 8);
        return this;
    }

    public boolean getAnonymousVotes() { return anonymousVotes; }
    public MinigameConfig setAnonymousVotes(boolean value) {
        this.anonymousVotes = value;
        return this;
    }

    public boolean getConfirmEjects() { return confirmEjects; }
    public MinigameConfig setConfirmEjects(boolean value) {
        this.confirmEjects = value;
        return this;
    }

    public TaskBarUpdates getTaskBarUpdates() { return taskBarUpdates; }
    public MinigameConfig setTaskBarUpdates(TaskBarUpdates value) {
        this.taskBarUpdates = value;
        return this;
    }

    public int getCommonTaskCount() { return commonTaskCount; }
    public MinigameConfig setCommonTaskCount(int value) {
        this.commonTaskCount = clamp(value, 0, 2);
        return this;
    }

    public int getLongTaskCount() { return longTaskCount; }
    public MinigameConfig setLongTaskCount(int value) {
        this.longTaskCount = clamp(value, 0, 2);
        return this;
    }

    public int getShortTaskCount() { return shortTaskCount; }
    public MinigameConfig setShortTaskCount(int value) {
        this.shortTaskCount = clamp(value, 0, 5);
        return this;
    }

    public boolean getVisualTasks() { return visualTasks; }
    public MinigameConfig setVisualTasks(boolean value) {
        this.visualTasks = value;
        return this;
    }

    static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }  else if (value > max) {
            return max;
        }

        return value;
    }
}
