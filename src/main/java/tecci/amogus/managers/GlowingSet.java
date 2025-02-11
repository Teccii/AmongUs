package tecci.amogus.managers;

import java.util.HashSet;
import java.util.Set;

public class GlowingSet<T> {
    public class GlowingData {
        private final T data;
        private final int entityId;

        public GlowingData(T data, int entityId) {
            this.data = data;
            this.entityId = entityId;
        }

        public T getData() {
            return data;
        }

        public int getEntityId() {
            return entityId;
        }
    }

    private final Set<GlowingData> set = new HashSet<>();

    public Set<GlowingData> getRawSet() {
        return set;
    }

    public void add(T data, int entityId) {
        if (has(data)) {
            return;
        }

        set.add(new GlowingData(data, entityId));
    }

    public void remove(T data) {
        set.removeIf(d -> d.getData().equals(data));
    }

    public boolean has(T data) {
        for (GlowingData d : set) {
            if (d.getData().equals(data)) {
                return true;
            }
        }

        return false;
    }

    public int getEntityId(T data) {
        for (GlowingData d : set) {
            if (d.getData().equals(data)) {
                return d.getEntityId();
            }
        }

        return -1;
    }
}
