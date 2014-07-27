import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by steve-kim on 7/27/14.
 */
public class Cache {
    private static Map<String, String> cache = new HashMap<String, String>();
    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

    private Cache() {}

    private static class LazyCache {
        private static final Cache INSTANCE = new Cache();
    }

    public static Cache getInstance() {
        return LazyCache.INSTANCE;
    }

    public void put(String key, String value) {
        rwl.writeLock().lock();
        cache.put(key, value);
        rwl.writeLock().unlock();
    }

    public String get(String key) {
        rwl.readLock().lock();
        String partial = cache.get(key);
        rwl.readLock().unlock();

        if (partial == null)
            return null;
        else
            return partial;
    }

    public boolean contains(String key) {
        boolean doesContain;
        rwl.readLock().lock();
        doesContain = cache.containsKey(key);
        rwl.readLock().unlock();
        return doesContain;
    }

    public void remove(String key) {
        rwl.writeLock().lock();
        cache.remove(key);
        rwl.writeLock().unlock();
    }

    public int size() {
        rwl.readLock().lock();
        int size = cache.size();
        rwl.readLock().unlock();
        return size;
    }
}
