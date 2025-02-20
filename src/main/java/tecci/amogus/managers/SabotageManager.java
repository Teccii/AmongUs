package tecci.amogus.managers;

import tecci.amogus.minigame.Sabotage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SabotageManager {
    private final Set<Sabotage> activeSabotages = new HashSet<>();
    private final GameManager gameManager;

    public SabotageManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public<T extends Sabotage> Set<T> getSabotagesOfType(Class<T> sabotageType) {
        return activeSabotages.stream()
                .filter(sabotageType::isInstance)
                .map(sabotageType::cast)
                .collect(Collectors.toSet());
    }

    public boolean isEmergencyButtonDisabled() {
        return activeSabotages.stream().anyMatch(Sabotage::disablesEmergencyButton);
    }

    public void startSabotage(Sabotage sabotage) {
        activeSabotages.add(sabotage);
        sabotage.onStart();
    }

    public void endSabotage(Sabotage sabotage) {
        activeSabotages.remove(sabotage);
        sabotage.onEnd();
    }

    public void endAllSabotages() {
        for (Sabotage sabotage : activeSabotages) {
            sabotage.onEnd();
        }

        activeSabotages.clear();
    }
}
