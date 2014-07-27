import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by steve-kim on 7/27/14.
 */
public class Cache {
    private static Map<String, String> cache = new HashMap<String, String>();

    private Cache() {}

    private static class LazyCache {
        private static final Cache INSTANCE = new Cache();
    }

    public static Cache getInstance() {
        return LazyCache.INSTANCE;
    }

    public void put(String key, String value) {
        synchronized(cache) {
            cache.put(key, value);
        }
    }

    public String get(String key) {
        synchronized(cache) {
            String partial = cache.get(key);

            if (partial == null)
                return null;
            else
                return partial;
        }
    }

    public boolean contains(String key) {
        synchronized (cache) {
            return cache.containsKey(key);
        }
    }

    public void remove(String key) {
        synchronized (cache) {
            cache.remove(key);
        }
    }

    public int size() {
        synchronized (cache) {
            return cache.size();
        }
    }
}
