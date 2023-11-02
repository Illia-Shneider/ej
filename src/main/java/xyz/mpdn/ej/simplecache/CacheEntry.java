package xyz.mpdn.ej.simplecache;

final class CacheEntry {
    private final String data;
    private long lastAccessTime;

    public CacheEntry(String data) {
        this.data = data;
        this.lastAccessTime = System.currentTimeMillis();
    }

    public String getData() {
        lastAccessTime = System.currentTimeMillis();
        return data;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }
}
